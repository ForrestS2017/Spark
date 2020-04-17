package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController extends BasicWindow{
	@FXML TextField TF_Username;
	@FXML TextField TF_Password;
	@FXML Button BN_Login;
	@FXML Button BN_AddUser;

	// Canvas References
	private Stage mainStage;
	private FXMLLoader loader;


	@FXML
	public void initialize() {
		TF_Username.setText("");
	}


	public void start(Stage mainStage, FXMLLoader loader) {
		this.mainStage = mainStage;
		this.loader = loader;
	}

	/**
	 * Actions taken when user click "Login" button
	 * @param e ActionEvent
	 */
	public void onLoginClick(ActionEvent e) {
		String warning = "";
		try {
			if (TF_Username.getText().isBlank()) {
				warning = "Please enter username!";
			} else if (TF_Username.getText().equals("admin")) {
				// Display admin scene
				warning = "Admin user requested!";
			} else if (TF_Username.getText().contains("prof")) {
				// Display professor scene
				warning = "Professor user requested!";
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource(LAYOUT_PROFESSOR_DASHBOARD_VIEW));
				LoadNewScene(loader);
				ProfessorDashboardController controller = (ProfessorDashboardController) loader.getController();
				controller.start();
			} else if (TF_Username.getText().equals("student")) {
				// Display student scene
				warning = "Student user requested!";
			} else {
				// Incorrect username
				warning = "Invalid username, please try again!";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("WARNING: " + warning);
	}


	/**
	 * Add user if the account doesn't exist. Prompt box for if it is a Student, Professor, Admin
	 */
	@FXML
	public void AddUser() {

	}

	/**
	 * Action taken when "Enter" keyboard button is pressed
	 * @param e ActionEvent
	 */
	@FXML
	public void onEnterPressed(ActionEvent e){
		// If the account exists
		onLoginClick(e);
		// Else AddUser()
	}
}
