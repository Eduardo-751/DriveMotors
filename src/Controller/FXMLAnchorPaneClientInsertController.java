package Controller;

import Main.MainApplication;
import DAO.ClientDAL;
import Model.Client;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
// </editor-fold>

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
    @FXML private TextField lblCpf, lblEmail, lblName, lblRg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetEvent();
        setAnchorPaneChildren();
        setTextMask();
    }    
    
    // <editor-fold defaultstate="collapsed" desc="Set Event to Components">  
    private void SetEvent() {
        btnCancel.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente cancelar?", ButtonType.YES, ButtonType.NO);
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.getDialogPane().getStylesheets().add("/CSS/styles.css");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    LoadAnchorPane("../View/FXMLAnchorPaneClientTable.fxml");
                }
            });
        });
        
        btnRegister.setOnAction((ActionEvent event) -> {
            newClient = new Client(lblCpf.getText(), lblName.getText(), lblRg.getText(), lblEmail.getText(), true);
            if (MainApplication.isRegistering){
                if(clientDAO.cadastraUsuario(newClient)){
                    showAlert(Alert.AlertType.INFORMATION, "Cliente Cadastrado com Sucesso!");
                    LoadAnchorPane("../View/FXMLAnchorPaneClientTable.fxml");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erro ao registrar o Cliente");
                } 
            } else if (MainApplication.isEditing) {
                newClient.setId(this.client.getId());
                if(clientDAO.alteraClient(newClient)){
                    showAlert(Alert.AlertType.INFORMATION, "Cliente Editado com Sucesso!");
                    LoadAnchorPane("../View/FXMLAnchorPaneClientTable.fxml");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erro ao Editar o Cliente");
                } 
            }
        });
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Text Mask">  
    private void setTextMask(){
        lblCpf.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 14)
                lblCpf.setText(oldValue);
            else{
                if(newValue.length() == 3 && oldValue.length() < newValue.length())
                    lblCpf.setText(newValue + ".");
                else if(newValue.length() == 7 && oldValue.length() < newValue.length())
                    lblCpf.setText(newValue + ".");
                else if(newValue.length() == 11 && oldValue.length()<11){
                    lblCpf.setText(newValue + "-");
                }
                else{
                    lblCpf.setText(newValue);
                }
            }
        });
        lblCpf.setPromptText("111.111.111-11");
        
        lblRg.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() > 13)
                lblRg.setText(oldValue);
            else{
                if(newValue.length() == 2 && oldValue.length() < newValue.length())
                    lblRg.setText(newValue + ".");
                else if(newValue.length() == 6 && oldValue.length() < newValue.length())
                    lblRg.setText(newValue + ".");
                else if(newValue.length() == 10 && oldValue.length() < newValue.length()){
                    lblRg.setText(newValue + "-");
                }
                else{
                    lblRg.setText(newValue);
                }
            }       
        });
        lblRg.setPromptText("11.111.111-11");
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Manage AnchorPane"> 
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
    }// </editor-fold>
    
    public void SetNew(Client client){
        this.client = client;
        lblName.setText(client.getName());
        lblCpf.setText(client.getCpf());
        lblRg.setText(client.getRg());
        lblEmail.setText(client.getEmail());
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
