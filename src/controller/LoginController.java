package controller;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Organization;

public class LoginController extends BasicWindow{
	@FXML TextField TF_Username;
	@FXML TextField TF_Password;
	@FXML Button BN_Login;
	@FXML Button BN_AddUser;

	// Canvas References
	private Stage mainStage;
	private FXMLLoader loader;

	Organization org;
	
	@FXML
	public void initialize() {
		String organizationName = promptForOrganization();

		TF_Username.setText("");

		try {
			org = (Organization)model.UserIO.readUserData(organizationName);
		} catch(EOFException e) {
			//System.out.println("Testing");
			org = new Organization(organizationName);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		System.out.println("Num students in organization = " + org.students.size());
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
			if (TF_Username.getText().isEmpty()) {
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
				controller.start(TF_Username.getText());
			} else if (TF_Username.getText().equals("student")) {
				// Display student scene
				warning = "Student user requested!";
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource(LAYOUT_STUDENT_DASHBOARD_VIEW));
				Stage stage = (Stage)BN_Login.getScene().getWindow();
		        stage.setTitle("Student Course Overview Dashboard");
				LoadNewScene(loader, stage);
				
				StudentDashboardController controller = (StudentDashboardController) loader.getController();
				controller.start(TF_Username.getText().trim());		// pass data current controller to new controller				
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
	 * Prompt user to select existing data file or to create new one
	 * @return Name of organization file
	 */
	private String promptForOrganization() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Organization Selection");
		alert.setHeaderText("Load or Create your Organization's data by selecting one of the options below.");
		alert.setContentText("Choose your option:");

		ButtonType existingFileButton = new ButtonType("Select Existing File");
		ButtonType newFileButton = new ButtonType("Create New File");
		ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(existingFileButton, newFileButton, cancelButton);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == existingFileButton){
		    // Select existing organization file name
			FileChooser fileChooser = new FileChooser();
			String currentPath = Paths.get(".").toAbsolutePath().normalize().toString() + "\\src\\data\\";
			fileChooser.setInitialDirectory(new File(currentPath));
			File selectedFile = fileChooser.showOpenDialog(null);
			String filePath = selectedFile.getName().toString();
			return filePath.substring(0, filePath.length()-4);		// remove '.dat' from end of string and return
		} 
		else if (result.get() == newFileButton) {
		    // New organization file to be created, prompt user for organization name
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("New Organization File");
			dialog.setHeaderText("Create New Organization File");
			dialog.setContentText("Please enter your Organizaiton name:");

			// Get user input
			Optional<String> dialogResult = dialog.showAndWait();
			if (dialogResult.isPresent())
			    return dialogResult.get();
		}
		
	    // User chose CANCEL or closed the dialog
		Platform.exit();
		System.exit(0);
		return null;
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
