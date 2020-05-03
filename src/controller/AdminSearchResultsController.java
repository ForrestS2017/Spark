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
import model.Professor;
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
    private TableView<Student> TV_CoursesList;
    

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
    	LL_Title.setText("Results for \'" + searchInput + "\':");
    	
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
    	setTableView((Student) studentSelection);
    	LL_NameSubtitle.setText(studentSelection.getFullName());
    	LL_IdSubtitle.setText(studentSelection.getUsername());
    }
    
    /**
     * Set courses and corresponding course grade of selected student from mouse click handler to table view
     * @param student
     */
    private void setTableView(Student student) {
    	TV_CoursesList.getColumns().clear();
    	TV_CoursesList.getItems().clear();
    	
    	//Populate table view
    	
    	TableColumn<Student, String> courses_col = new TableColumn<>("Course");
    	courses_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    	courses_col.setPrefWidth(175);
    	
    	TableColumn<Student, String> id_col = new TableColumn<>("ID");
    	id_col.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    	id_col.setPrefWidth(70);
    	
    	TableColumn<Student, String> professor_col = new TableColumn<>("Professor");
    	professor_col.setCellValueFactory(new PropertyValueFactory<>("username"));
    	professor_col.setPrefWidth(175);
    	
    	TableColumn<Student, String> grade_col = new TableColumn<>("Grade");
    	grade_col.setCellValueFactory(new PropertyValueFactory<>("GPA"));
    	grade_col.setPrefWidth(75);
    	
    	TV_CoursesList.getColumns().add(courses_col);
    	TV_CoursesList.getColumns().add(id_col);
    	TV_CoursesList.getColumns().add(professor_col);
    	TV_CoursesList.getColumns().add(grade_col);
    	
	    if(!student.getCourses().isEmpty()) {
	    	ArrayList<Course> courses = student.getCourses();
	    	ArrayList<Student> coursesToDisplay = new ArrayList<Student>();
	    	
		    for(int i=0; i<courses.size();i++) {
		    	Course currentCourse = courses.get(i);
		    	
		    	String title = currentCourse.getTitle();
	    		String id = currentCourse.getId();
		    	String professor = currentCourse.getProfessor().toString();
		    	float grade = 0;
		    	if(!currentCourse.getAssignments().isEmpty())
		    		grade = currentCourse.getStudentFinalGrade(student.getUsername());
		    
		    	Student thisCourse = new Student(title, id, professor,"x");
		    	thisCourse.setGPA(grade);
		    	
		    	coursesToDisplay.add(thisCourse);
		    }
		    
		    TV_CoursesList.getItems().addAll(coursesToDisplay);
	    }
    	
    }

}
