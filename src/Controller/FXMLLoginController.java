package Controller;

// <editor-fold defaultstate="collapsed" desc="Imports"> 
import java.util.ResourceBundle;
import java.io.IOException;
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
// </editor-fold>
import Model.User;
import DAO.UserDAL;
import java.net.URL;
import javafx.beans.value.ObservableValue;
/*
 * FXML Controller class
 *
 * @author eduardo
 */
public class FXMLLoginController implements Initializable {

    private User usuario;
    private final UserDAL userDAL = new UserDAL();

    @FXML private TextField txtLogin, txtPassword;
    @FXML private PasswordField txtPasswordField;
    @FXML private CheckBox cbMostrarSenha;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CheckBoxSelected();
    }
    
    @FXML private void TentarLogin(){
        User user = (User) userDAL.getWithWhere("user_login", txtLogin.getText());
        if (user != null) {
            usuario = user;
            if ((txtPasswordField.isVisible() && usuario.getPassword().equals(txtPasswordField.getText())) || usuario.getPassword().equals(txtPassword.getText())) {
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
                System.out.println("Senha invalida!");
        } else
            System.out.println("Usuario não cadastrado!");

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
}
