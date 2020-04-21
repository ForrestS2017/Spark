/**
 * @author Anita Kotowska
 */
package controller;

import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.Course;
import model.Student;
import model.User;
import util.DataController;

/**
 * Controller for the Student View for the Administrator
 */
public class AdminStudentViewController extends BasicWindow{

    @FXML
    private Label LL_Title;

    @FXML
    private Label LL_Subtitle;

    @FXML
    private Button BT_Back;

    @FXML
    private Button BT_Logout;

    @FXML
    private ListView<Student> LV_StudentList;

    @FXML
    private Label LL_StudentsTitle;

    @FXML
    private Label LL_StudentDetailsTitle;

    @FXML
    private Label LL_NameTitle;

    @FXML
    private Label LL_IdTitle;

    @FXML
    private Label LL_StudentsTitle11;

    @FXML
    private Label LL_NameSubtitle;

    @FXML
    private Label LL_IdSubtitle;

    @FXML
    private Label LL_GradeTItle;

    @FXML
    private Label LL_GradeSubtitle;

    @FXML
    private ListView<Course> LV_Courses;

    @FXML
    private Label LL_CoursesTItle;

    @FXML
    private Button BN_RegisterStudent;
    
    String username;
    Course courseDisplayed;
    User professorPreviouslyDisplayed;
    private ArrayList<Student> studentArrList;
    private ObservableList<Student> studentObsList;
    private ArrayList<Course> coursesArrList;
    private ObservableList<Course> coursesObsList;
    
    /**
     * Updates title with course name
     * Updates listView with students in course
     * @param course
     * @param professor
     */
    public void start(Course course, User professor) {
		this.courseDisplayed = course;
		this.professorPreviouslyDisplayed = professor;
		LL_Title.setText(course.getTitle());
		updateStudentListView();
    }
    
    /**
     * Return to previous view
     * @param event
     */
    @FXML
    void GoBack(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(LAYOUT_ADMIN_COURSE_VIEW));
        LoadNewScene(loader);
        AdminCourseViewController controller = (AdminCourseViewController) loader.getController();
        controller.start(professorPreviouslyDisplayed);
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
     * Button clicked to register a student to class that is currently being viewed
     * @param event
     */
    @FXML
    void RegisterStudent(ActionEvent event) {
    	ArrayList<User> users = new ArrayList<>(DataController.readUsers());
    	ArrayList<Student> choices = new ArrayList<>();
    	for(User u : users) {
    		//Add student only if not already in the class
    		if(u.getType().equals("STUDENT") &&
    				!u.getCourses().contains(courseDisplayed)) {
    			choices.add((Student)u);
    		}
    	}
    	
    	ChoiceDialog<Student> dialog = new ChoiceDialog<>(null,choices);
    	dialog.setTitle("Choice Dialog");
    	dialog.setHeaderText("Register a Student.");
    	dialog.setContentText("Choose a Student to register to this course:");
    	Optional<Student> result = dialog.showAndWait();
    	result.ifPresent(student -> registerAndUpdate(student));
    	
    }
    
    /**
     * Helper method to register a student to a course and updates the list view
     * @param student
     */
    private void registerAndUpdate(Student student) {
    	courseDisplayed.registerStudent(student);
    	DataController.saveCourse(courseDisplayed);
    	//Update ListView
    	updateStudentListView();
		return;
    }
    
    /**
     * Helper method to update the list view with newly registered students
     */
    private void updateStudentListView() {
    	studentObsList = FXCollections.observableArrayList();
		studentArrList = courseDisplayed.getStudents();
		studentArrList.forEach(stud -> {studentObsList.add(stud);} );
		LV_StudentList.setItems(studentObsList);
		return;
    }
    
    /**
     * Mouse click handler for list of students
     * Changes labels and tableView list of courses to match currently selected student
     * @param arg0
     */
    @FXML public void handleMouseClickStudents (MouseEvent arg0) {
    	Student studentSelection = LV_StudentList.getSelectionModel().getSelectedItem();
    	//setCourseList(studentSelection);
    	LL_NameSubtitle.setText(studentSelection.getFullName());
    	LL_IdSubtitle.setText(studentSelection.getUsername());
    	if(!(courseDisplayed.getSpecificStudentSubmissions(studentSelection.getUsername()).isEmpty())) {
    		LL_GradeSubtitle.setText(Float.toString(courseDisplayed.getStudentFinalGrade(studentSelection.getUsername())));
    	}
    	else {
    		LL_GradeSubtitle.setText("0.0");
    	}
    }
    
    /**
     * Helper method updates tableView list of courses for a selected student
     * @param student
     */
    private void setCourseList(Student student) {
     	coursesObsList = FXCollections.observableArrayList();
     	coursesArrList = student.getCourses();
     	coursesArrList.forEach(course -> {
             coursesObsList.add(course);
         });
         LV_Courses.setItems(coursesObsList);
    }
    
    

}
