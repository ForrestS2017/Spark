/**
 * @author Luis Guzman
 * @author Forrest Smith
 */
package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Organization;
import model.Student;
import model.User;
import util.DataController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Controller for Login
 */
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
		//String organizationName = promptForOrganization();

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
		if(TF_Username.getText().isEmpty() || TF_Password.getText().isEmpty()) {
			Alert a = new Alert(AlertType.ERROR); 
			a.setContentText("Invalid input, please fill all fields");
            a.show(); 
			return;
		}
		
		String username = TF_Username.getText(), password = TF_Password.getText();
		User user = passwordCheck(username, password);
		
		if(user == null) {
			// TODO: Display warning
			Alert a = new Alert(AlertType.ERROR); 
			a.setContentText("Username and password do not match!");
            a.show();
			return;
		}
		
		System.out.println("Username and password do match!");
		System.out.println("User obj = " + user);
		
		String warning = "";
		try {
			if (user.getType().equalsIgnoreCase("admin")) {
				// Display admin scene
				warning = "Admin user requested!";
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource(LAYOUT_ADMIN_DASHBOARD_VIEW));
				LoadNewScene(loader);
				AdminDashboardController controller = (AdminDashboardController) loader.getController();
				controller.start(user.getUsername());
			} 
			else if (user.getType().equalsIgnoreCase("professor")) {
				// Display professor scene
				warning = "Professor user requested!";
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource(LAYOUT_PROFESSOR_DASHBOARD_VIEW));
				LoadNewScene(loader, (Stage)BN_Login.getScene().getWindow());
				ProfessorDashboardController controller = (ProfessorDashboardController) loader.getController();
				controller.start(user.getUsername());
			} 
			else if (user.getType().equalsIgnoreCase("student")) {
				System.out.println("Got student user type!");
				// Display student scene
				warning = "Student user requested!";
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource(LAYOUT_STUDENT_DASHBOARD_VIEW));
				Stage stage = (Stage)BN_Login.getScene().getWindow();
		        stage.setTitle("Student Course Overview Dashboard");
				LoadNewScene(loader, stage);
				
				StudentDashboardController controller = (StudentDashboardController) loader.getController();
				controller.start((Student)user);		// pass data current controller to new controller	
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("WARNING: " + warning);
	}
	
	/**
	 * Checks whether if user name and password match
	 * @param username username string input
	 * @param password password string input
	 * @return Returns User object if fields match, returns null otherwise if no match
	 */
	private User passwordCheck(String username, String password) {
		ArrayList<User> allUsers = DataController.readUsers();
		
		if(allUsers.contains(new User("", "", username, "", ""))) {
			User u = allUsers.get(allUsers.indexOf(new User("", "", username, "", "")));
			if(u.verifyPassword(password)) 
				return u;
		}
		
		return null;
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
	 * @throws IOException 
	 */
	@FXML
	public void AddUser() throws IOException {
		try {
			//Open Dialog asking for new user information.
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewUser_Dialog.fxml"));
		        Parent root = (Parent) loader.load();
		        Stage stage = new Stage();
		        NewUserDialogController controller = loader.<NewUserDialogController>getController();
		        controller.start();
		        stage.setScene(new Scene(root));  
		        stage.show();
		    
	 } catch(Exception ex) {
		 	Alert a = new Alert(AlertType.ERROR); 
			a.setContentText("There was an unexpected error!");
			a.show(); 
			System.out.println("AddUser error: " +ex);
	 }
		
		
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
