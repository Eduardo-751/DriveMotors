package Controller;

import DAO.CarDAL;
import DAO.UserDAL;
import DAO.ClientDAL;
import DAO.MySQL;
import Model.Car;
import Model.User;
import Model.Client;
import Main.MainApplication;
// <editor-fold defaultstate="collapsed" desc="Imports"> 
import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
// </editor-fold>

/**
 * FXML Controller class
 *
 * @author eduardo
 */
public class FXMLMenuController implements Initializable {
    
    static CarDAL autoDAO = new CarDAL();
    static UserDAL userDAO = new UserDAL();
    static ClientDAL clientDAO = new ClientDAL();
    
    @FXML private AnchorPane anchorPaneSideNav, anchorPaneMenu;
    @FXML private VBox vbNavMenu;
    @FXML private ImageView btnExit, btnLogout;
    @FXML private Label btnMenu;
    @FXML private Label btnDashboard;
    @FXML private Label btnAuto, btnSideAuto, btnSideAutoCadastrar, btnSideAutoEditar, btnSideAutoBrandAndModel;
    @FXML private Label btnUser, btnSideUser, btnSideUserCadastrar, btnSideUserEditar;
    @FXML private Label btnClient, btnSideClient, btnSideClientCadastrar, btnSideClientEditar;
    @FXML private Label btnSideDocument;
    @FXML private Label btnSideConfig;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setEventListeners();
        LoadAnchorPane("../View/FXMLAnchorPaneCarTable.fxml");
    }

    // <editor-fold defaultstate="collapsed" desc="Set Event to Components"> 
    private void setEventListeners() {
        anchorPaneSideNav.setTranslateX(-335);
        btnMenu.setOnMouseClicked(event -> {
            if(anchorPaneSideNav.getTranslateX() == -335)
                ShowSideBar();
        });

        btnAuto.setOnMouseClicked(event -> {
            CheckIfRegisteringToLoadAnchor("../View/FXMLAnchorPaneCarTable.fxml");
        });
        btnSideAuto.setOnMouseClicked(event -> {
            AddSideButtons(btnSideAuto, new Label[] {btnSideAutoCadastrar, btnSideAutoEditar, btnSideAutoBrandAndModel});
        });
        btnSideAutoCadastrar.setOnMouseClicked(event -> {
            HideSideBar(); 
            CheckIfRegisteringToLoadAnchor("../View/FXMLAnchorPaneCarInsert.fxml");
            MainApplication.isRegistering = true;
        });
        btnSideAutoEditar.setOnMouseClicked(event -> {
            HideSideBar(); 
            if(CheckIfRegisteringToLoadDialog("Digite a placa do Veiculo:", autoDAO, "plate_number", "../View/FXMLAnchorPaneCarInsert.fxml", "Placa não encontrada!"))
                MainApplication.isEditing = true;
        });
        btnSideAutoBrandAndModel.setOnMouseClicked(event -> {
            HideSideBar(); 
            CheckIfRegisteringToLoadAnchor("../View/FXMLAnchorPaneBrand.fxml");
        });
        
        btnUser.setOnMouseClicked(event -> {
            CheckIfRegisteringToLoadAnchor("../View/FXMLAnchorPaneUserTable.fxml");
        });
        btnSideUser.setOnMouseClicked(event -> {
            AddSideButtons(btnSideUser, new Label[] {btnSideUserCadastrar, btnSideUserEditar});
        });
        btnSideUserCadastrar.setOnMouseClicked(event -> {
            HideSideBar(); 
            CheckIfRegisteringToLoadAnchor("../View/FXMLAnchorPaneUserInsert.fxml");
            MainApplication.isRegistering = true;
        });
        btnSideUserEditar.setOnMouseClicked(event -> {
            HideSideBar(); 
            if(CheckIfRegisteringToLoadDialog("Digite o Login do Usuario:", userDAO, "user_login", "../View/FXMLAnchorPaneUserInsert.fxml", "Login não encontrado!"))
                MainApplication.isEditing = true;
        });
        
        btnClient.setOnMouseClicked(event -> {
            CheckIfRegisteringToLoadAnchor("../View/FXMLAnchorPaneClientTable.fxml");
        });
        btnSideClient.setOnMouseClicked(event -> {
            AddSideButtons(btnSideClient, new Label[] {btnSideClientCadastrar, btnSideClientEditar});
        });
        btnSideClientCadastrar.setOnMouseClicked(event -> {
            HideSideBar(); 
            CheckIfRegisteringToLoadAnchor("../View/FXMLAnchorPaneClientInsert.fxml");
            MainApplication.isRegistering = true;
        });
        btnSideClientEditar.setOnMouseClicked(event -> {
            HideSideBar(); 
            if(CheckIfRegisteringToLoadDialog("Digite o CPF do Cliente:", clientDAO, "client_cpf", "../View/FXMLAnchorPaneClientInsert.fxml", "CPF não encontrado!"))
                MainApplication.isEditing = true;
        });
        
        btnLogout.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voçê realmente gostaria de Deslogar do Usuario atual?", ButtonType.YES, ButtonType.NO);
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.getDialogPane().getStylesheets().add("/CSS/styles.css");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(FXMLMenuController.class.getResource("../View/FXMLLogin.fxml"));
                        AnchorPane page = (AnchorPane) loader.load();

                        Stage stage = new Stage();
                        stage.setScene(new Scene(page));

                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.show();
                        stage = (Stage) btnLogout.getScene().getWindow();
                        stage.close();
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLMenuController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        });
        
        btnExit.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voce realmente gostaria de Sair do Sistema?", ButtonType.YES, ButtonType.NO);
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.getDialogPane().getStylesheets().add("/CSS/styles.css");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    System.exit(0);
                }
            });
        });
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Check Application Stage to Load new Anchor Pane">  
    private void CheckIfRegisteringToLoadAnchor(String fxmlResource){
        if(MainApplication.isRegistering || MainApplication.isEditing){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "As alterações que você fez talvez não sejam salvas.\nDeseja realmente sair?", ButtonType.YES, ButtonType.NO);
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.getDialogPane().getStylesheets().add("/CSS/styles.css");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    MainApplication.isRegistering = false;
                    MainApplication.isEditing = false;
                    LoadAnchorPane(fxmlResource);
                }
            });
        } else
            LoadAnchorPane(fxmlResource);
    }
    
    private void LoadAnchorPane(String fxmlUrl){
        try {
            AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource(fxmlUrl));
            anchorPaneMenu.getChildren().setAll(a);
        } catch (IOException ex) {
            Logger.getLogger(FXMLMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Check Application Stage to Load a Dialog Screen">  
    private boolean CheckIfRegisteringToLoadDialog(String dialog, MySQL dao, String field, String fxmlResource, String error){
        if(MainApplication.isRegistering || MainApplication.isEditing){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "As alterações que você fez talvez não sejam salvas.\nDeseja realmente sair?", ButtonType.YES, ButtonType.NO);
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.getDialogPane().getStylesheets().add("/CSS/styles.css");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    MainApplication.isRegistering = false;
                    MainApplication.isEditing = false;
                    LoadDialog(dialog, dao, field, fxmlResource, error);
                }
            });
        } else {
            return LoadDialog(dialog, dao, field, fxmlResource, error);
        }
        return true;
    }
    
    private boolean LoadDialog(String dialog, MySQL dao, String field, String fxmlResource, String error){
        
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText(dialog);
        td.setGraphic(null);
        td.setHeaderText(null);
        td.getDialogPane().getStylesheets().add("/CSS/styles.css");
        td.showAndWait();

        Object aux = dao.getWithWhere(field, td.getResult());
        if(aux != null){
            try {
                FXMLLoader loader = new FXMLLoader();
                AnchorPane page = (AnchorPane) loader.load(getClass().getResource(fxmlResource).openStream());

                if(aux.getClass().equals(Car.class)){
                    FXMLAnchorPaneCarInsertController controller = (FXMLAnchorPaneCarInsertController) loader.getController();
                    controller.SetNew((Car) aux);
                } else if(aux.getClass().equals(User.class)){
                    FXMLAnchorPaneUserInsertController controller = (FXMLAnchorPaneUserInsertController) loader.getController();
                    controller.SetNew((User) aux);
                } else if (aux.getClass().equals(Client.class)){
                    FXMLAnchorPaneClientInsertController controller = (FXMLAnchorPaneClientInsertController) loader.getController();
                    controller.SetNew((Client) aux);
                }
                anchorPaneMenu.getChildren().setAll(page);
            } catch (IOException ex) {
                Logger.getLogger("Error", null);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(null);
            alert.setHeaderText(null);
            alert.getDialogPane().getStylesheets().add("/CSS/styles.css");
            alert.setContentText(error);
            alert.show();
            return false;
        }
        return true;
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Add and Remove Buttons on the Side Nav">  
    private void AddSideButtons(Label parent, Label[] add){
        RemoveSideButtons();
        int i = 1;
        for(Label l : add){
            l.setVisible(true);
            vbNavMenu.getChildren().add(vbNavMenu.getChildren().indexOf(parent)+i, l);
            i++;
        }
        
        vbNavMenu.setMinHeight(vbNavMenu.getChildren().size()*85);
    }
    
    private void RemoveSideButtons(){
        ArrayList<Node> remove = new ArrayList<>();
        for(Node l : vbNavMenu.getChildren()){
            if(l != btnSideClient && l != btnSideUser && l != btnSideAuto && l != btnSideDocument && l != btnSideConfig){
                remove.add(l);
                l.setVisible(false);
            }
        }
        if(!remove.isEmpty())
            vbNavMenu.getChildren().removeAll(remove);
        vbNavMenu.setMinHeight(vbNavMenu.getChildren().size()*85);
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Showing and Hiding Side Menu">  
    public void HideSideBar() {
        RemoveSideButtons();
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.4), anchorPaneSideNav);
        translateTransition.setToX(-335);
        translateTransition.play();
        anchorPaneSideNav.setTranslateX(0);
    }

    public void ShowSideBar() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.4), anchorPaneSideNav);
        translateTransition.setToX(0);
        translateTransition.play();

        anchorPaneSideNav.setTranslateX(-335);
    }// </editor-fold>
    
    public AnchorPane getAnchorPane(){
        return anchorPaneSideNav;
    }
}