package Controller;

import DAO.CarDAL;
// <editor-fold defaultstate="collapsed" desc="Imports"> 
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
// </editor-fold>

/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class FXMLBrandInsertController implements Initializable {

    CarDAL carDAO = new CarDAL();

    @FXML private Button btnCancel, btnInsert;
    @FXML private TextField txtBrand;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SetEvent();
    }    
    
    // <editor-fold defaultstate="collapsed" desc="Set Event to Components">  
    private void SetEvent() {
        btnCancel.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente cancelar?", ButtonType.YES, ButtonType.NO);
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.getDialogPane().getStylesheets().add("/CSS/styles.css");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES)
                    CloseScene();
            });
        });
        
        btnInsert.setOnAction((ActionEvent event) -> {
            if(carDAO.InsertBrand(txtBrand.getText().toUpperCase())){
                showAlert(Alert.AlertType.INFORMATION, "Marca Cadastrada com Sucesso!");
                CloseScene();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erro ao registrar a Marca");
            } 
        });
    }// </editor-fold>
    
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setGraphic(null);
        alert.setHeaderText(null);
        alert.getDialogPane().getStylesheets().add("/CSS/styles.css");
        alert.setContentText(message);
        alert.show();
    }
    
    private void CloseScene(){
        Stage stage = (Stage) txtBrand.getScene().getWindow();
        stage.close();
    }
}
