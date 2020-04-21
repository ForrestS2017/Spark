/**
 * @author Anita Kotowska
 */
package controller;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Course;
import model.Professor;
import model.Student;
import model.User;
import util.DataController;

/**
 * Controller for the CourseView for the Administrator
 */
public class AdminCourseViewController extends BasicWindow{

    @FXML
    private Label LL_Title;

    @FXML
    private Label LL_Subtitle;

    @FXML
    private Button BT_Back;

    @FXML
    private Button BT_Logout;

    @FXML
    private ListView<Course> LV_CourseList;

    @FXML
    private Label LL_CoursesTitle;

    @FXML
    private Label LL_ProfessorTitle;

    @FXML
    private Button BN_ViewCourse;

    @FXML
    private Label LL_StudentsTitle;

    @FXML
    private Label LL_AverageGradeTitle;

    @FXML
    private Label LL_AverageGradeSubtitle;

    @FXML
    private TableView<Student> TV_Students;
    @FXML
    public TableColumn<Student, String> name;
    @FXML
    public TableColumn<Student, String> id;
    @FXML
    public TableColumn<Student, Integer> grade;

    
    User professorViewing;;
    ArrayList<User> users;
    String username;
    private ArrayList<Course> coursesArryList;
    private ObservableList<Course> coursesObsList;
    private ObservableList<Student> studentsInCourse;
    private ArrayList<User> students;
    private ArrayList<Course> courses;
    
    /**
     * Populates list view with courses taught by previously selected professor
     * Updates title label
     * @param professorSelected
     */
    public void start(User professorSelected) {
    	// Fill courses list view
    	this.professorViewing = professorSelected;
    	LL_Title.setText(professorSelected.getFullName());
    	coursesObsList = FXCollections.observableArrayList();
    	coursesArryList = DataController.readCourses();
    	coursesArryList.forEach(course -> {
            if (course.getProfessor().getUsername().equals(professorViewing.getUsername())) coursesObsList.add(course); });
        LV_CourseList.setItems(coursesObsList);
    	
        
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
     * Goes to the student view of selected course
     * @param event
     */
    @FXML
    void ViewCourse(ActionEvent event) {
    	Course selection = LV_CourseList.getSelectionModel().getSelectedItem();
    	if (selection == null) return;
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(LAYOUT_ADMIN_STUDENT_VIEW));
        LoadNewScene(loader);
        AdminStudentViewController controller = (AdminStudentViewController) loader.getController();
        controller.start(selection, professorViewing);
    }
    
    /**
     * Populate TableView with courses selected student is taking and the students grade in that course
     * @param arg0
     */
    @FXML public void handleMouseClickCourse (MouseEvent arg0) {
    	
    	Course selection = LV_CourseList.getSelectionModel().getSelectedItem();
    	/*studentsInCourse = FXCollections.observableArrayList(selection.getStudents());
  
    	name.setCellValueFactory(new PropertyValueFactory<Student, String>("studentName"));
		grade.setCellValueFactory(new PropertyValueFactory<Student, Integer>("studentGrade"));
		
    	TV_Students.setItems(studentsInCourse);
    	*/
    	
    	if(!selection.getStudents().isEmpty()) {
    		LL_AverageGradeSubtitle.setText(Float.toString(selection.getClassAverage()));
    	}
    	else {
    		LL_AverageGradeSubtitle.setText("0.0");
    	}
    	
    }
}
