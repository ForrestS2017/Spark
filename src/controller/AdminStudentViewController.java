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
    private ListView<?> LV_Courses;

    @FXML
    private Label LL_CoursesTItle;

    @FXML
    private Button BN_RegisterStudent;
    
    String username;
    Course courseDisplayed;
    User professorPreviouslyDisplayed;
    private ArrayList<Student> studentArryList;
    private ObservableList<Student> studentObsList;
    
    public void start(Course course, User professor) {
		this.courseDisplayed = course;
		this.professorPreviouslyDisplayed = professor;
		LL_Title.setText(course.getTitle());
		//Fill Student List
		studentObsList = FXCollections.observableArrayList();
		studentArryList = course.getStudents();
		
    }
    
    @FXML
    void GoBack(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(LAYOUT_ADMIN_COURSE_VIEW));
        LoadNewScene(loader);
        AdminCourseViewController controller = (AdminCourseViewController) loader.getController();
        controller.start(professorPreviouslyDisplayed);
    }

    @FXML
    void Logout(ActionEvent event) {
    	Logout();
    }

    @FXML
    void RegisterStudent(ActionEvent event) {
    	ArrayList<User> users = new ArrayList<>(DataController.readUsers());
    	ArrayList<Student> choices = new ArrayList<>();
    	for(User u : users) {
    		if(u.getType().equals("STUDENT")) {
    			choices.add((Student)u);
    		}
    	}
    	
    	ChoiceDialog<Student> dialog = new ChoiceDialog<>(null,choices);
    	dialog.setTitle("Choice Dialog");
    	dialog.setHeaderText("Register a Student.");
    	dialog.setContentText("Choose a Student to register to this course:");
    	Optional<Student> result = dialog.showAndWait();
    	result.ifPresent(student -> courseDisplayed.registerStudent(student));
    	DataController.saveCourse(courseDisplayed);
    	
    }
    
    

}
