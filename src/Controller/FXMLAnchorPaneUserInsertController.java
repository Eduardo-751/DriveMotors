package Controller;

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
// </editor-fold>
import Main.MainApplication;
import DAO.UserDAL;
import Model.User;
import javafx.scene.control.TextField;
/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class FXMLAnchorPaneUserInsertController implements Initializable {
    
    UserDAL userDAO = new UserDAL();
    User user, newUser;
    
    @FXML private AnchorPane anchorPaneMenu;
    @FXML private Button btnRegister, btnCancel;
    @FXML private Label lblInsertUser;
    @FXML private TextField txtName, txtLogin, txtPassword;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetEvent();
        setAnchorPaneChildren();
    }    
    
    // <editor-fold defaultstate="collapsed" desc="Set Event to Components"> 
    private void SetEvent() {
        btnCancel.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente cancelar?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    LoadAnchorPane("../View/FXMLAnchorPaneUserTable.fxml");
                }
            });
        });
        
        btnRegister.setOnAction((ActionEvent event) -> {
            newUser = new User(txtLogin.getText(), txtPassword.getText(), txtName.getText());
            if (MainApplication.isRegistering){
                if(userDAO.InsertUsuario(newUser)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Usuario Cadastrado com Sucesso!");
                    alert.show();
                    LoadAnchorPane("../View/FXMLAnchorPaneUserTable.fxml");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro ao registrar o Usuario");
                    alert.show();
                } 
            } else if (MainApplication.isEditing) {
                newUser.setId(this.user.getId());
                if(userDAO.UpdateUsuario(newUser)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Usuario Editado com Sucesso!");
                    alert.show();
                    LoadAnchorPane("../View/FXMLAnchorPaneUserTable.fxml");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro ao Editar o Usuario");
                    alert.show();
                } 
            }
        });
    }// </editor-fold>
    
    public void SetNew(User user){
        this.user = user;
        txtLogin.setText(user.getLogin());
        txtPassword.setText(user.getPassword());
        txtName.setText(user.getName());
    }
    
    private void setAnchorPaneChildren(){
        if(MainApplication.isRegistering){
            btnRegister.setText("Cadastrar");
            lblInsertUser.setText("Cadastro de Usuario");
        } else if (MainApplication.isEditing){
            btnRegister.setText("Salvar");
            lblInsertUser.setText("Edição de Usuario");
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
    }
}