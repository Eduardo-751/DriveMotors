package Controller;

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
import DAO.CarDAL;
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
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES)
                    CloseScene();
            });
        });
        
        btnInsert.setOnAction((ActionEvent event) -> {
            if(carDAO.InsertBrand(txtBrand.getText().toUpperCase())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Marca Cadastrada com Sucesso!");
                alert.show();
                CloseScene();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao registrar a Marca");
                alert.show();
            } 
        });
    }// </editor-fold>
    
    private void CloseScene(){
        Stage stage = new Stage();
        stage = (Stage) txtBrand.getScene().getWindow();
        stage.close();
    }
}
