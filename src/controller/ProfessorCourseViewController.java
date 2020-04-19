package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
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
    @FXML
    Label LL_Title;
    @FXML
    Label LL_Subtitle;
    @FXML
    TabPane TP_TabPane;
    @FXML
    Button BT_Back;

    /*************************
     ** Assignments Widgets **
     *************************/

    /////// Text-Based ///////
    @FXML
    ListView<Assignment> LV_AssignmentListAssignments;
    @FXML
    TextArea TA_AssignmentDescription;
    @FXML
    TextField TF_AssignmentTitle;
    @FXML
    DatePicker DP_AssignmentDueDate;

    ////// Action-Based //////
    @FXML
    Button BN_NewAssignment;
    @FXML
    Button BN_AssignmentPublish;
    @FXML
    Button BN_AssignmentEditPublish;
    @FXML
    Button BN_AssignmentDelete;

    /*************************
     ** Submissions Widgets **
     *************************/

    /////// Text-Based ///////
    @FXML
    ListView<Assignment> LV_AssignmentListSubmissions;
    @FXML
    TextArea TA_FeedbackDescription;
    @FXML
    TextField TF_FeedbackGrade;
    @FXML
    Accordion AN_AssignmentSubmissions;
    @FXML
    Label LL_NoSubmissionsSubmissions;

    ////// Action-Based //////
    @FXML
    Button BN_SubmitFeedback;

    /*************************
     * Announcements Widgets *
     *************************/

    /////// Text-Based ///////
    @FXML
    ListView<Course.Announcement> LV_AnnouncementList;
    @FXML
    TextArea TA_AnnouncementDescription;
    @FXML
    TextField TF_AnnouncementTitle;

    ////// Action-Based //////
    @FXML
    Button BN_NewAnnouncement;
    @FXML
    Button BN_AnnouncementPublish;
    @FXML
    Button BN_AnnouncementEditPublish;
    @FXML
    Button BN_AnnouncementDelete;

    /*************************
     **** Students Widgets ***
     *************************/

    /////// Text-Based ///////
    @FXML
    ListView<Student> LV_StudentList;
    @FXML
    Accordion AN_StudentSubmissions;
    @FXML
    TextField TF_AdjustedGrade;

    @FXML
    Label LL_FullNameBody;
    @FXML
    Label LL_AssignmentsCompletedBody;
    @FXML
    Label LL_CalculatedGradeBody;
    @FXML
    Label LL_AnalyticsAverageBody;
    @FXML
    Label LL_AnalyticsRangeBody;
    @FXML
    Label LL_AnalyticsMedianBody;
    @FXML
    Label LL_NoSubmissionsStudents;

    ////// Action-Based //////
    @FXML
    Button BN_SubmitFinalGrade;


    /***********************************************
     ****** Widget Methods & Events Listeners ******
     ***********************************************/

    public void start(String inputCourseName) {
        ArrayList<Course> allCourses = DataController.readCourses();
        for (Course c : allCourses) {
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
        LL_NoSubmissionsSubmissions.setVisible(false);
        LL_NoSubmissionsStudents.setVisible(false);

        // Init Lists
        course.getAssignments().forEach(a -> assignmentList.add(a));
        course.getAnnouncements().forEach(a -> announcementList.add(a));
        course.getStudents().forEach(a -> studentList.add(a));

        initAssignments();
    }

    /*************************
     ** Assignments Actions **
     *************************/

    /**
     * Note, this method executes before start(). So we must call it in start, as initialize() can't take params
     */
    @FXML
    public void initAssignments() {
        if (course == null) return;
        // Init Assignment List
        assignmentList.clear();
        if (course.getAssignments() == null || course.getAssignments().size() < 1) return;
        assignmentList = FXCollections.observableArrayList(course.getAssignments());

        LV_AssignmentListAssignments.setItems(assignmentList);

        // Init listview onSelect
        LV_AssignmentListAssignments.getSelectionModel().selectedItemProperty().addListener((observableValue, assignment, t1) -> {
            //System.out.println("ass: " + assignment.getTitle());
            if (t1 != null) {
                System.out.println("T1: " + t1.getTitle());
                TA_AssignmentDescription.setText(t1.getDescription());
                TF_AssignmentTitle.setText(t1.getTitle());
                DP_AssignmentDueDate.setValue(t1.getDueDate().toLocalDate());
            }
        });

        //TODO: Refactor below into a method to call for the selection model!

        // Display selected assignment
        LV_AssignmentListAssignments.getSelectionModel().selectFirst();
        Assignment currAss = LV_AssignmentListAssignments.getSelectionModel().getSelectedItem();
        TA_AssignmentDescription.setText(currAss.getDescription());
        TF_AssignmentTitle.setText(currAss.getTitle());
        DP_AssignmentDueDate.setValue(currAss.getDueDate().toLocalDate());

    }

    @FXML
    public void AddNewAssignment() {
        // Clear input fields & selection
        LV_AssignmentListAssignments.getSelectionModel().clearSelection();
        TA_AssignmentDescription.clear();
        TF_AssignmentTitle.clear();
        DP_AssignmentDueDate.setValue(null);
    }

    @FXML
    public void PublishAssignment() {
        if (TF_AssignmentTitle.getText().isBlank()
                || TA_AssignmentDescription.getText().isBlank()
                || DP_AssignmentDueDate.getValue() == null) return;

        Assignment newAssignment = new Assignment(
                TF_AssignmentTitle.getText(),
                TA_AssignmentDescription.getText(),
                DP_AssignmentDueDate.getValue().atTime(11, 55)
        );
        if (course.addAssignment(newAssignment) == false) {
            System.out.println("Failed to publish");
            return;
        }
        assignmentList = FXCollections.observableArrayList(course.getAssignments());
        LV_AssignmentListAssignments.setItems(assignmentList);
        System.out.println("Published: " + TF_AssignmentTitle.getText());
        DataController.saveCourse(course);
    }

    @FXML
    public void EditPublishAssignment() {
    }

    @FXML
    public void DeleteAssignment() {
    }


    /*************************
     ** Submissions Actions **
     *************************/

    @FXML
    public void initSubmissions() {
        // Init Assignment List
        assignmentList.clear();
        if (course.getAssignments() == null || course.getAssignments().size() < 1) {
            AN_AssignmentSubmissions.getPanes().clear();
            AN_AssignmentSubmissions.setVisible(false);
            LL_NoSubmissionsSubmissions.setVisible(true);
            return;
        }

        assignmentList = FXCollections.observableArrayList(course.getAssignments());
        LV_AssignmentListSubmissions.setItems(assignmentList);

        /**
         * List Listener
         */
        LV_AssignmentListSubmissions.getSelectionModel().selectedItemProperty().addListener((observableValue, assignment, t1) -> {
            //System.out.println("ass: " + assignment.getTitle());
            if (t1 == null) return;
            ArrayList<Assignment.Submission> submissions = t1.getStudentSubmissions();
            TA_FeedbackDescription.clear();
            TF_FeedbackGrade.clear();
            if (submissions.isEmpty() == false) {
                AN_AssignmentSubmissions.setVisible(true);
                LL_NoSubmissionsSubmissions.setVisible(false);
                AN_AssignmentSubmissions.getPanes().clear();
                for (Assignment.Submission s : submissions) {
                    //String title = s.toString();
                    SubmissionPane pane = new SubmissionPane(s, new TextArea(s.getSubmissionText()));
                    AN_AssignmentSubmissions.getPanes().add(pane);
                }
            } else {
                AN_AssignmentSubmissions.getPanes().clear();
                AN_AssignmentSubmissions.setVisible(false);
                LL_NoSubmissionsSubmissions.setVisible(true);
            }
        });
        LV_AssignmentListSubmissions.getSelectionModel().selectFirst();

        /**
         * Accordian Listener
         */
        AN_AssignmentSubmissions.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
            @Override
            public void changed(ObservableValue<? extends TitledPane> observableValue, TitledPane titledPane, TitledPane t1) {
                if (t1 == null) return;
                SubmissionPane sPane = (SubmissionPane) t1;
                Assignment.Submission s = sPane.getSubmission();
                TA_FeedbackDescription.setText(s.getFeedbackText());
                TF_FeedbackGrade.setText(Long.toString(s.getGrade()));
            }
        });


        //TODO: Refactor below into a method to call for the selection model!


    }

    @FXML
    public void SubmitFeedback() {
        String feedback = TA_FeedbackDescription.getText();
        long grade = Long.parseLong(TF_FeedbackGrade.getText());
        SubmissionPane pane = (SubmissionPane) AN_AssignmentSubmissions.getExpandedPane();
        Assignment.Submission submission = pane.getSubmission();
        submission.assignFeedback(feedback);
        submission.assignGrade(grade);
        Assignment selectedItem = LV_AssignmentListSubmissions.getSelectionModel().getSelectedItem();
        LV_AssignmentListSubmissions.getSelectionModel().clearSelection();
        LV_AssignmentListSubmissions.getSelectionModel().select(selectedItem);
        DataController.saveCourse(course);
    }


    /*************************
     * Announcements Actions *
     *************************/

    @FXML
    public void initAnnouncements() {
        if (course == null) return;

        // Init Assignment List
        announcementList.clear();
        if (course.getAnnouncements() == null || course.getAnnouncements().size() < 1) return;
        announcementList = FXCollections.observableArrayList(course.getAnnouncements());
        LV_AnnouncementList.setItems(announcementList);

        // Init list view onSelect
        LV_AnnouncementList.getSelectionModel().selectedItemProperty().addListener((observableValue, announcement, t1) -> {
            //System.out.println("ass: " + assignment.getTitle());
            if (t1 != null) {
                System.out.println("T1: " + t1.getTitle());
                TA_AnnouncementDescription.setText(t1.getDescription());
                TF_AnnouncementTitle.setText(t1.getTitle());
            }
        });

        // Display selected assignment
        LV_AnnouncementList.getSelectionModel().selectFirst();
    }

    @FXML
    public void AddNewAnnouncement() {
        // Clear input fields
        TA_AnnouncementDescription.clear();
        TF_AnnouncementTitle.clear();
    }

    @FXML
    public void PublishAnnouncement() {
        if (TF_AnnouncementTitle.getText().isBlank() || TA_AnnouncementDescription.getText().isBlank()) return;

        Course.Announcement newAnnouncement = new Course.Announcement(
                TF_AnnouncementTitle.getText(),
                TA_AnnouncementDescription.getText()
        );

        if (course.addAnnouncement(newAnnouncement) == false) {
            System.out.println("Failed to publish");
            return;
        }
        announcementList = FXCollections.observableArrayList(course.getAnnouncements());
        LV_AnnouncementList.setItems(announcementList);
        System.out.println("Published: " + TF_AssignmentTitle.getText());
        DataController.saveCourse(course);
    }

    @FXML
    public void EditPublishAnnouncement() {
    }

    @FXML
    public void DeleteAnnouncement() {

    }


    /*************************
     **** Students Actions ***
     *************************/

    @FXML
    public void initStudents() {
        // Init Student List
        studentList.clear();
        if (course.getStudents() == null || course.getStudents().size() < 1) {
            AN_StudentSubmissions.getPanes().clear();
            AN_StudentSubmissions.setVisible(false);
            LL_NoSubmissionsStudents.setVisible(true);
            return;
        }

        studentList = FXCollections.observableArrayList(course.getStudents());
        LV_StudentList.setItems(studentList);

        //TODO: Refactor below into a method to call for the selection model!

        /**
         * List listener
         */
        LV_StudentList.getSelectionModel().selectedItemProperty().addListener(((observableValue, student, t1) -> {
            if (t1 == null) return;
            AN_StudentSubmissions.getPanes().clear();
            LL_FullNameBody.setText(t1.getFullName());
            ArrayList<Assignment.Submission> submissions = getStudentSubmissions(t1);
            float GPA = 0.0f;
            int totalAss = course.getAssignments().size();
            if (submissions.isEmpty() == false) {
                AN_StudentSubmissions.setVisible(true);
                LL_NoSubmissionsStudents.setVisible(false);
                for (Assignment.Submission s : submissions) {
                    SubmissionPane pane = new SubmissionPane(s, new TextArea(s.getSubmissionText()));
                    AN_StudentSubmissions.getPanes().add(pane);
                    GPA += s.getGrade();
                }
                // Calculate GPA and stuff
                int compAss = submissions.size();

                LL_AssignmentsCompletedBody.setTextFill(Color.BLACK);
                LL_CalculatedGradeBody.setTextFill(Color.BLACK);
                LL_AssignmentsCompletedBody.setText(compAss + "/" + totalAss);
                LL_CalculatedGradeBody.setText(String.format("%.2f", GPA / totalAss));
            } else {
                LL_FullNameBody.setTextFill(Color.RED);
                AN_StudentSubmissions.getPanes().clear();
                AN_StudentSubmissions.setVisible(false);
                LL_NoSubmissionsStudents.setVisible(true);
                LL_AssignmentsCompletedBody.setText("0/" + totalAss);
                LL_AssignmentsCompletedBody.setTextFill(Color.RED);
                LL_CalculatedGradeBody.setText("0.0");
                LL_CalculatedGradeBody.setTextFill(Color.RED);
            }


        }));
        LV_StudentList.getSelectionModel().selectFirst();


        // Calculate & Init Stats
    }


    @FXML
    public void SubmitFinalGrade() {
        String input = TF_AdjustedGrade.getText();
        float finalGPA = 0.0f;
        if (input.isBlank() == false) {
            finalGPA = Float.parseFloat(input);
        } else {
            finalGPA = Float.parseFloat(LL_CalculatedGradeBody.getText());
        }
        Student student = LV_StudentList.getSelectionModel().getSelectedItem();
        course.addGrade(student.getUsername(), finalGPA);
        LL_AnalyticsAverageBody.setText(Float.toString(course.getClassAverage()));
        LL_AnalyticsMedianBody.setText(Float.toString(course.getClassMedian()));
        LL_AnalyticsRangeBody.setText(String.format("{%.2f,%.2f}", course.getClassMinGrade(), course.getClassMaxGrade()));
        DataController.saveCourse(course);
        LV_StudentList.refresh();
    }


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

    private ArrayList<Assignment.Submission> getStudentSubmissions(Student student) {
        ArrayList<Assignment.Submission> studentSubs = new ArrayList<>();
        for (Assignment ass : course.getAssignments()) {
            for (Assignment.Submission sub : ass.getStudentSubmissions()) {
                if (sub.getUsername().equals(student.getUsername())) {
                    studentSubs.add(sub);
                }
            }
        }
        return studentSubs;
    }

    private class SubmissionPane extends TitledPane {
        Assignment.Submission submission;

        public SubmissionPane(Assignment.Submission submission, Node node) {
            super(submission.toString(), node);
            this.submission = submission;
        }

        public Assignment.Submission getSubmission() {
            return submission;
        }
    }

}
