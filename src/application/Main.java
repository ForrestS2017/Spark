/**
 * @author: Luis Guzman
 * @author: Forrest Smith
 */
package application;

import java.io.IOException;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main class to run Spark
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException{
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/LoginView.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		LoginController controller = (LoginController)loader.getController();
		controller.start(primaryStage, loader);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Spark - User Login");
		primaryStage.setResizable(false);
		
		primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
