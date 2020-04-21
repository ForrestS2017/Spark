/**
 * @author Anita Kotowska
 */
package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.Administrator;
import model.Course;
import model.Professor;
import model.Student;
import model.User;
import util.DataController;

/**
 * Controller for the New Course Dialog View for the Administrator
 */
public class NewCourseDialogController {

    @FXML
    private Label LL_Title;

    @FXML
    private Button BN_AddCourse;

    @FXML
    private TextField TF_Title;

    @FXML
    private TextField TF_Id;

    @FXML
    private ChoiceBox<Professor> CB_Professor;
    
    private ArrayList<User> userList;
    private ObservableList<Professor> professorList;
    private String title;
    private String id;
    private Professor professorSelected;
    
    /**
     * Populates choice box with all the professors in the system
     */
    public void start() {
    	userList = DataController.readUsers();
    	professorList = FXCollections.observableArrayList();
    	userList.forEach(user -> {
            if (user.getType().equalsIgnoreCase(User.TYPE_PROFESSOR)) {
            	professorList.add((Professor)user); 
    		}
    	});
    	if(!professorList.isEmpty()) CB_Professor.setItems(professorList);
    	
    }
    
    /**
     * Adds new Course
     * @param event
     */
    @FXML
    void AddCourse(ActionEvent event) {
    	//Get input
    	title = TF_Title.getText();
    	id = TF_Id.getText();
    	professorSelected = (Professor) CB_Professor.getValue();
	
	ArrayList<Course> courses = DataController.readCourses();
	boolean idExists = false;
	
	//Check course list if course ID exists already
	for(Course c : courses) {
		if (c.getId().equalsIgnoreCase(id)){
			//Username is taken
			idExists = true;
			Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Error");
    		alert.setHeaderText("The course ID already exists.");
    		alert.setContentText("Please try again.");
    		alert.showAndWait();
    		break;
		}
	}
	
		//Add course
		if(idExists == false) {
	    	DataController.saveCourse(new Course(title, id, professorSelected));
	    	BN_AddCourse.getScene().getWindow().hide();
		}
    }

}
