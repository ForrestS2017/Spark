package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Assignment;
import model.Course;
import model.Course.Announcement;
import model.Student;
import util.DataController;

import java.util.ArrayList;

public class StudentCourseViewController extends BasicWindow {

    /***********************************************
     ************** Widget References **************
     ***********************************************/

    /***************
     * Shared Data *
     ***************/
    Course course;
    Student student;
    ObservableList<Assignment> Assignments;
    ObservableList<Student> Students;

    /*************************
     ***** Shared Widgets ****
     *************************/
    @FXML Label LL_Title;
    @FXML Label LL_Subtitle;
    @FXML TabPane TP_TabPane;
    @FXML Button BT_Back;

    /*************************
     ** Home Widgets **
     *************************/

    /////// Text-Based ///////
    @FXML ListView<Assignment> LV_UpcomingAssignments;
    @FXML ListView<Announcement> LV_AnnouncementList, LV_AnnouncementDetail;
    @FXML ListView<Student> LV_Classmates;
    @FXML Accordion ACC_Announcements;

    ////// Action-Based //////
    @FXML Button BN_ViewAssignmentDetails;


    public void start(Course course, Student student) {
        this.course = course;
        this.student = student;
        
        LL_Title.setText(course.getTitle());
        //LL_Subtitle.setText("Professor " + course.getProfessor().getLastName());
    }

    /*************************
     ** Assignments Actions **
     *************************/

    @FXML
    public void initAssignments() {}

    @FXML
    public void AddNewAssignment() {}

    @FXML
    public void PublishAssignment() {}

    @FXML
    public void EditPublishAssignment() {}


    /*************************
     ** Submissions Actions **
     *************************/

    @FXML
    public void initSubmissions() {}

    @FXML
    public void SubmitFeedback() {}


    /*************************
     * Announcements Actions *
     *************************/

    @FXML
    public void initAnnouncements() {}

    @FXML
    public void AddNewAnnouncement() {}

    @FXML
    public void PublishAnnouncement() {}

    @FXML
    public void EditPublishAnnouncement() {}


    /*************************
     **** Students Actions ***
     *************************/

    @FXML
    public void initStudents() {}

    @FXML
    public void SubmitFinalGrade() {}


    /****************
     * Util Methods *
     ****************/

    @FXML
    public void GoBack() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(LAYOUT_STUDENT_DASHBOARD_VIEW));
        LoadNewScene(loader, (Stage)LL_Title.getScene().getWindow());
        ProfessorDashboardController controller = (ProfessorDashboardController) loader.getController();
        controller.start(student.getUsername());
    }

}
