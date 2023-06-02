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
import DAO.ClientDAL;
import Model.Client;
/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class FXMLAnchorPaneClientInsertController implements Initializable {

    ClientDAL clientDAO = new ClientDAL();
    Client client, newClient;
    
    @FXML private AnchorPane anchorPaneMenu;
    @FXML private Button btnRegister, btnCancel;
    @FXML private Label lblInsertUser;

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
                    LoadAnchorPane("../View/FXMLAnchorPaneClientTable.fxml");
                }
            });
        });
        
        btnRegister.setOnAction((ActionEvent event) -> {
            //newUser = new Usuario();
            if (MainApplication.isRegistering){
                if(clientDAO.cadastraUsuario(newClient)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Cliente Cadastrado com Sucesso!");
                    alert.show();
                    LoadAnchorPane("../View/FXMLAnchorPaneClientTable.fxml");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro ao registrar o Cliente");
                    alert.show();
                } 
            } else if (MainApplication.isEditing) {
                newClient.setId(this.client.getId());
                if(clientDAO.alteraClient(newClient)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Cliente Editado com Sucesso!");
                    alert.show();
                    LoadAnchorPane("../View/FXMLAnchorPaneClientTable.fxml");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Erro ao Editar o Cliente");
                    alert.show();
                } 
            }
        });
    }// </editor-fold>
    
    public void SetNew(Client client){
        this.client = client;
    }
    
    private void setAnchorPaneChildren(){
        if(MainApplication.isRegistering){
            btnRegister.setText("Cadastrar");
            lblInsertUser.setText("Cadastro de Cliente");
        } else if (MainApplication.isEditing){
            btnRegister.setText("Salvar");
            lblInsertUser.setText("Edição de Cliente");
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
