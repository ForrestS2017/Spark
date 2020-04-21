/**
 * @author Anita Kotowska
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
import util.DataController;

/**
 * Controller for the New User Dialog View for the Administrator
 */
public class NewUserDialogController extends BasicWindow{

    @FXML
    private Label LL_Title;

    @FXML
    private Button BN_AddUser;

    @FXML
    private TextField TF_FirstName;

    @FXML
    private TextField TF_LastName;

    @FXML
    private TextField TF_Username;

    @FXML
    private TextField TF_Password;

    @FXML
    private ChoiceBox<String> CB_UserType;
    
    private Stage stage;
    private Parent root;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String type;
    
    /**
     * Populate choice box with types of Users
     */
    public void start() {
    	CB_UserType.getItems().add("Professor");
    	CB_UserType.getItems().add("Student");
    	CB_UserType.getItems().add("Administrator");
    	
    }
    
    /**
     * Adds a new User on button push
     */
    @FXML
    void AddUser() {
    	//Get input
	    	firstName = TF_FirstName.getText();
	    	lastName = TF_LastName.getText();
	    	username = TF_Username.getText();
	    	password = TF_Password.getText();
	    	type = (String) CB_UserType.getValue();
    	
    	ArrayList<User> users = DataController.readUsers();
    	boolean usernameExists = false;
    	
    	//Check user list if username exists already
    	for(User u : users) {
    		if (u.getUsername().equalsIgnoreCase(username)){
    			//Username is taken
    			usernameExists = true;
    			Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setTitle("Error");
	    		alert.setHeaderText("The username you entered is taken.");
	    		alert.setContentText("Please try again.");
	    		alert.showAndWait();
	    		break;
    		}
    	}
    	
    	ArrayList<User> newUser = new ArrayList<>();
    	
    	//Add user 
    	if(usernameExists == false) {
	    	if(type.equalsIgnoreCase(User.TYPE_PROFESSOR)) {
	    		DataController.saveUser(new Professor(firstName, lastName, username, password));
	    	}
	    	else if(type.equalsIgnoreCase(User.TYPE_STUDENT)) {
				DataController.saveUser(new Student(firstName, lastName, username, password));
	    	}
	    	else { //Admin
				DataController.saveUser(new Administrator(firstName, lastName, username, password));
	    	}
	    	BN_AddUser.getScene().getWindow().hide();
    	}
    	
    }
    
}

