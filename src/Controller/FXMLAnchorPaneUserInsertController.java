package Controller;

import Main.MainApplication;
import DAO.UserDAL;
import Model.User;
// <editor-fold defaultstate="collapsed" desc="Imports"> 
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
// </editor-fold>

/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class FXMLAnchorPaneUserInsertController implements Initializable {
    
    UserDAL userDAO = new UserDAL();
    User user, newUser;
    
    @FXML private AnchorPane anchorPaneMenu, apUpdate;
    @FXML private Button btnRegister, btnCancel;
    @FXML private Label lblInsertUser;
    @FXML private TextField txtName, txtLogin, txtPassword;
    @FXML private TextField txtUpdatePassword2, txtUpdatePassword3;
    @FXML private Label lblPassword;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetEvent();
        setAnchorPaneChildren();
    }    
    
    // <editor-fold defaultstate="collapsed" desc="Set Event to Components"> 
    private void SetEvent() {
        btnCancel.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente cancelar?", ButtonType.YES, ButtonType.NO);
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.getDialogPane().getStylesheets().add("/CSS/styles.css");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES)
                    LoadAnchorPane("../View/FXMLAnchorPaneUserTable.fxml");
            });
        });
        
        btnRegister.setOnAction((ActionEvent event) -> {
            if (MainApplication.isRegistering){
                newUser = new User(txtLogin.getText(), "", txtName.getText(), true);
                newUser.setPassword(newUser.setCriptografia(txtPassword.getText()));
                if(userDAO.InsertUsuario(newUser)){
                    showAlert(Alert.AlertType.INFORMATION, "Usuario Cadastrado com Sucesso!");
                    LoadAnchorPane("../View/FXMLAnchorPaneUserTable.fxml");
                } else
                    showAlert(Alert.AlertType.ERROR, "Erro ao registrar o Usuario");
            } else if (MainApplication.isEditing) {
                User user = (User)userDAO.getWithWhere("user_id", String.valueOf(this.user.getId()));
                String cryptPass = user.setCriptografia(txtPassword.getText());
                if(user.getPassword().equals(cryptPass) && txtUpdatePassword2.getText().equals(txtUpdatePassword3.getText())){
                    newUser = new User(txtLogin.getText(), user.setCriptografia(txtUpdatePassword2.getText()), txtName.getText(), true);
                    newUser.setId(this.user.getId());
                    if(userDAO.UpdateUsuario(newUser)){
                        showAlert(Alert.AlertType.INFORMATION, "Usuario Editado com Sucesso!");
                        LoadAnchorPane("../View/FXMLAnchorPaneUserTable.fxml");
                    } else
                        showAlert(Alert.AlertType.ERROR,"Erro ao Editar o Usuario");
                } else 
                    showAlert(Alert.AlertType.ERROR, "Senha invalida!");
            }
        });
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Manage AnchorPane"> 
    private void setAnchorPaneChildren(){
        if(MainApplication.isRegistering){
            apUpdate.setVisible(false);
            btnRegister.setText("Cadastrar");
            lblInsertUser.setText("Cadastro de Usuario");
            lblPassword.setText("Senha:");
        } else if (MainApplication.isEditing){
            apUpdate.setVisible(true);
            btnRegister.setText("Salvar");
            lblInsertUser.setText("Edição de Usuario");
            lblPassword.setText("Senha Antiga:");
        }
    }

    private void LoadAnchorPane(String Url){
        MainApplication.isRegistering = false;
        MainApplication.isEditing = false;
        try {
            AnchorPane a;
            a = (AnchorPane) FXMLLoader.load(getClass().getResource(Url));
            anchorPaneMenu.getChildren().setAll(a);
        } catch (IOException ex) {
            Logger.getLogger(FXMLAnchorPaneCarInsertController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }// </editor-fold>
    
    public void SetNew(User user){
        this.user = user;
        txtLogin.setText(user.getLogin());
        txtName.setText(user.getName());
    }
    
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