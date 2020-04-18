package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import model.Assignment;
import model.Course;
import model.Student;
import util.DataController;

import java.util.ArrayList;

public class ProfessorCourseViewController extends BasicWindow {

    /***********************************************
     ************** Widget References **************
     ***********************************************/

    /***************
     * Shared Data *
     ***************/
    Course course;
    ObservableList<Assignment> assignmentList = FXCollections.observableArrayList();
    ObservableList<Course.Announcement> announcementList = FXCollections.observableArrayList();
    ObservableList<Student> studentList = FXCollections.observableArrayList();

    /*************************
     ***** Shared Widgets ****
     *************************/
    @FXML Label LL_Title;
    @FXML Label LL_Subtitle;
    @FXML TabPane TP_TabPane;
    @FXML Button BT_Back;

    /*************************
     ** Assignments Widgets **
     *************************/

    /////// Text-Based ///////
    @FXML ListView<Assignment> LV_AssignmentListAssignments;
    @FXML TextArea TA_AssignmentDescription;
    @FXML TextField TF_AssignmentTitle;
    @FXML DatePicker DP_AssignmentDueDate;

    ////// Action-Based //////
    @FXML Button BN_NewAssignment;
    @FXML Button BN_AssignmentPublish;
    @FXML Button BN_AssignmentEditPublish;

    /*************************
     ** Submissions Widgets **
     *************************/

    /////// Text-Based ///////
    @FXML ListView<Assignment> LV_AssignmentListSubmissions;
    @FXML TextArea TA_FeedbackDescription;
    @FXML TextField TF_FeedbackGrade;
    @FXML Accordion AN_AssignmentSubmissions;

    ////// Action-Based //////
    @FXML Button BN_SubmitFeedback;

    /*************************
     * Announcements Widgets *
     *************************/

    /////// Text-Based ///////
    @FXML ListView<Course.Announcement> LV_AnnouncementList;
    @FXML TextArea TA_AnnouncementDescription;
    @FXML TextField TF_AnnouncementTitle;

    ////// Action-Based //////
    @FXML Button BN_NewAnnouncement;
    @FXML Button BN_AnnouncementPublish;
    @FXML Button BN_AnnouncementEditPublish;

    /*************************
     **** Students Widgets ***
     *************************/

    /////// Text-Based ///////
    @FXML ListView<Student> LV_StudentList;
    @FXML Accordion AN_StudentSubmissions;
    @FXML TextField TF_AdjustedGrade;

    @FXML Label LL_FullNameBody;
    @FXML Label LL_AssignmentsCompletedBody;
    @FXML Label LL_CalculatedGradeBody;
    @FXML Label LL_AnalyticsAverageBody;
    @FXML Label LL_AnalyticsRangeBody;
    @FXML Label LL_AnalyticsMedianBody;

    ////// Action-Based //////
    @FXML Button BN_SubmitFinalGrade;


    /***********************************************
     ****** Widget Methods & Events Listeners ******
     ***********************************************/

    public void start(String inputCourseName) {
        ArrayList<Course> allCourses = DataController.readCourses();
        for (Course c : allCourses ) {
            if (c.getTitle().equals(inputCourseName)) {
                course = c;
                break;
            }
        }
        if (course == null) {
            System.out.println("WARN: FAILED TO LOAD CLASS: " + inputCourseName);   //TODO: CHANGE TO PROMPT
            return;
        }

        // Init Titles
        LL_Title.setText(course.getTitle());
        LL_Subtitle.setText(TP_TabPane.getSelectionModel().getSelectedItem().getText());

        // Init Lists
        course.getAssignments().forEach(a -> assignmentList.add(a));
        course.getAnnouncements().forEach(a -> announcementList.add(a));
        course.getStudents().forEach(a -> studentList.add(a));
    }

    /*************************
     ** Assignments Actions **
     *************************/

    @FXML
    public void initAssignments() {
        System.out.println("initAssignments");
    }

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
    public void initSubmissions() {
        System.out.println("initSubmissions");
    }

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
        loader.setLocation(this.getClass().getResource(LAYOUT_PROFESSOR_DASHBOARD_VIEW));
        LoadNewScene(loader);
        ProfessorDashboardController controller = (ProfessorDashboardController) loader.getController();
        controller.start(course.getProfessorUsername());
    }

}
