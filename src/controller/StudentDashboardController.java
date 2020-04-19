package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Course;
import model.Organization;
import model.Student;
import model.User;
import util.DataController;

public class StudentDashboardController extends BasicWindow {
    /***********************************************
     ************** Widget References **************
     ***********************************************/

    /*******************************
     **** Non-Responsive Widgets ***
     *******************************/
	@FXML Label LL_Header; 
	@FXML Label LL_Subtitle;
	@FXML Label LL_NoCourses;


    /*******************************
     ****** Responsive Widgets *****
     *******************************/
    @FXML ListView<Course> LV_CourseList;
    @FXML Button BN_EnterCourse, BT_Logout;


    /***********************************************
     ****** Widget Methods & Events Listeners ******
     ***********************************************/
    
    private String username;
    private Student studentObj;
    private ArrayList<Course> courseList;
    private ObservableList<Course> obsList;
    
    @FXML
    public void initialize() {
    	// Only run once user name is initialized
		if(username == null)
			return;
		
		username = "lc96";		// TEMP
		
		//LL_Header.setText(org.getOrganizationName());
    	LL_Subtitle.setText(username + "'s Dashboard");
    	
    	// 1. Retrieve Student object
    	ArrayList<User> allStudents = DataController.readUsers();
    	for(User s : allStudents) {
    		if(s.getUsername().equals(username)) {
    			studentObj = (Student)s;
    			break;
    		}
    	}
    	
    	// 2. Retrieve courses current student is enrolled in
    	courseList = studentObj.getCourses();
    	if (courseList == null || courseList.size() < 1) { System.out.println("TESTING: No Courses in System!"); return; }
    	    	
    	// 3. Fill ListView with Courses
    	obsList = FXCollections.observableArrayList();
    	obsList.setAll(courseList);
    	LV_CourseList.setItems(obsList);
    	LV_CourseList.getSelectionModel().select(0);		// Selects first item by default
    }

    /**
     * Enter the course view for the selected course
     */
    @FXML
    private void EnterCourse() { 
    	// Read selected index from ListView
    	ObservableList<Integer> selectedIndex = LV_CourseList.getSelectionModel().getSelectedIndices();
    	
        if(selectedIndex.size() > 1) {
        	// TODO: Display pop up warning
        	System.out.println("TESTING: Please select only one course!");
        	return;
        }
        
        // Retrieve appropriate course object
        Course selectedCourse = courseList.get(selectedIndex.get(0));
        
        // Load next scene and pass information to next scene
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(LAYOUT_STUDENT_COURSE_VIEW));
        Stage stage = (Stage)LL_Header.getScene().getWindow();
        stage.setTitle(selectedCourse.getTitle() + " - " + studentObj.getUsername());
        LoadNewScene(loader, stage);
        
        StudentCourseViewController controller = (StudentCourseViewController) loader.getController();
        controller.start(selectedCourse, studentObj);
    }
    
    /**
     * Used to pass necessary information from login controller to current controller
     * @param username
     */    
    public void start(String username) {
    	this.username = username;
    	initialize();
    }
    
    @FXML
    public void LogoutHelper() {
    	Stage stage = (Stage)LL_Header.getScene().getWindow();
    	Logout(stage);
    }
}
