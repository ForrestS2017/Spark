package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Assignment;
import model.Course;
import model.Student;

public class ProfessorCourseView {

    /***********************************************
     ************** Widget References **************
     ***********************************************/

    /***************
     * Shared Data *
     ***************/
    Course course;
    ObservableList<Assignment> Assignments;
    ObservableList<Student> Students;

    /*************************
     ***** Shared Widgets ****
     *************************/
    @FXML Label LL_Title;
    @FXML Label LL_Subtitle;

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

    @FXML
    public void initialize() {
        /**
         * TODO:
         *  - Fill @LV_Title, @LL_Subtitle
         *  - Focus on Assignments Tab
         */
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

}
