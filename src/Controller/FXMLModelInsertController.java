package Controller;

import DAO.CarDAL;
// <editor-fold defaultstate="collapsed" desc="Imports"> 
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
// </editor-fold>

/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class FXMLModelInsertController implements Initializable {

    CarDAL carDAO = new CarDAL();
    String brand;

    @FXML private Button btnCancel, btnInsert;
    @FXML private TextField txtModel;
    @FXML private CheckBox cbAbsBreak, cbAirCondic, cbAlarm, cbAlloyWheels, cbDigitalRadio;
    @FXML private CheckBox cbElecWindws, cbParkingAssis, cbPowerSteering, cbcbRearView, dbKeylessStart;

    
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
                if (response == ButtonType.YES) {
                    CloseScene();
                }
            });
        });
        
        btnInsert.setOnAction((ActionEvent event) -> {
            boolean[] bool = {cbAlarm.isSelected(), cbAbsBreak.isSelected(), cbAirCondic.isSelected(), cbElecWindws.isSelected(), cbPowerSteering.isSelected(), 
                              cbAlloyWheels.isSelected(), cbcbRearView.isSelected(), cbDigitalRadio.isSelected(), dbKeylessStart.isSelected(), cbParkingAssis.isSelected()};
            if(carDAO.InsertAccessories(bool) && carDAO.InsertModel(txtModel.getText().toUpperCase(), brand)){
                showAlert(Alert.AlertType.INFORMATION, "Modelo Cadastrado com Sucesso!");
                CloseScene();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erro ao registrar o Modelo");
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
        Stage stage = (Stage) txtModel.getScene().getWindow();
        stage.close();
    }
    
    public void setBrand(String brand){
        this.brand = brand;
    }
}
