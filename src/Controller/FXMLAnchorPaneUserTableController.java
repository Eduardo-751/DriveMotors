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
public class FXMLAnchorPaneUserTableController implements Initializable {

    UserDAL userDAO = new UserDAL();
    @FXML private AnchorPane anchorPaneMenu;

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> userNome, userUsuario;
    @FXML private TableColumn<User, Integer> userPerfil;
    @FXML private TextField userFilter;
    @FXML private Button btnInsert, btnUpdate, btnDelete;
    @FXML private CheckBox cbStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CreateTable();
        SetEvent();
    }

    // <editor-fold defaultstate="collapsed" desc="Create Table User"> 
    private void CreateTable() {
        ObservableList<User> completeList = FXCollections.observableArrayList(userDAO.getList());
        ObservableList<User> userList = FXCollections.observableArrayList();
        for(User u : completeList){
            if(u.isEnable() || cbStatus.isSelected())
                userList.add(u);
        }
        userNome.setCellValueFactory(new PropertyValueFactory<>("name"));
        userUsuario.setCellValueFactory(new PropertyValueFactory<>("login"));
        userPerfil.setCellValueFactory(new PropertyValueFactory<>("profile"));

        FilteredList<User> filteredUsers = new FilteredList<>(userList);
        
        userFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUsers.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty())
                    return true;
                String lowerCaseFilter = newValue.toLowerCase();
                return user.getName().toLowerCase().contains(lowerCaseFilter)
                        || user.getLogin().toLowerCase().contains(lowerCaseFilter);
            });
        });
        
        SortedList<User> sortedUsers = new SortedList<>(filteredUsers);
        sortedUsers.comparatorProperty().bind(userTable.comparatorProperty());
        userTable.setItems(sortedUsers);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Set Event to Components">  
    private void SetEvent() {
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

        btnUpdate.setOnAction((ActionEvent event) -> {
            User user = userTable.getSelectionModel().getSelectedItem();
            if (user != null) {
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
            } else
                showAlert(Alert.AlertType.ERROR, "Por favor escolha um usuário da tabela!");
        });

        btnDelete.setOnAction((ActionEvent event) -> {
            User user = userTable.getSelectionModel().getSelectedItem();
            if (user != null) {
                String str;
                if(user.isEnable())
                    str = "Você realmente deseja Desativar o Usuario selecionado?";
                else
                    str = "Você realmente deseja Ativar o Usuario selecionado?";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, str, ButtonType.YES, ButtonType.NO);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        userDAO.excluiUsuario(user);
                        CreateTable();
                    }
                });
            } else
                showAlert(Alert.AlertType.ERROR, "Por favor escolha um usuário da tabela!");
        });
        
        userTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if(userTable.getSelectionModel().getSelectedItem() != null)
                {
                    User user = userTable.getSelectionModel().getSelectedItem();
                    if(!user.isEnable())
                        btnDelete.setText("Ativar");
                    else
                        btnDelete.setText("Desativar");
                }
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
            userFilter.setText(null);
            CreateTable();
        });
    }// </editor-fold>
    
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}
