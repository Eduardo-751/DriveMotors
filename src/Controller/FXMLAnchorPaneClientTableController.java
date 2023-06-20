package Controller;

import Model.Client;
import DAO.ClientDAL;
import Main.MainApplication;
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
import javafx.css.PseudoClass;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableRow;
import javafx.stage.StageStyle;
// </editor-fold>

/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class FXMLAnchorPaneClientTableController implements Initializable {

    static ClientDAL clientDAO = new ClientDAL();
    @FXML
    private AnchorPane anchorPaneMenu;

    @FXML private TableView<Client> clientTable;
    @FXML private TableColumn<Client, String> clientNome, clientCPF, clientRG, clientEmail;
    @FXML private TableColumn<Client, Button> clientBtnStatus, clientBtnUpdate;
    @FXML private TextField clientFilter;
    @FXML private Button btnInsert;
    @FXML private CheckBox cbStatus;
    @FXML private Pagination pagination;
    
    
    private int rowsPerPage = 14;
    private ObservableList<Client> clientList = FXCollections.observableArrayList();
    private FilteredList<Client> filteredClients;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pagination.setCurrentPageIndex(0);
        createTable();
        setEventListeners();
    }

    // <editor-fold defaultstate="collapsed" desc="Create Table Client">  
    private void createTable() {
        ObservableList<Client> completeList = FXCollections.observableArrayList(clientDAO.getList());
        clientList = FXCollections.observableArrayList();
        for (Client c : completeList) {
            if (c.isEnable() || cbStatus.isSelected()){
                clientList.add(c);
                Button btnUpdate = new Button(); {
                    btnUpdate.setOnAction((ActionEvent event) -> {
                        btnUpdate(c);
                    });
                }
                Button btnStatus = new Button(); {
                    btnStatus.setOnAction((ActionEvent event) -> {
                        btnDelete(c);
                    });
                }
                c.setBtnUpdate(btnUpdate);
                c.setBtnStatus(btnStatus);
            }
        }
        clientNome.setCellValueFactory(new PropertyValueFactory<>("name"));
        clientCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        clientRG.setCellValueFactory(new PropertyValueFactory<>("rg"));
        clientEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clientBtnStatus.setCellValueFactory(new PropertyValueFactory<>("BtnStatus"));
        clientBtnUpdate.setCellValueFactory(new PropertyValueFactory<>("BtnUpdate"));

        filteredClients = new FilteredList<>(clientList);
        
        clientFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            setPredicateFilter(newValue);
            pagination.setCurrentPageIndex(0);
            changeTableView(pagination.getCurrentPageIndex(), rowsPerPage);
        });
        changeTableView(pagination.getCurrentPageIndex(), rowsPerPage);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), rowsPerPage));
    }
    
    private void changeTableView(int index, int limit) {
        int totalPage = (int) (Math.ceil(filteredClients.size() * 1.0 / rowsPerPage));
        pagination.setPageCount(totalPage);

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, filteredClients.size());
        
        SortedList<Client> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredClients.subList(fromIndex, toIndex)));
        sortedData.comparatorProperty().bind(clientTable.comparatorProperty());
        
        clientTable.setItems(sortedData);
    }
    
    private void setPredicateFilter(String newValue){
        filteredClients.setPredicate(client -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            return client.getName().toLowerCase().contains(lowerCaseFilter)
                    || client.getCpf().toLowerCase().contains(lowerCaseFilter)
                    || client.getEmail().toLowerCase().contains(lowerCaseFilter);
                
        });
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Set Event to Components">  
    private void setEventListeners() {
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

        cbStatus.setOnAction((ActionEvent event) -> {
            createTable();
            setPredicateFilter(clientFilter.getText());
            changeTableView(pagination.getCurrentPageIndex(), rowsPerPage);
        });
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Set Event to Edit and Delete itens from the Table">
    private void btnUpdate(Client client) {
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
    }

    private void btnDelete(Client client) {
        String str;
        if (client.isEnable()) {
            str = "Você realmente deseja Desativar o Cliente selecionado?";
        } else {
            str = "Você realmente deseja Ativar o Cliente selecionado?";
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, str, ButtonType.YES, ButtonType.NO);
        alert.setGraphic(null);
        alert.setHeaderText(null);
        alert.getDialogPane().getStylesheets().add("/CSS/styles.css");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                clientDAO.excluiClient(client);
                createTable();
            }
        });
    }// </editor-fold>

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setGraphic(null);
        alert.setHeaderText(null);
        alert.getDialogPane().getStylesheets().add("/CSS/styles.css");
        alert.setContentText(message);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.show();
    }
}
