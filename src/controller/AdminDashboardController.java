package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;

public class AdminDashboardController {

    @FXML
    private Rectangle decorBox;

    @FXML
    private Label LL_Title;

    @FXML
    private Label LL_Subtitle;

    @FXML
    private Label LL_NoCourses;

    @FXML
    private ListView<?> LV_ProfessorList;

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
    private ListView<?> LV_CoursesTaught;

    @FXML
    private ListView<?> LV_StudentsTaught;

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

    }

    @FXML
    void SearchStudent(ActionEvent event) {

    }

    @FXML
    void StudentNameID(ActionEvent event) {

    }

    @FXML
    void ViewProfessor(ActionEvent event) {

    }

}