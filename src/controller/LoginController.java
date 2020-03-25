package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class LoginController {
	@FXML TextField UsernameTF;
	@FXML Button loginBtn;
	
	@FXML
	public void initialize() {
		UsernameTF.setText("");
	}
	
	/**
	 * Actions taken when user click "Login" button
	 * @param e ActionEvent
	 */
	public void onLoginClick(ActionEvent e) {
		Alert prompt = new Alert(AlertType.ERROR);
		
		if(UsernameTF.getText().length() == 0) {
			prompt.setContentText("Please enter username!");
		}
		else if(UsernameTF.getText().equals("admin")) {
			// Display admin scene
			prompt.setHeaderText("Admin user requested!");
		}
		else if(UsernameTF.getText().equals("professor")) {
			// Display professor scene
			prompt.setHeaderText("Professor user requested!");
		}
		else if(UsernameTF.getText().equals("student")) {
			// Display student scene
			prompt.setHeaderText("Student user requested!");
		}
		else {
			// Incorrect username
			prompt.setHeaderText("Invalid username, please try again!");
		}
		
		prompt.showAndWait();
	}
	
	/**
	 * Action taken when "Enter" keyboard button is pressed
	 * @param e ActionEvent
	 */
	@FXML
	public void onEnterPressed(ActionEvent e){
	   onLoginClick(e);
	}
}
