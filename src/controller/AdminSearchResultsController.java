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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Course;
import model.Student;
import model.User;
import util.DataController;

/**
 * Controller for the Search Results View for the Administrator
 */
public class AdminSearchResultsController extends BasicWindow{

    @FXML
    private Label LL_Title;

    @FXML
    private Button BT_Back;

    @FXML
    private Button BT_Logout;

    @FXML
    private ListView<User> LV_SearchResults;

    @FXML
    private Label LL_StudentsTitle;

    @FXML
    private Label LL_StudentDetailsTitle;

    @FXML
    private Label LL_StudentsTitle11;

    @FXML
    private Label LL_NameSubtitle;

    @FXML
    private Label LL_IdSubtitle;

    @FXML
    private TableView<User> TV_CoursesList;
    
    @FXML
    private TableColumn<User, Course> TC_Course;

    @FXML
    private TableColumn<User, Float> TC_Grade;

    private String username;
    private ArrayList<Course> courses;
    private ObservableList<Course> coursesObsList;
    private ObservableList<Float> gradesObsList;
    private ArrayList<User> userList;
    private ObservableList<User> matchingStudentsObsList;
    
    /**
     * Populate ListView with search results containing student info input
     * @param searchInput
     * @param username
     */
    public void start(String searchInput, String username) {
    	this.username = username;
    	LL_Title.setText(searchInput);
    	
    	matchingStudentsObsList = FXCollections.observableArrayList();
    	userList = DataController.readUsers();
    	
    	for(User user : userList) {
    		if (user.getType().equalsIgnoreCase(User.TYPE_STUDENT) && (user.getFullName().contains(searchInput) || user.getUsername().contains(searchInput))) { 
                		matchingStudentsObsList.add(user); 
                }
    	}
    	
    	//No matches found
    	if(matchingStudentsObsList.isEmpty()) {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Error");
    		alert.setHeaderText("No matches found.");
    		alert.setContentText("Please go back and search again.");
    		alert.showAndWait();
    	}
    	//Matches found
    	else {
    		LV_SearchResults.setItems(matchingStudentsObsList);
    	}
    	
       
    }
    
    /**
     * Return to previous view
     * @param event
     */
    @FXML
    void GoBack(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(LAYOUT_ADMIN_DASHBOARD_VIEW));
        LoadNewScene(loader);
        AdminDashboardController controller = (AdminDashboardController) loader.getController();
        controller.start(username);
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
     * Handles mouse click for Students listView
     * Updates labels and Courses tableView to match selected student
     * @param event
     */
    @FXML
    void handleMouseClickStudents(MouseEvent event) {
    	User studentSelection = LV_SearchResults.getSelectionModel().getSelectedItem();
    	//setTableView((Student) studentSelection);
    	LL_NameSubtitle.setText(studentSelection.getFullName());
    	LL_IdSubtitle.setText(studentSelection.getUsername());
    }
    
    /**
     * Set courses and corresponding course grade of selected student from mouse click handler to table view
     * @param student
     */
    private void setTableView(Student student) {
    	courses = student.getCourses();
    	for(Course c : courses) {
    		coursesObsList.add(c);
    		gradesObsList.add(c.getStudentFinalGrade(student.getUsername()));
    	}
    	TC_Course.setCellValueFactory(new PropertyValueFactory<>("courses"));
    	TC_Grade.setCellValueFactory(new PropertyValueFactory<>("registeredStudents"));
    	
    }

}
