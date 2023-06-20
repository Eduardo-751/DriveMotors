package Controller;

import DAO.CarDAL;
import Model.Car;
import Main.MainApplication;
import Main.ComboBoxDataInsert;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
// </editor-fold>
/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class FXMLAnchorPaneCarInsertController implements Initializable {

    CarDAL carDAO = new CarDAL();
    ComboBoxDataInsert cbos = new ComboBoxDataInsert();
    Car car, newCar;
    
    @FXML private AnchorPane anchorPaneMenu;
    @FXML private Button btnAddBrand, btnAddModel, btnRegister, btnCancel;
    @FXML private ComboBox cbBrand, cbModel, cbColor, cbFuel, cbTransmission;
    @FXML private TextField txtYear, txtPlate, txtRenavam, txtMileage, txtPrice;
    @FXML private CheckBox cbAbsBreak, cbAirCondic, cbAlarm, cbAlloyWheels, cbDigitalRadio;
    @FXML private CheckBox cbElecWindws, cbParkingAssis, cbPowerSteering, cbcbRearView, dbKeylessStart;
    @FXML private TextArea txtNotes;
    @FXML private Label lblInsertCar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeComboBoxes();
        setEventListeners();
        setTextMask();
        setAnchorPaneChildren();
    }    
    
    // <editor-fold defaultstate="collapsed" desc="Populate ComboBox">  
    private void initializeComboBoxes() {
        cbTransmission.setItems(FXCollections.observableArrayList("Manual", "Automático"));
        cbFuel.setItems(FXCollections.observableArrayList("Gasolina", "Etanol", "Diesel", "Gás natural veicular (GNV)",
                "Biodiesel", "Híbrido", "Elétrico", "Flex"));
        cbos.setStatus(cbBrand, "brand", "brand_name");
        cbos.setStatus(cbColor, "exteriorcolor", "color_name");
        cbModel.setDisable(true);
        btnAddModel.setDisable(true);
        
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Set Event Listeners to Components">  
    private void setEventListeners() {
        btnCancel.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente cancelar?", ButtonType.YES, ButtonType.NO);
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.getDialogPane().getStylesheets().add("/CSS/styles.css");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES)
                    loadAnchorPane("../View/FXMLAnchorPaneCarTable.fxml");
            });
        });
        
        btnRegister.setOnAction((ActionEvent event) -> {
            boolean[] bool = { cbAlarm.isSelected(), cbAbsBreak.isSelected(), cbAirCondic.isSelected(),
                    cbElecWindws.isSelected(), cbPowerSteering.isSelected(), cbAlloyWheels.isSelected(),
                    cbcbRearView.isSelected(), cbDigitalRadio.isSelected(), dbKeylessStart.isSelected(),
                    cbParkingAssis.isSelected() };

            newCar = new Car(carDAO.getModel("model_name", cbModel.getValue().toString().toUpperCase()), (String)cbColor.getValue(), (String)cbTransmission.getValue(),
                            "car_drivetrain", Integer.valueOf(txtYear.getText()), (String)cbFuel.getValue(), txtPlate.getText().toUpperCase(), txtRenavam.getText(),
                            Integer.valueOf(txtMileage.getText()), Double.valueOf(txtPrice.getText()), 2.0, txtNotes.getText().toUpperCase(), true);
            
            if (MainApplication.isRegistering){
                if(carDAO.InsertAccessories(bool)){
                    carDAO.InsertCar(newCar);
                    showAlert(Alert.AlertType.INFORMATION, "Veiculo Cadastrado com Sucesso!");
                    loadAnchorPane("../View/FXMLAnchorPaneCarTable.fxml");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erro ao registrar o Veiculo");
                } 
            } else if (MainApplication.isEditing) {
                newCar.setId(car.getId());
                newCar.setAccessories(car.getAccessoriesId(), bool);
                if(carDAO.UpdateAccessories(newCar.getAccessoriesId(), newCar.getAccessoriesValue())){
                    carDAO.UpdateCar(newCar);
                    showAlert(Alert.AlertType.INFORMATION, "Veiculo Editado com Sucesso!");
                    loadAnchorPane("../View/FXMLAnchorPaneCarTable.fxml");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erro ao Editar o Veiculo");
                } 
            }
            loadAnchorPane("../View/FXMLAnchorPaneCarTable.fxml");
        });
        
        btnAddBrand.setOnAction((ActionEvent event) -> {
            MainApplication.isRegistering = true;
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(FXMLMenuController.class.getResource("../View/FXMLBrandInsert.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(page));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.showAndWait();
                cbos.setStatus(cbBrand, "brand", "brand_name");
            } catch (IOException ex) {
                Logger.getLogger(FXMLAnchorPaneCarInsertController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });
        
        btnAddModel.setOnAction((ActionEvent event) -> {
            MainApplication.isRegistering = true;
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(FXMLMenuController.class.getResource("../View/FXMLModelInsert.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                
                FXMLModelInsertController controller = (FXMLModelInsertController) loader.getController();
                controller.setBrand(cbBrand.getValue().toString());
                
                Stage stage = new Stage();
                stage.setScene(new Scene(page));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.showAndWait();
                
                cbos.setStatusWhiteWhere(cbModel, "model", "brand_name", cbBrand.getValue().toString(), "model_name");
                cbModel.setDisable(false);
                btnAddModel.setDisable(false);
            } catch (IOException ex) {
                Logger.getLogger(FXMLAnchorPaneCarInsertController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });
        
        cbBrand.setOnAction(e -> {
            if(cbBrand.getValue() != null)
                cbos.setStatusWhiteWhere(cbModel, "model", "brand_name", cbBrand.getValue().toString(), "model_name");
            cbModel.setDisable(false);
            btnAddModel.setDisable(false);
        });
        
        cbModel.setOnAction(e -> {
            if(cbModel.getValue() != null)
                setAccessories(carDAO.getModel("model_name", cbModel.getValue().toString().toUpperCase()).getAccessoriesId());
            else
                resetAccessories();
        });
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Text Mask">  
    private void setTextMask(){
        txtPlate.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() == 3 && oldValue.length()<3)
                txtPlate.setText(newValue.toUpperCase() + "-");
            else if(newValue.length() == 9)
                txtPlate.setText(oldValue.toUpperCase());
            else{
                txtPlate.setText(newValue.toUpperCase());
            }
        });
        txtPlate.setPromptText("ABC-1234");
        
        txtYear.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                if(!newValue.equals(""))
                    Integer.valueOf(newValue);
                if(newValue.length() > 4)
                    txtYear.setText(oldValue);
            } catch (NumberFormatException ex){
                txtYear.setText(oldValue);
            }       
        });
        txtYear.setPromptText("YYYY");
        
        txtMileage.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                if(!newValue.equals(""))
                    Integer.valueOf(newValue.substring(newValue.length()-1, newValue.length()));
                if(newValue.length() == 16)
                    txtMileage.setText(oldValue);
            } catch (NumberFormatException ex){
                txtMileage.setText(oldValue);
            }       
        });
        txtMileage.setPromptText("0 Km");
        
        txtRenavam.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                if(!newValue.equals(""))
                    Integer.valueOf(newValue.substring(newValue.length()-1, newValue.length()));
                if(newValue.length() == 12)
                    txtRenavam.setText(oldValue);
            } catch (NumberFormatException ex){
                txtRenavam.setText(oldValue);
            }       
        });
        
        txtPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                if(!newValue.equals(""))
                    Integer.valueOf(newValue.substring(newValue.length()-1, newValue.length()));
                if(newValue.length() == 14)
                    txtPrice.setText(oldValue);
            } catch (NumberFormatException ex){
                txtPrice.setText(oldValue);
            }       
        });
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="CheckBox Setup">  
    private void setAccessories(int id){
        boolean[] bool = carDAO.getAccessories("accessories_id", String.valueOf(id));
        cbAlarm.setSelected(bool[0]);
        cbAbsBreak.setSelected(bool[1]);
        cbAirCondic.setSelected(bool[2]);
        cbElecWindws.setSelected(bool[3]);
        cbPowerSteering.setSelected(bool[4]);
        cbAlloyWheels.setSelected(bool[5]);
        cbcbRearView.setSelected(bool[6]);
        cbDigitalRadio.setSelected(bool[7]);
        dbKeylessStart.setSelected(bool[8]);
        cbParkingAssis.setSelected(bool[9]);
    }
    private void resetAccessories(){
        cbAlarm.setSelected(false);
        cbAbsBreak.setSelected(false);
        cbAirCondic.setSelected(false);
        cbElecWindws.setSelected(false);
        cbPowerSteering.setSelected(false);
        cbAlloyWheels.setSelected(false);
        cbcbRearView.setSelected(false);
        cbDigitalRadio.setSelected(false);
        dbKeylessStart.setSelected(false);
        cbParkingAssis.setSelected(false);       
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Manage AnchorPane">  
    private void setAnchorPaneChildren(){
        if(MainApplication.isRegistering){
            btnRegister.setText("Cadastrar");
            lblInsertCar.setText("Cadastro de Veiculos");
        } else if (MainApplication.isEditing){
            btnRegister.setText("Salvar");
            lblInsertCar.setText("Edição de Veiculos");
        }
    }
        
    private void loadAnchorPane(String Url){
        MainApplication.isRegistering = false;
        MainApplication.isEditing = false;
        try {
            AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource(Url));
            anchorPaneMenu.getChildren().setAll(a);
        } catch (IOException ex) {
            Logger.getLogger(FXMLAnchorPaneCarInsertController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }// </editor-fold>
    
    public void SetNew(Car car){
        this.car = car;
        boolean[] bool = car.getAccessoriesValue();
        cbBrand.setValue(car.getBrandName());
        cbModel.setValue(car.getModelName());
        cbTransmission.setValue(car.getTransmission());
        txtYear.setText(car.getYear().toString());
        cbFuel.setValue(car.getFuel());
        cbColor.setValue(car.getColor());
        txtPlate.setText(car.getPlate());
        txtRenavam.setText(car.getRenavam());
        txtMileage.setText(car.getMileage().toString());
        txtPrice.setText(car.getPrice().toString());
        txtNotes.setText(car.getNotes());
        cbos.setStatusWhiteWhere(cbModel, "model", "brand_name", car.getBrandName(), "model_name");
        setAccessories(car.getAccessoriesId());
        cbModel.setDisable(false);
        btnAddModel.setDisable(false);
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