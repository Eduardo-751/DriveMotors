package Controller;

// <editor-fold defaultstate="collapsed" desc="Imports"> 
import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
// </editor-fold>
import Model.Client;
import DAO.ClientDAL;
import Main.MainApplication;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CreateTable();
        SetEvent();
    }    
    
    // <editor-fold defaultstate="collapsed" desc="Create Table Client">  
    private void CreateTable() {
        ObservableList<Client> clientList = FXCollections.observableArrayList(clientDAO.getLista());
        clientNome.setCellValueFactory(new PropertyValueFactory<>("name"));
        clientCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        clientRG.setCellValueFactory(new PropertyValueFactory<>("rg"));
        clientEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clientTable.setItems(clientList);
        
        FilteredList<Client> filteredAuto = new FilteredList<>(clientList, b -> true);
        
        clientFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredAuto.setPredicate(cliente -> {
                if(newValue == null || newValue.isEmpty())
                    return true;

                String lowerCaseFilter = newValue.toLowerCase();
                if(cliente.getName().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (cliente.getCpf().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else 
                    return cliente.getEmail().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Client> sortedClient = new SortedList<>(filteredAuto);
        sortedClient.comparatorProperty().bind(clientTable.comparatorProperty());
        clientTable.setItems(sortedClient);
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
            if(client != null){
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
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Por favor escolha um cliente da tabela!");
                alert.show();
            }
        });
        
        btnDelete.setOnAction((ActionEvent event) -> {
            Client auto = clientTable.getSelectionModel().getSelectedItem();
            if(auto != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Você realmente deseja excluir o cliente selecionado?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        clientDAO.excluiClient(auto);
                        CreateTable();
                    }
                });
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Pro favor escolha um cliente da tabela!");
                alert.show();
            }
        });
    }// </editor-fold>
    
}