package Controller;

import Model.Client;
import DAO.ClientDAL;
import Main.MainApplication;
import Model.Car;
// <editor-fold defaultstate="collapsed" desc="Imports"> 
import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.css.PseudoClass;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableRow;
// </editor-fold>

/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class FXMLAnchorPaneClientTableController implements Initializable {

    static ClientDAL clientDAO = new ClientDAL();
    @FXML private AnchorPane anchorPaneMenu;

    @FXML private TableView<Client> clientTable;
    @FXML private TableColumn<Client, String> clientNome, clientCPF, clientRG, clientEmail;
    @FXML private TextField clientFilter;
    @FXML private Button btnInsert, btnUpdate, btnDelete;
    @FXML private CheckBox cbStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CreateTable();
        SetEvent();
    }

    // <editor-fold defaultstate="collapsed" desc="Create Table Client">  
    private void CreateTable() {
        ObservableList<Client> completeList = FXCollections.observableArrayList(clientDAO.getList());
        ObservableList<Client> clientList = FXCollections.observableArrayList();
        for(Client c : completeList){
            if(c.isEnable() || cbStatus.isSelected())
                clientList.add(c);
        }
        clientNome.setCellValueFactory(new PropertyValueFactory<>("name"));
        clientCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        clientRG.setCellValueFactory(new PropertyValueFactory<>("rg"));
        clientEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        FilteredList<Client> filteredClients = new FilteredList<>(clientList, b -> true);

        clientFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredClients.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return client.getName().toLowerCase().contains(lowerCaseFilter)
                        || client.getCpf().toLowerCase().contains(lowerCaseFilter)
                        || client.getEmail().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Client> sortedClients = new SortedList<>(filteredClients);
        sortedClients.comparatorProperty().bind(clientTable.comparatorProperty());
        clientTable.setItems(sortedClients);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Set Event to Components">  
    private void SetEvent() {
        btnInsert.setOnAction((ActionEvent event) -> {
            MainApplication.isRegistering = true;
            try {
                AnchorPane a;
                a = (AnchorPane) FXMLLoader.load(getClass().getResource("../View/FXMLAnchorPaneClientInsert.fxml"));
                anchorPaneMenu.getChildren().setAll(a);
            } catch (IOException ex) {
                Logger.getLogger(FXMLAnchorPaneCarInsertController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnUpdate.setOnAction((ActionEvent event) -> {
            Client client = clientTable.getSelectionModel().getSelectedItem();
            if (client != null) {
                MainApplication.isEditing = true;
                try {
                    FXMLLoader loader = new FXMLLoader();
                    AnchorPane page = (AnchorPane) loader.load(getClass().getResource("../View/FXMLAnchorPaneClientInsert.fxml").openStream());
                    FXMLAnchorPaneClientInsertController controller = (FXMLAnchorPaneClientInsertController) loader.getController();

                    controller.SetNew(client);

                    anchorPaneMenu.getChildren().setAll(page);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLAnchorPaneCarTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else
                showAlert(Alert.AlertType.ERROR, "Por favor escolha um cliente da tabela!");
        });

        btnDelete.setOnAction((ActionEvent event) -> {
            Client client = clientTable.getSelectionModel().getSelectedItem();
            if (client != null) {
                String str;
                if(client.isEnable())
                    str = "Você realmente deseja Desativar o Cliente selecionado?";
                else
                    str = "Você realmente deseja Ativar o Cliente selecionado?";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, str, ButtonType.YES, ButtonType.NO);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        clientDAO.excluiClient(client);
                        CreateTable();
                    }
                });
            } else
                showAlert(Alert.AlertType.ERROR, "Por favor escolha um Cliente da tabela!");
        });
        
        clientTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if(clientTable.getSelectionModel().getSelectedItem() != null)
                {
                    Client client = clientTable.getSelectionModel().getSelectedItem();
                    if(!client.isEnable())
                        btnDelete.setText("Ativar");
                    else
                        btnDelete.setText("Desativar");
                }
             }
        });
        
        clientTable.setRowFactory(tableView -> {
            TableRow<Client> row = new TableRow<>();
            PseudoClass higlighted = PseudoClass.getPseudoClass("disable");
            row.itemProperty().addListener((obs, oldOrder, newOrder) -> {
                if (newOrder != null && newOrder.getStatus() != null) {
                    row.pseudoClassStateChanged(higlighted, newOrder.getStatus().equals("Inativo"));
                } else {
                    row.pseudoClassStateChanged(higlighted, false);
                }
            });
            return row;
        });
        
        cbStatus.setOnAction ((ActionEvent event) -> {
            clientFilter.setText(null);
            CreateTable();
        });
    }// </editor-fold>
    
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}
