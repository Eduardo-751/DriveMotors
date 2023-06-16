package Controller;

import DAO.CarDAL;
import Main.ComboBoxDataInsert;
import Model.Brand;
import Model.Model;
// <editor-fold defaultstate="collapsed" desc="Imports"> 
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
// </editor-fold>
/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class FXMLAnchorPaneBrandController implements Initializable {
    
    CarDAL carDAO = new CarDAL();
    ComboBoxDataInsert cbos = new ComboBoxDataInsert();
    @FXML private AnchorPane anchorPaneMenu;
    
    @FXML private ComboBox cbBrand;
    @FXML private TextField txtModel;
    @FXML private ScrollPane spAccessories;
    @FXML private CheckBox cbAbsBreak, cbAirCondic, cbAlarm, cbAlloyWheels, cbDigitalRadio;
    @FXML private CheckBox cbElecWindws, cbParkingAssis, cbPowerSteering, cbcbRearView, dbKeylessStart;
    @FXML private Button btnUpdate, btnDelete;
    @FXML private CheckBox cbBrandStatus, cbModelStatus;
    @FXML private TableView<Brand> brandTable;
    @FXML private TableView<Model> modelTable;
    @FXML private TableColumn<Model, String> brandName, brandStatus, modelName, modelStatus;
    @FXML private TableColumn<Model, Integer> brandId, modelId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CreateBrandTable();
        setEventListeners();
        resetFields();
    }    
    
    // <editor-fold defaultstate="collapsed" desc="Create Table">  
    private void CreateBrandTable() {
        ObservableList<Brand> completeList = FXCollections.observableArrayList(carDAO.getBrandList());
        ObservableList<Brand> brandList = FXCollections.observableArrayList();
        for(Brand b : completeList){
            if(b.isEnable() || cbBrandStatus.isSelected())
                brandList.add(b);
        }
        brandId.setCellValueFactory(new PropertyValueFactory<>("id"));
        brandName.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        brandTable.setItems(brandList);
    }

    private void CreateModelTable() {
        if(brandTable.getSelectionModel().getSelectedItem() != null)
        {
            Brand selectionBrand = brandTable.getSelectionModel().getSelectedItem();
            ObservableList<Model> completeList = FXCollections.observableArrayList(carDAO.getModelList("brand_id", Integer.toString(selectionBrand.getId())));
            ObservableList<Model> modelList = FXCollections.observableArrayList();
            for(Model m : completeList){
                if(m.isEnable() || cbModelStatus.isSelected())
                    modelList.add(m);
            }
            modelId.setCellValueFactory(new PropertyValueFactory<>("id"));
            modelName.setCellValueFactory(new PropertyValueFactory<>("name"));
            modelStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            modelTable.setItems(modelList);
            resetFields();
        }
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Set Event to Components">  
    private void setEventListeners() {
        brandTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if(brandTable.getSelectionModel().getSelectedItem() != null)
                {
                    CreateModelTable();
                }
             }
        });
        brandTable.setRowFactory(tableView -> {
            TableRow<Brand> row = new TableRow<>();
            PseudoClass higlighted = PseudoClass.getPseudoClass("disable");
            row.itemProperty().addListener((obs, oldOrder, newOrder) -> {
                if (newOrder != null && newOrder.getStatus() != null) {
                    row.pseudoClassStateChanged(higlighted, newOrder.getStatus().equals("Inativo"));
                } else {
                    row.pseudoClassStateChanged(higlighted, false);
                }
            });
            return row;
        });
        
        modelTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if(modelTable.getSelectionModel().getSelectedItem() != null)
                {
                    Model selectionModel = modelTable.getSelectionModel().getSelectedItem();
                    cbBrand.setDisable(false);
                    txtModel.setDisable(false);
                    spAccessories.setDisable(false);
                    cbos.setStatus(cbBrand, "brand", "brand_name");
                    cbBrand.setValue(selectionModel.getBrand().getName());
                    txtModel.setText(selectionModel.getName());
                    setAccessories(carDAO.getModel("model_name", txtModel.getText().toUpperCase()).getAccessoriesId());
                    Model m = modelTable.getSelectionModel().getSelectedItem();
                    if(!m.isEnable())
                        btnDelete.setText("Ativar");
                    else
                        btnDelete.setText("Desativar");
                }
             }
        });
        modelTable.setRowFactory(tableView -> {
            TableRow<Model> row = new TableRow<>();
            PseudoClass higlighted = PseudoClass.getPseudoClass("disable");
            row.itemProperty().addListener((obs, oldOrder, newOrder) -> {
                if (newOrder != null && newOrder.getStatus() != null) {
                    row.pseudoClassStateChanged(higlighted, newOrder.getStatus().equals("Inativo"));
                } else {
                    row.pseudoClassStateChanged(higlighted, false);
                }
            });
            return row;
        });
        
        btnUpdate.setOnAction((ActionEvent event) -> {
            if (modelTable.getSelectionModel().getSelectedItem() != null) {
                Model selectionModel = modelTable.getSelectionModel().getSelectedItem();
                boolean[] bool = {cbAlarm.isSelected(), cbAbsBreak.isSelected(), cbAirCondic.isSelected(), cbElecWindws.isSelected(), cbPowerSteering.isSelected(), 
                              cbAlloyWheels.isSelected(), cbcbRearView.isSelected(), cbDigitalRadio.isSelected(), dbKeylessStart.isSelected(), cbParkingAssis.isSelected()};
                selectionModel.setName(txtModel.getText());
                selectionModel.setBrand(carDAO.getBrand("brand_name", cbBrand.getValue().toString()));
                if(carDAO.UpdateModel(selectionModel) && carDAO.UpdateAccessories(selectionModel.getAccessoriesId(), bool)){
                    showAlert(Alert.AlertType.INFORMATION, "Modelo Editado com Sucesso!");
                    CreateModelTable();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erro ao Editar o Modelo");
                } 
            }
            else
                showAlert(Alert.AlertType.ERROR, "Pro favor escolha um Modelo da tabela!");
        });

        btnDelete.setOnAction((ActionEvent event) -> {
            if (modelTable.getSelectionModel().getSelectedItem() != null) {
                Model selectionModel = modelTable.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Você realmente deseja excluir o Modelo selecionado?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        carDAO.DeleteModel(selectionModel);
                        CreateModelTable();
                    }
                });
                if(modelTable.getItems().size() == 0){
                    alert = new Alert(Alert.AlertType.CONFIRMATION, "Esta Marca nao contem nenhum Modelo, gostaria de Desativar-la?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait().ifPresent(response -> {
                        carDAO.DeleteBrand(selectionModel.getBrand());
                        CreateBrandTable();
                            
                    });
                }
            }
            else
                showAlert(Alert.AlertType.ERROR, "Pro favor escolha um Modelo da tabela!");
        });
        
        cbBrandStatus.setOnAction((ActionEvent event) -> {
            CreateBrandTable();
        });
        
        cbModelStatus.setOnAction((ActionEvent event) -> {
            CreateModelTable();
        });
    }// </editor-fold>
    
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
    
    private void resetFields(){
        cbBrand.setDisable(true);
        txtModel.setDisable(true);
        spAccessories.setDisable(true);
        cbBrand.setValue("");
        txtModel.setText("");
        setAccessories(1);
    }
    
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}
