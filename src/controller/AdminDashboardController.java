package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import model.Course;
import model.Professor;
import model.Student;
import model.User;
import util.DataController;

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
    
    public void start(String username) {
		// Fill professor list view
    	professorList = FXCollections.observableArrayList();
    	userList = DataController.readUsers();
    	userList.forEach(user -> {
            if (user.getType().equals("professor")) professorList.add(user); });
        LV_ProfessorList.setItems(professorList);
    }
    
    @FXML
    void ConfigureUI(ActionEvent event) {

    }

    @FXML
    void CreateCourse(ActionEvent event) {

    }

    @FXML
    void CreateUser(ActionEvent event) {

    }

    @FXML
    void Logout(ActionEvent event) {
    	Logout();
    }

    @FXML
    void SearchStudent(ActionEvent event) {

    }

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
    
    @FXML public void handleMouseClick(MouseEvent arg0) {
        User selection = LV_ProfessorList.getSelectionModel().getSelectedItem();
    	courseObsList = FXCollections.observableArrayList();
    	studentsObsList = FXCollections.observableArrayList();
    	courseArrList = DataController.readCourses();
    	courseArrList.forEach(course -> {
            if (course.getProfessor().equals(selection)) {
            	courseObsList.add(course);
            	for(int i=0;i<course.getStudents().size(); i++){
            		studentsObsList.add(course.getStudents().get(i));
    			}
            }
        });
        LV_CoursesTaught.setItems(courseObsList);
        LV_StudentsTaught.setItems(studentsObsList);
        
        
    }


}