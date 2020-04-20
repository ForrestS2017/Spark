/**
 * @author Luis Guzman
 */
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
import model.Student;

/**
 * Controller for the Dashboard for Student's View
 */
public class StudentDashboardController extends BasicWindow {

    // Text-Based
	@FXML Label LL_Header; 
	@FXML Label LL_Subtitle;
	@FXML Label LL_NoCourses;
	@FXML ListView<Course> LV_CourseList;

    // Action-Based

    @FXML Button BN_EnterCourse, BT_Logout;


    // __Shared Data__
    private Student studentObj;
    private ArrayList<Course> courseList;
    private ObservableList<Course> obsList;

    /**
     * Initialize the Dashboard view for student's courses
     */
    @FXML
    public void initialize() {
    	// Only run once studentObj is initialized
		if(studentObj == null)
			return;
		
		//LL_Header.setText(org.getOrganizationName());
    	LL_Subtitle.setText(studentObj.getUsername() + "'s Dashboard");
    	
    	// 1. Retrieve courses current student is enrolled in
    	courseList = studentObj.getCourses();
    	if (courseList == null || courseList.size() < 1) { System.out.println("TESTING: No Courses in System!"); return; }
    	    	
    	// 2. Fill ListView with Courses
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
    	
        if(selectedIndex.size() > 1)
        	return;
        
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
    public void start(Student student) {
    	this.studentObj = student;
    	initialize();
    }
    
    /**
     * Log current user out of their account
     * Helper function retrieves current Stage and passes to logout function call
     */
    @FXML
    public void LogoutHelper() {
    	Stage stage = (Stage)LL_Header.getScene().getWindow();
    	Logout(stage);
    }
}
