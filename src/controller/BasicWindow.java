/**
 * @author Forrest Smith
 */
package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Helper controller class for navigating between views
 */
public class BasicWindow {
    // Paths
    public final String LAYOUT_PATH_LOGIN_SCREEN = "/View/LoginView.fxml";
    public final String LAYOUT_PROFESSOR_COURSE_VIEW = "/view/Professor_CourseView.fxml";
    public final String LAYOUT_PROFESSOR_DASHBOARD_VIEW = "/view/Professor_DashboardView.fxml";
    public final String LAYOUT_STUDENT_DASHBOARD_VIEW = "/view/Student_Dashboard.fxml";
    public final String LAYOUT_STUDENT_COURSE_VIEW = "/view/Student_CourseView.fxml";

    /**
     * Return to login screen
     */
    @FXML
    public void Logout() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(LAYOUT_PATH_LOGIN_SCREEN));
        Stage stage = LoadNewScene(loader);
        LoginController controller = loader.<LoginController>getController();
        controller.start(stage, loader);
    }
    
    public void Logout(Stage s) {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(LAYOUT_PATH_LOGIN_SCREEN));
        Stage stage = LoadNewScene(loader, s);
        LoginController controller = loader.<LoginController>getController();
        controller.start(stage, loader);
    }

    /**
     * Exit application completely
     */
    @FXML
    public void Quit() {
        Platform.exit();
    }

    /**
     * Load new scene via loader and stage
     *
     * @param loader loader to load new scene's controller
     * @return mainStage with new scene
     */
    public Stage LoadNewScene(FXMLLoader loader) {
        return LoadNewScene(loader, getMainStage());
    }

    /**
     * Load new scene via loader and stage
     *
     * @param loader    loader to be used with new scene's controller
     * @param mainStage main stage to load new scene on
     * @return mainStage with new scene
     */
    public Stage LoadNewScene(FXMLLoader loader, Stage mainStage) {
        Stage stage = mainStage;
        try {
            Scene scene = new Scene((Parent) loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stage;
    }

    /**
     * Get main stage
     *
     * @return Main Stage
     */
    public Stage getMainStage() {
        return (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().get();
    }


    /**
     * Display an error
     *
     * @param header Title of error
     * @param body   Content of error
     */
    protected void ShowError(String header, String body) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(header);
        alert.setHeaderText(body);
        alert.showAndWait();
    }
}
