package Controller;

import DAO.UserDAL;
import Model.User;
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
public class FXMLAnchorPaneUserTableController implements Initializable {

    UserDAL userDAO = new UserDAL();
    @FXML private AnchorPane anchorPaneMenu;

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> userNome, userUsuario;
    @FXML private TableColumn<User, Integer> userPerfil;
    @FXML private TableColumn<User, Button> userBtnStatus, userBtnUpdate;
    @FXML private TextField userFilter;
    @FXML private Button btnInsert;
    @FXML private CheckBox cbStatus;
    @FXML private Pagination pagination;
    
    
    private int rowsPerPage = 14;
    private ObservableList<User> userList = FXCollections.observableArrayList();
    private FilteredList<User> filteredUsers;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pagination.setCurrentPageIndex(0);
        createTable();
        setEventListeners();
    }

    // <editor-fold defaultstate="collapsed" desc="Create Table User"> 
    private void createTable() {
        ObservableList<User> completeList = FXCollections.observableArrayList(userDAO.getList());
        userList = FXCollections.observableArrayList();
        for(User u : completeList){
            if(u.isEnable() || cbStatus.isSelected()){
                userList.add(u);
                Button btnUpdate = new Button(); {
                    btnUpdate.setOnAction((ActionEvent event) -> {
                        btnUpdate(u);
                    });
                }
                Button btnStatus = new Button(); {
                    btnStatus.setOnAction((ActionEvent event) -> {
                        btnDelete(u);
                    });
                }
                u.setBtnUpdate(btnUpdate);
                u.setBtnStatus(btnStatus);
            }
        }
        userNome.setCellValueFactory(new PropertyValueFactory<>("name"));
        userUsuario.setCellValueFactory(new PropertyValueFactory<>("login"));
        userPerfil.setCellValueFactory(new PropertyValueFactory<>("profile"));
        userBtnStatus.setCellValueFactory(new PropertyValueFactory<>("BtnStatus"));
        userBtnUpdate.setCellValueFactory(new PropertyValueFactory<>("BtnUpdate"));

        filteredUsers = new FilteredList<>(userList);
        
        userFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            setPredicateFilter(newValue);
            pagination.setCurrentPageIndex(0);
            changeTableView(pagination.getCurrentPageIndex(), rowsPerPage);
        });
        changeTableView(pagination.getCurrentPageIndex(), rowsPerPage);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), rowsPerPage));
    }
    
    private void changeTableView(int index, int limit) {
        int totalPage = (int) (Math.ceil(filteredUsers.size() * 1.0 / rowsPerPage));
        pagination.setPageCount(totalPage);

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, filteredUsers.size());
        
        SortedList<User> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredUsers.subList(fromIndex, toIndex)));
        sortedData.comparatorProperty().bind(userTable.comparatorProperty());
        
        userTable.setItems(sortedData);
    }
    
    private void setPredicateFilter(String newValue){
        filteredUsers.setPredicate(user -> {
            if (newValue == null || newValue.isEmpty())
                return true;
            String lowerCaseFilter = newValue.toLowerCase();
            return user.getName().toLowerCase().contains(lowerCaseFilter)
                || user.getLogin().toLowerCase().contains(lowerCaseFilter);   
        });
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Set Event to Components">  
    private void setEventListeners() {
        btnInsert.setOnAction((ActionEvent event) -> {
            MainApplication.isRegistering = true;
            try {
                AnchorPane a;
                a = (AnchorPane) FXMLLoader.load(getClass().getResource("../View/FXMLAnchorPaneUserInsert.fxml"));
                anchorPaneMenu.getChildren().setAll(a);
            } catch (IOException ex) {
                Logger.getLogger(FXMLAnchorPaneCarInsertController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        userTable.setRowFactory(tableView -> {
            TableRow<User> row = new TableRow<>();
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
            createTable();
            setPredicateFilter(userFilter.getText());
            changeTableView(pagination.getCurrentPageIndex(), rowsPerPage);
        });
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Set Event to Edit and Delete itens from the Table">
    private void btnUpdate(User user) {
        MainApplication.isEditing = true;
        try {
            FXMLLoader loader = new FXMLLoader();
            AnchorPane page = (AnchorPane) loader.load(getClass().getResource("../View/FXMLAnchorPaneUserInsert.fxml").openStream());
            FXMLAnchorPaneUserInsertController controller = (FXMLAnchorPaneUserInsertController) loader.getController();

            controller.SetNew(user);

            anchorPaneMenu.getChildren().setAll(page);
        } catch (IOException ex) {
            Logger.getLogger(FXMLAnchorPaneCarTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void btnDelete(User user) {
        String str;
        if (user.isEnable()) {
            str = "Você realmente deseja Desativar o Usuario selecionado?";
        } else {
            str = "Você realmente deseja Ativar o Usuario selecionado?";
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, str, ButtonType.YES, ButtonType.NO);
        alert.setGraphic(null);
        alert.setHeaderText(null);
        alert.getDialogPane().getStylesheets().add("/CSS/styles.css");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                userDAO.excluiUsuario(user);
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
