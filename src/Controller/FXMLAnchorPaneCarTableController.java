package Controller;

// <editor-fold defaultstate="collapsed" desc="Imports"> 
import java.net.URL;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
// </editor-fold>
import DAO.CarDAL;
import Model.Car;
import Main.MainApplication;
import com.itextpdf.text.Image;

/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class FXMLAnchorPaneCarTableController implements Initializable {

    CarDAL carDAO = new CarDAL();
    @FXML private AnchorPane anchorPaneMenu;

    @FXML private TableView<Car> carTable;
    @FXML private TableColumn<Car, String> carBrand, carModel, carPlate;
    @FXML private TableColumn<Car, Integer> carYear, carMiliage;
    @FXML private TableColumn<Car, Double> carPrice;
    @FXML private TextField carFilter;
    @FXML private Button btnInsert, btnUpdate, btnDelete, btnGerarPDF;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CreateTable();
        setEventListeners();
    }

    // <editor-fold defaultstate="collapsed" desc="Create Table Car">  
    private void CreateTable() {
        ObservableList<Car> carList = FXCollections.observableArrayList(carDAO.getLista());
        carBrand.setCellValueFactory(new PropertyValueFactory<>("brandName"));
        carModel.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        carYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        carMiliage.setCellValueFactory(new PropertyValueFactory<>("mileage"));
        carPlate.setCellValueFactory(new PropertyValueFactory<>("plate"));
        carPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        carTable.setItems(carList);

        FilteredList<Car> filteredCars = new FilteredList<>(carList);

        carFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCars.setPredicate(car -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return car.getBrandName().toLowerCase().contains(lowerCaseFilter)
                        || car.getModelName().toLowerCase().contains(lowerCaseFilter)
                        || car.getYear().toString().toLowerCase().contains(lowerCaseFilter)
                        || car.getPlate().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Car> sortedCars = new SortedList<>(filteredCars);
        sortedCars.comparatorProperty().bind(carTable.comparatorProperty());
        carTable.setItems(sortedCars);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Set Event to Components">  
    private void setEventListeners() {
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
            if (car != null) {
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
                showAlert(Alert.AlertType.ERROR, "Por favor escolha um cliente da tabela!");
            }
        });

        btnDelete.setOnAction((ActionEvent event) -> {
            Car car = carTable.getSelectionModel().getSelectedItem();
            if (car != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Você realmente deseja excluir o automóvel selecionado?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        carDAO.DeleteCar(car);
                        CreateTable();
                    }
                });
            } else {
                showAlert(Alert.AlertType.ERROR, "Pro favor escolha um cliente da tabela!");
            }
        });
        
        btnGerarPDF.setOnAction ((ActionEvent event) -> {
            generateDocument();
        });
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Generate PDF File">  
    public void generateDocument() {
         Document document  = new Document(PageSize.A4);
         FileChooser f = new FileChooser();
         f.getExtensionFilters().add(new ExtensionFilter("PDF", "*.pdf"));
         File file = f.showSaveDialog(new Stage());
         if(file != null){
            try{
               PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath()));
               document.open();
               
               Image imgTitulo = null;
                try {
                    imgTitulo = Image.getInstance("src/Images/logo1.jpg");
                    imgTitulo.setWidthPercentage(50);
                } catch (Exception e) {
                    showAlert(AlertType.ERROR, "Imagem nao Encontrada!");
                }
                
                document.add(new Paragraph(" "));
                
                if (imgTitulo != null) {
                    imgTitulo.setAlignment(Element.ALIGN_CENTER);
                    document.add(imgTitulo);
                }
                
                Paragraph auxParagraph = new Paragraph("Relatório PDF", FontFactory.getFont(FontFactory.TIMES_ROMAN, 24F));
                auxParagraph.setAlignment(1);
                document.add(auxParagraph);

                Paragraph paragrafoSessao = new Paragraph("__________________________________________________________");
                paragrafoSessao.setAlignment(Element.ALIGN_CENTER);
                document.add(paragrafoSessao);
                
                auxParagraph = new Paragraph("  ");
                document.add(auxParagraph);
               
               PdfPTable  table = new PdfPTable (5);
               
               PdfPCell brandCell = new PdfPCell(new Phrase(20F, "Marca", FontFactory.getFont(FontFactory.HELVETICA, 12F, BaseColor.WHITE)));
               brandCell.setBackgroundColor(new BaseColor(10, 83, 120));
               table.addCell(brandCell);
               PdfPCell  modelCell = new PdfPCell (new Phrase(20F, "Modelo", FontFactory.getFont(FontFactory.HELVETICA, 12F, BaseColor.WHITE)));
               modelCell.setBackgroundColor(new BaseColor(10, 83, 120));
               table.addCell(modelCell);
               PdfPCell  yearCell = new PdfPCell (new Phrase(20F, "Ano", FontFactory.getFont(FontFactory.HELVETICA, 12F, BaseColor.WHITE)));
               yearCell.setBackgroundColor(new BaseColor(10, 83, 120));
               table.addCell(yearCell);
               PdfPCell  plateCell = new PdfPCell (new Phrase(20F, "Placa", FontFactory.getFont(FontFactory.HELVETICA, 12F, BaseColor.WHITE)));
               plateCell.setBackgroundColor(new BaseColor(10, 83, 120));
               table.addCell(plateCell);
               PdfPCell  priceCell = new PdfPCell (new Phrase(20F, "valor", FontFactory.getFont(FontFactory.HELVETICA, 12F, BaseColor.WHITE)));
               priceCell.setBackgroundColor(new BaseColor(10, 83, 120));
               table.addCell(priceCell);
               
               int identity = 1;
               for (Car c : carTable.getItems()) {

                    PdfPCell c1 = new PdfPCell(new Phrase(c.getBrandName(), FontFactory.getFont(FontFactory.HELVETICA, 9F)));
                    PdfPCell c2 = new PdfPCell(new Phrase(c.getModelName(), FontFactory.getFont(FontFactory.HELVETICA, 9F)));
                    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell c3 = new PdfPCell(new Phrase(c.getYear().toString(), FontFactory.getFont(FontFactory.HELVETICA, 9F)));
                    c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell c4 = new PdfPCell(new Phrase(c.getPlate().toString(), FontFactory.getFont(FontFactory.HELVETICA, 9F)));
                    PdfPCell c5 = new PdfPCell(new Phrase("R$ " + String.valueOf(c.getPrice()), FontFactory.getFont(FontFactory.HELVETICA, 9F)));
                    c5.setHorizontalAlignment(Element.ALIGN_CENTER);

                    if (identity % 2 == 0) {
                        c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        c3.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        c4.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        c5.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    }

                    table.addCell(c1);
                    table.addCell(c2);
                    table.addCell(c3);
                    table.addCell(c4);
                    table.addCell(c5);
                    identity++;
                }
               
               document.add(table);
               
               showAlert(AlertType.INFORMATION, "PDF Gerado com sucesso!");
               document.close();
            }
            catch(DocumentException | FileNotFoundException ex){

            }
         }
        else{
            showAlert(AlertType.ERROR, "Erro ao Salvar o Arquivo!");
        }
    }// </editor-fold>
    
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
    
}
