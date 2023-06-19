package Controller;

import Model.User;
import DAO.UserDAL;
import java.net.URL;
// <editor-fold defaultstate="collapsed" desc="Imports"> 
import java.util.ResourceBundle;
import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
// </editor-fold>
/*
 * FXML Controller class
 *
 * @author eduardo
 */
public class FXMLLoginController implements Initializable {

    private User usuario;
    private final UserDAL userDAO = new UserDAL();

    @FXML private TextField txtLogin, txtPassword;
    @FXML private PasswordField txtPasswordField;
    @FXML private CheckBox cbMostrarSenha;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CheckBoxSelected();
    }
    
    @FXML private void TentarLogin() {
        User user = (User) userDAO.getWithWhere("user_login", txtLogin.getText());
        if (user != null) {
            usuario = user;
            String cryptPass;
            if(txtPasswordField.isVisible())
                cryptPass = usuario.setCriptografia(txtPasswordField.getText());
            else
                cryptPass = usuario.setCriptografia(txtPassword.getText());
            if (usuario.getPassword().equals(cryptPass)) {
                txtLogin.setText("");
                txtPasswordField.setText("");
                txtPassword.setText("");
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(FXMLMenuController.class.getResource("../View/FXMLMenu.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(page));
                    
                    FXMLMenuController controller = (FXMLMenuController) loader.getController();

                    stage.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
                        if (!inHierarchy(evt.getPickResult().getIntersectedNode(), controller.getAnchorPane()) && controller.getAnchorPane().getTranslateX() == -0)
                            controller.HideSideBar();
                    });
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();
                    stage = (Stage) txtLogin.getScene().getWindow();
                    stage.close();
                } catch (IOException e) {
                }
            } else
                showAlert(AlertType.ERROR, "Senha Invalida!");
        } else
            showAlert(AlertType.ERROR, "Usuario nao Cadastrado!");

    }

    @FXML private void KeyPressed(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            TentarLogin();
        }
    }
    
    private void CheckBoxSelected(){
        cbMostrarSenha.selectedProperty().addListener(
        (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if(new_val){
                txtPassword.setText(txtPasswordField.getText());
                txtPasswordField.setVisible(false);
                txtPassword.setVisible(true);
            }
            else{
                txtPasswordField.setText(txtPassword.getText());
                txtPasswordField.setVisible(true);
                txtPassword.setVisible(false);
            }
        });
    }
    
    public static boolean inHierarchy(Node node, Node potentialHierarchyElement) {
        if (potentialHierarchyElement == null)
            return true;
        while (node != null) {
            if (node == potentialHierarchyElement)
                return true;
            node = node.getParent();
        }
        return false;
    }
    
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setGraphic(null);
        alert.setHeaderText(null);
        alert.getDialogPane().getStylesheets().add("/CSS/styles.css");
        alert.setContentText(message);
        alert.show();
    }
}