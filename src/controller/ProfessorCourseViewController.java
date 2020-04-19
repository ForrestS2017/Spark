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
    Button BN_FeedbackSubmit;
    @FXML
    Button BN_FeedbackDelete;

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
    Label LL_FinalGradeTitle;
    @FXML
    Label LL_FinalGrade;
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
    Button BN_FinalGradeSubmit;
    @FXML
    Button BN_FinalGradeDelete;


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
        if (TF_AssignmentTitle.getText().isBlank()
                || TA_AssignmentDescription.getText().isBlank()
                || DP_AssignmentDueDate.getValue() == null) return;

        Assignment newAssignment = new Assignment(
                TF_AssignmentTitle.getText(),
                TA_AssignmentDescription.getText(),
                DP_AssignmentDueDate.getValue().atTime(11, 55)
        );
        if (course.addAssignment(newAssignment) == false) {
            course.removeAssignment(newAssignment);
            course.addAssignment(newAssignment);
            return;
        }
        assignmentList = FXCollections.observableArrayList(course.getAssignments());
        LV_AssignmentListAssignments.setItems(assignmentList);
        System.out.println("Published: " + TF_AssignmentTitle.getText());
        DataController.saveCourse(course);
    }

    @FXML
    public void DeleteAssignment() {
        Assignment assignment = LV_AssignmentListAssignments.getSelectionModel().getSelectedItem();
        if (assignment == null) return;
        course.removeAssignment(assignment);
        assignmentList = FXCollections.observableArrayList(course.getAssignments());
        LV_AssignmentListAssignments.setItems(assignmentList);
        DataController.saveCourse(course);
        LV_AssignmentListAssignments.getSelectionModel().clearSelection();
        TA_AssignmentDescription.clear();
        TF_AssignmentTitle.clear();
        DP_AssignmentDueDate.setValue(null);
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
            LL_NoSubmissionsSubmissions.setText("No Assignments");
            return;
        }

        assignmentList = FXCollections.observableArrayList(course.getAssignments());
        LV_AssignmentListSubmissions.setItems(assignmentList);
        AN_AssignmentSubmissions.setVisible(false);

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
    }

    @FXML
    public void SubmitFeedback() {
        if (AN_AssignmentSubmissions.getExpandedPane() == null) {
            return;
        }
        String feedback = TA_FeedbackDescription.getText();
        long grade = Long.parseLong(TF_FeedbackGrade.getText());
        SubmissionPane pane = (SubmissionPane) AN_AssignmentSubmissions.getExpandedPane();
        Assignment.Submission submission = pane.getSubmission();
        submission.assignFeedback(feedback);
        submission.assignGrade(grade);
        course.updateAutomaticGrade(submission.getUsername());
        Assignment selectedItem = LV_AssignmentListSubmissions.getSelectionModel().getSelectedItem();
        LV_AssignmentListSubmissions.getSelectionModel().clearSelection();
        LV_AssignmentListSubmissions.getSelectionModel().select(selectedItem);
        DataController.saveCourse(course);
    }

    @FXML
    public void DeleteFeedback() {
        if (AN_AssignmentSubmissions.getExpandedPane() == null) {
            return;
        }
        SubmissionPane pane = (SubmissionPane) AN_AssignmentSubmissions.getExpandedPane();
        Assignment.Submission submission = pane.getSubmission();
        submission.removeFeedback();
        Assignment selectedItem = LV_AssignmentListSubmissions.getSelectionModel().getSelectedItem();
        LV_AssignmentListSubmissions.getSelectionModel().clearSelection();
        LV_AssignmentListSubmissions.getSelectionModel().select(selectedItem);
        course.updateAutomaticGrade(submission.getUsername());
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Another announcement already has this title!");
            alert.setHeaderText("Failed to add announcement");
            alert.showAndWait();
            return;
        }
        announcementList = FXCollections.observableArrayList(course.getAnnouncements());
        LV_AnnouncementList.setItems(announcementList);
        System.out.println("Published: " + TF_AssignmentTitle.getText());
        DataController.saveCourse(course);
    }

    @FXML
    public void EditPublishAnnouncement() {
        if (TF_AnnouncementTitle.getText().isBlank() || TA_AnnouncementDescription.getText().isBlank()) return;

        Course.Announcement newAnnouncement = new Course.Announcement(
                TF_AnnouncementTitle.getText(),
                TA_AnnouncementDescription.getText()
        );

        if (course.addAnnouncement(newAnnouncement) == false) {
            course.removeAnnouncement(newAnnouncement);
            course.addAnnouncement(newAnnouncement);
        }
        announcementList = FXCollections.observableArrayList(course.getAnnouncements());
        LV_AnnouncementList.setItems(announcementList);
        System.out.println("Published: " + TF_AssignmentTitle.getText());
        DataController.saveCourse(course);
    }

    @FXML
    public void DeleteAnnouncement() {
        Course.Announcement announcement = LV_AnnouncementList.getSelectionModel().getSelectedItem();
        if (announcement == null) return;
        course.removeAnnouncement(announcement);
        announcementList = FXCollections.observableArrayList(course.getAnnouncements());
        LV_AnnouncementList.setItems(announcementList);
        LV_AnnouncementList.refresh();
        DataController.saveCourse(course);
        TA_AnnouncementDescription.clear();
        TF_AnnouncementTitle.clear();
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
            LL_NoSubmissionsStudents.setText("No Students");
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
            ArrayList<Assignment.Submission> submissions = course.getSpecificStudentSubmissions(t1.getUsername());
            int totalAss = course.getAssignments().size();
            if (submissions.isEmpty() == false) {
                AN_StudentSubmissions.setVisible(true);
                LL_NoSubmissionsStudents.setVisible(false);
                for (Assignment.Submission s : submissions) {
                    SubmissionPane pane = new SubmissionPane(s, new TextArea(s.getSubmissionText()));
                    AN_StudentSubmissions.getPanes().add(pane);
                }

                LL_AssignmentsCompletedBody.setTextFill(Color.BLACK);
                LL_CalculatedGradeBody.setTextFill(Color.BLACK);
                LL_AssignmentsCompletedBody.setText(
                        course.getSpecificStudentSubmissions(t1.getUsername()).size() + "/" + totalAss);
                LL_CalculatedGradeBody.setText(String.format("%.2f", course.getStudentAutomaticGrade(t1.getUsername())));
                float finalGPA = course.getStudentFinalGrade(t1.getUsername());
                String gpaString = finalGPA >= 0.0f ? String.format("%.2f", finalGPA) : "--";
                LL_FinalGrade.setText(gpaString);
                LL_FinalGrade.setTextFill(Color.BLACK);
            } else {
                LL_FullNameBody.setTextFill(Color.RED);
                AN_StudentSubmissions.getPanes().clear();
                AN_StudentSubmissions.setVisible(false);
                LL_NoSubmissionsStudents.setVisible(true);
                LL_AssignmentsCompletedBody.setText("0/" + totalAss);
                LL_AssignmentsCompletedBody.setTextFill(Color.RED);
                LL_CalculatedGradeBody.setText("0.0");
                LL_CalculatedGradeBody.setTextFill(Color.RED);
                float potentialFinalGrade = course.getStudentFinalGrade(t1.getUsername());
                if (course.getStudentFinalGrade(t1.getUsername()) >= 0.0) {
                    LL_FinalGrade.setText(String.format("%.2f", potentialFinalGrade));
                    LL_FinalGrade.setTextFill(Color.ORANGE);
                } else {
                    LL_FinalGrade.setText("0.0");
                    LL_FinalGrade.setTextFill(Color.RED);
                }
            }
        }));
        LV_StudentList.getSelectionModel().selectFirst();
        RefreshClassAnalytics();


        // Calculate & Init Stats
    }


    @FXML
    public void SubmitFinalGrade() {
        String input = TF_AdjustedGrade.getText();
        float finalGPA = 0.0f;
        if (input.isBlank() == false) {
            try {
                finalGPA = Float.parseFloat(input);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Grade must be a number");
                alert.setHeaderText("Invalid Adjusted Grade");
                alert.showAndWait();
                TF_AdjustedGrade.clear();
            }
        } else {
            finalGPA = Float.parseFloat(LL_CalculatedGradeBody.getText());
        }
        String studentID = LV_StudentList.getSelectionModel().getSelectedItem().getUsername();
        course.setFinalGrade(studentID, finalGPA);
        if (course.getSpecificStudentSubmissions(studentID).size() < 1) {
            LL_FinalGrade.setTextFill(Color.ORANGE);
        } else {
            LL_FinalGrade.setTextFill(Color.BLACK);
        }
        LL_FinalGrade.setText(String.format("%.2f", finalGPA));
        DataController.saveCourse(course);
        RefreshClassAnalytics();
        TF_AdjustedGrade.clear();
        LV_StudentList.refresh();
    }

    @FXML
    public void DeleteFinalGrade() {
        String studentID = LV_StudentList.getSelectionModel().getSelectedItem().getUsername();
        if (course.getStudentFinalGrade(studentID) < 0f) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Cannot remove non-existent final grade");
            alert.setHeaderText("Grade Removal");
            alert.showAndWait();
            return;
        }
        course.removeFinalGrade(studentID);
        if (course.getSpecificStudentSubmissions(studentID).size() < 1) {
            LL_FinalGrade.setText("0.0");
            LL_FinalGrade.setTextFill(Color.RED);
        } else {
            LL_FinalGrade.setText("--");
            LL_FinalGrade.setTextFill(Color.BLACK);
        }
        RefreshClassAnalytics();
        DataController.saveCourse(course);
        TF_AdjustedGrade.clear();
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

    protected void RefreshClassAnalytics() {
        LL_AnalyticsAverageBody.setText(Float.toString(course.getClassAverage()));
        LL_AnalyticsMedianBody.setText(Float.toString(course.getClassMedian()));
        LL_AnalyticsRangeBody.setText(String.format("{%.2f,%.2f}", course.getClassMinGrade(), course.getClassMaxGrade()));
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
