/**
 * @author Anita Kotowska
 */
package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Course;
import model.Professor;
import model.Student;
import model.User;
import util.DataController;

/**
 * Controller for the Dashboard for the Administrator
 */
public class AdminDashboardController extends BasicWindow{

    @FXML
    private Rectangle decorBox;

    @FXML
    private Label LL_Title;

    @FXML
    private Label LL_Subtitle;

    @FXML
    private Label LL_NoCourses;

    @FXML
    private ListView<User> LV_ProfessorList;

    @FXML
    private Button BN_ViewProfessor;

    @FXML
    private Button BT_Logout;

    @FXML
    private Button BN_CreateCourse;

    @FXML
    private Label LL_ProfessorDeatilsTitle;

    @FXML
    private Label LL_CoursesTitle;

    @FXML
    private Label LL_StudentsTitle;

    @FXML
    private Label LL_AverageGradeTitle;

    @FXML
    private Label LL_AverageGradeSubtitle;

    @FXML
    private ListView<Course> LV_CoursesTaught;

    @FXML
    private ListView<Student> LV_StudentsTaught;

    @FXML
    private Label LL_AdminOptions;

    @FXML
    private Button BN_CreateUser;

    @FXML
    private Button BN_ConfigureUI;

    @FXML
    private TextField TF_StudentName;

    @FXML
    private Button BN_SearchStudent;

    @FXML
    private Label LL_ProfessorsTitle;

    private ArrayList<User> userList;
    private ObservableList<User> professorList;
    private ArrayList<Course> courseArrList;
    private ObservableList<Course> courseObsList;
    private ArrayList<Student> studentsArrList;
    private ObservableList<Student> studentsObsList;
    private String username;
    
    public void start(String username) {
    	this.username = username;
		fillProfessorList();
    }
    
    @FXML
    void ConfigureUI(ActionEvent event) {

    }

    /**
     * Creating a new course handled by NewCourseDialogController
     * @param event
     */
    @FXML
    void CreateCourse(ActionEvent event) {
    	try {
			//Open Dialog asking for new user information.
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewCourse_Dialog.fxml"));
		        Parent root = (Parent) loader.load();
		        Stage stage = new Stage();
		        NewCourseDialogController controller = loader.<NewCourseDialogController>getController();
		        controller.start();
		        stage.setScene(new Scene(root));  
		        stage.show();
		    
	 } catch(Exception ex) {
		 	Alert a = new Alert(AlertType.ERROR); 
			a.setContentText("There was an unexpected error!");
			a.show(); 
			System.out.println("CreateCourse error: " +ex);
	 }
    }

    /**
     * Creating a new user handled by NewUserDialogController
     * @param event
     */
    @FXML
    void CreateUser(ActionEvent event) {
    	try {
			//Open Dialog asking for new user information.
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NewUser_Dialog.fxml"));
		        Parent root = (Parent) loader.load();
		        Stage stage = new Stage();
		        NewUserDialogController controller = loader.<NewUserDialogController>getController();
		        controller.start();
		        stage.setScene(new Scene(root));  
		        stage.showAndWait();
		    
	 } catch(Exception ex) {
		 	Alert a = new Alert(AlertType.ERROR); 
			a.setContentText("There was an unexpected error!");
			a.show(); 
			System.out.println("AddUser error: " +ex);
	 }
    	//Refresh Professor List
    	fillProfessorList();
    }

    /**
     * Logs admin out of system and opens login page
     * @param event
     */
    @FXML
    void Logout(ActionEvent event) {
    	Logout();
    }

    /**
     * Search for student containing input
     * Handled by AdminSearchResultsController
     * @param event
     */
    @FXML
    void SearchStudent(ActionEvent event) {
    	String searchInput = TF_StudentName.getText();
    	
    	if (searchInput.equals("")) {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Error");
    		alert.setHeaderText("Please enter student name or ID to search.");
    		alert.setContentText("Please try again.");
    		alert.showAndWait();
    	}
    	//Search Student
    	else {
    		FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/view/Admin_SearchResultsView.fxml"));
            LoadNewScene(loader);
            AdminSearchResultsController controller = (AdminSearchResultsController) loader.getController();
            controller.start(searchInput, username);
    	}
    	
    }

    /**
     * Go into course view for selected professor
     * @param event
     */
    @FXML
    void ViewProfessor(ActionEvent event) {
    	 User selection = LV_ProfessorList.getSelectionModel().getSelectedItem();
    	 if (selection == null) return;
    	 FXMLLoader loader = new FXMLLoader();
         loader.setLocation(this.getClass().getResource(LAYOUT_ADMIN_COURSE_VIEW));
         LoadNewScene(loader);
         AdminCourseViewController controller = (AdminCourseViewController) loader.getController();
         controller.start(selection);
     }
    
    /**
     * Handler for admin clicking on a professor from list of professors
     * Updates courses taught list, students taught list, and overall average student grade
     */
    @FXML public void handleMouseClick() {
        User selection = LV_ProfessorList.getSelectionModel().getSelectedItem();
    	courseObsList = FXCollections.observableArrayList();
    	studentsObsList = FXCollections.observableArrayList();
    	courseArrList = DataController.readCourses();
    	courseArrList.forEach(course -> {
            if (course.getProfessor().equals(selection)) {
            	courseObsList.add(course);
            	for(int i=0;i<course.getStudents().size(); i++){
            		if(!studentsObsList.contains(course.getStudents().get(i)))
            			studentsObsList.add(course.getStudents().get(i));
    			}
            }
        });
        LV_CoursesTaught.setItems(courseObsList);
        LV_StudentsTaught.setItems(studentsObsList);
        
        //Update average grade
        courseArrList = DataController.readCourses();
        //Get courses taught by selected professor
        ArrayList<Course> coursesTaught = new ArrayList<>();
        for(Course c : courseArrList) {
        	if(c.getProfessor().equals(selection)) {
        		coursesTaught.add(c);
        	}
        }
        
        float avg=0;
	    if(!coursesTaught.isEmpty()) {
        	int size = coursesTaught.size();
        	int non_empty_class_count = size;
        	
	        for(int i=0; i<size;i++) {
	        	if(!(coursesTaught.get(i).getStudents().isEmpty()) 
	        			&& !(coursesTaught.get(i).getAssignments().isEmpty())) {
	        		avg = avg + coursesTaught.get(i).getClassAverage();
	        	}
	        	else {
	        		non_empty_class_count-=1;
	        	}
	        }
	        
	        avg = avg/non_empty_class_count;
	    }
        LL_AverageGradeSubtitle.setText(Float.toString(avg));
    }
    
    /**
     * Fills the professor list view with all professors in the system
     */
    private void fillProfessorList() {
    	// Fill professor list view
    	professorList = FXCollections.observableArrayList();
    	userList = DataController.readUsers();
    	userList.forEach(user -> {
            if (user.getType().equalsIgnoreCase(User.TYPE_PROFESSOR)) {
            	professorList.add(user); 
    		}
    	});
        LV_ProfessorList.setItems(professorList);
        
    }


}