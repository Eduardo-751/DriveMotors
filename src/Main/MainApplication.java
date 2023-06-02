package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 *
 * @author eduardo
 */
public class MainApplication extends Application {
    
    public static boolean isRegistering = false;
    public static boolean isEditing = false;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        stage.initStyle(StageStyle.UNDECORATED);
        
        Parent root = FXMLLoader.load(getClass().getResource("../View/FXMLLogin.fxml"));

        Scene scene = new Scene(root);
        scene.setFill(null);
        stage.setScene(scene);  
        stage.show();
    }    
}
