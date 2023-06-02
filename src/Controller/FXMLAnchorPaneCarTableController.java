package Controller;

// <editor-fold defaultstate="collapsed" desc="Imports"> 
import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
// </editor-fold>
import DAO.CarDAL;
import Model.Car;
import Main.MainApplication;
/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class FXMLAnchorPaneCarTableController implements Initializable {
    
    static CarDAL carDAO = new CarDAL();
    @FXML private AnchorPane anchorPaneMenu;
    
    @FXML private TableView<Car> carTable;
    @FXML private TableColumn<Car, String> carBrand, carModel, carPlate;
    @FXML private TableColumn<Car, Integer> carYear, carMiliage;
    @FXML private TableColumn<Car, Double> carPrice;
    @FXML private TextField carFilter;
    @FXML private Button btnInsert, btnUpdate, btnDelete;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CreateTable();
        SetEvent();
    }   
    
    // <editor-fold defaultstate="collapsed" desc="Create Table Car">  
    private void CreateTable() {
        ObservableList<Car> autoList = FXCollections.observableArrayList(carDAO.getLista());
        carBrand.setCellValueFactory(new PropertyValueFactory<>("brandName"));
        carModel.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        carYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        carMiliage.setCellValueFactory(new PropertyValueFactory<>("mileage"));
        carPlate.setCellValueFactory(new PropertyValueFactory<>("plate"));
        carPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        carTable.setItems(autoList);
        
        FilteredList<Car> filteredAuto = new FilteredList<>(autoList, b -> true);
        
        carFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredAuto.setPredicate(car -> {
                if(newValue == null || newValue.isEmpty())
                    return true;

                String lowerCaseFilter = newValue.toLowerCase();
                if(car.getBrandName().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (car.getModelName().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else if (car.getYear().toString().toLowerCase().contains(lowerCaseFilter))
                    return true;
                else return car.getPlate().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Car> sortedAuto = new SortedList<>(filteredAuto);
        sortedAuto.comparatorProperty().bind(carTable.comparatorProperty());
        carTable.setItems(sortedAuto);
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Set Event to Components">  
    private void SetEvent() {
        btnInsert.setOnAction((ActionEvent event) -> {
            MainApplication.isRegistering = true;
            try {
                AnchorPane a;
                a = (AnchorPane) FXMLLoader.load(getClass().getResource("../View/FXMLAnchorPaneCarInsert.fxml"));
                anchorPaneMenu.getChildren().setAll(a);
            } catch (IOException ex) {
                Logger.getLogger(FXMLAnchorPaneCarInsertController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });
        
        btnUpdate.setOnAction((ActionEvent event) -> {
            Car car = carTable.getSelectionModel().getSelectedItem();
            if(car != null){
                MainApplication.isEditing = true;
                car.setAccessories(car.getAccessoriesId(), carDAO.getAccessories("accessories_id", String.valueOf(car.getAccessoriesId())));
                try {
                    FXMLLoader loader = new FXMLLoader();
                    AnchorPane page = (AnchorPane) loader.load(getClass().getResource("../View/FXMLAnchorPaneCarInsert.fxml").openStream());
                    FXMLAnchorPaneCarInsertController controller = (FXMLAnchorPaneCarInsertController) loader.getController();
                    controller.SetNew(car);

                    anchorPaneMenu.getChildren().setAll(page);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLAnchorPaneCarTableController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Por favor escolha um automóvel da tabela!");
                alert.show();
            }
        });
        
        btnDelete.setOnAction((ActionEvent event) -> {
            Car car = carTable.getSelectionModel().getSelectedItem();
            if(car != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Você realmente deseja excluir o automóvel selecionado?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        carDAO.DeleteCar(car);
                        CreateTable();
                    }
                });
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Pro favor escolha um automóvel da tabela!");
                alert.show();
            }
        });
    }// </editor-fold>
}
