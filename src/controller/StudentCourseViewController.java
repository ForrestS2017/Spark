/**
 * @author Luis Guzman
 */
package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Assignment;
import model.Assignment.Submission;
import model.Course;
import model.Course.Announcement;
import model.Student;
import util.DataController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    @FXML Button BT_Back, BT_Logout;

    /*************************
     ** Home Page Widgets **
     *************************/
    @FXML ListView<Announcement> LV_AnnouncementList, LV_AnnouncementDetail;
    @FXML ListView<String> LV_Classmates;
    @FXML Accordion ACC_Announcements;
    @FXML Label LL_NoAnnouncements;
    @FXML Button BN_ViewAssignmentDetails;
    @FXML TableView<Assignment> tblUpComingAssignments;
    
    /*************************
     ** Assignment Page Widgets **
     *************************/
    @FXML TableView<Assignment> tblAllAssignments;
    @FXML Label LL_AssignmentTitle, LL_AssignmentDateDue, LL_AssignmentSubmissionDate, LL_AssignmentStatus, LL_AssignmentInstructions;
    @FXML TextArea TA_AssignmentSubmission;
    @FXML Button BN_AssignmentPublish;
    
    /*************************
     ** Student Grades Page Widgets **
     *************************/
    @FXML TableView<Assignment> tblAssignmentSubmissions;
    @FXML TextArea TA_Feedback;
    @FXML Label LL_FeedbackStatus, LL_SubmissionGrade, LL_CourseGrade, LL_AssignmentsCompleted,
    			LL_AnalyticsAverageBody, LL_AnalyticsRangeBody, LL_AnalyticsMedianBody;
    

    /**
     * Initialize controller with course and student information
     * @param course selected course to view
     * @param student selected student accessing course
     */
    public void start(Course course, Student student) {
        this.course = course;
        this.student = student;

        LL_Title.setText(course.getTitle());
        LL_Subtitle.setText("Professor " + course.getProfessor().getLastName());
        
        course.publishAssignment(new Assignment("Assignment Test #2", "Desc. of assignment test", LocalDateTime.of(LocalDate.of(2020, 04, 21), LocalTime.of(13, 40))));
        
        initHomePage();
        initAssignments();
        initGrades();
    }

    /*************************
     ** Home Page Actions **
     *************************/
    
    /**
     * Initializes Home Tab with appropriate information for each residing element
     */
    public void initHomePage() {
    	if(course == null)
    		return;
    	
    	// Load Accordion with Announcements
    	ArrayList<TitledPane> panes = new ArrayList<TitledPane>();
    	ArrayList<Announcement> allAnnouncements = course.getAnnouncements();
    	
    	if(allAnnouncements == null || allAnnouncements.size() == 0) {
    		LL_NoAnnouncements.setVisible(true);
    		// TODO: Display overlaying message
    		System.out.println("Course has no announcements!");
    	}
    	else {
    		for(Announcement a : course.getAnnouncements()) {
        		String headerPane = a.getTitle() + "\t\t\t\t" + a.getPublishDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"));
        		TitledPane p = new TitledPane(headerPane, new Label(a.getDescription()));
        		panes.add(p);
        	}
    		ACC_Announcements.getPanes().addAll(panes);
    	}
    	
    	// Load ListView with Classmates
    	ObservableList<String> obsListClassmates = FXCollections.observableArrayList();
    	ArrayList<String> classmateUsernames = course.getRegisteredStudents();
    	//classmateUsernames.remove(student.getUsername());	// remove current student user name
    	obsListClassmates.setAll(classmateUsernames);
    	LV_Classmates.setItems(obsListClassmates);
    	LV_Classmates.setMouseTransparent(true);			// disables highlighting
    	LV_Classmates.setFocusTraversable(false);
    	
        // Load upcoming Assignments TableView
    	TableColumn<Assignment, String> tblCol_AssignmentTitle = new TableColumn<>("Assignment Title");
    	tblCol_AssignmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
    	tblCol_AssignmentTitle.setPrefWidth(450);
    	
    	TableColumn<Assignment, String> tblCol_AssignmentDueDate = new TableColumn<>("Due Date");
    	tblCol_AssignmentDueDate.setCellValueFactory(a -> new SimpleStringProperty(a.getValue().getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"))));
    	tblCol_AssignmentDueDate.setPrefWidth(300);
    	tblCol_AssignmentDueDate.setSortType(TableColumn.SortType.ASCENDING);
    	
    	tblUpComingAssignments.getColumns().add(tblCol_AssignmentTitle);
    	tblUpComingAssignments.getColumns().add(tblCol_AssignmentDueDate);
    	
    	ArrayList<Assignment> incompletAssignments = new ArrayList<Assignment>();
    	for(Assignment a : course.getAssignments()) {
    		if(a.searchStudentSubmission(student.getUsername()) == null)
    			incompletAssignments.add(a);
    	}
    	tblUpComingAssignments.getItems().addAll(incompletAssignments);

    	tblUpComingAssignments.getSortOrder().add(tblCol_AssignmentDueDate);
    	tblUpComingAssignments.sort();
    	
    }
    
    /**
     * Action taken for when "View Assignment Details" button is clicked on the Home Tab
     */
    @FXML
    public void ViewAssignmentDetails() {
    	// Read selected index from ListView
    	TableViewSelectionModel<Assignment> selectionModel = tblUpComingAssignments.getSelectionModel();
    	Assignment assignment = selectionModel.getSelectedItems().get(0);

    	int selectedIndex = tblUpComingAssignments.getSelectionModel().getSelectedIndices().get(0);
        
        // Retrieve appropriate Assignment object
        TP_TabPane.getSelectionModel().selectNext();
        tblAllAssignments.getSelectionModel().select(selectedIndex);
    }

    
    /*************************
     ** Assignment Page Actions **
     *************************/
    
    /**
     * Initializes Assignments Tab with appropriate information for each residing element
     */
    public void initAssignments() {
    	// Load table with assignments
    	TableColumn<Assignment, String> tblCol_AssignmentTitle = new TableColumn<>("Assignment Title");
    	tblCol_AssignmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
    	tblCol_AssignmentTitle.setPrefWidth(200);
    	
    	TableColumn<Assignment, String> tblCol_AssignmentDueDate = new TableColumn<>("Due Date");
    	tblCol_AssignmentDueDate.setCellValueFactory(a -> new SimpleStringProperty(a.getValue().getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"))));
    	tblCol_AssignmentDueDate.setPrefWidth(145);
    	tblCol_AssignmentDueDate.setSortType(TableColumn.SortType.ASCENDING);
    	
    	TableColumn<Assignment, String> tblCol_AssignmentStatus = new TableColumn<>("Status");
    	tblCol_AssignmentStatus.setCellValueFactory(a -> {
    		Submission studentSubmission = a.getValue().searchStudentSubmission(student.getUsername());
    		if(studentSubmission == null)
    			if(LocalDateTime.now().isBefore(a.getValue().getDueDate()))
    				return new SimpleStringProperty("Incomplete");
    			else
    				return new SimpleStringProperty("Late");
    		else
    			if(studentSubmission.getSubmissionDate().isBefore(a.getValue().getDueDate()))
    				return new SimpleStringProperty("Submitted");
    			else
    				return new SimpleStringProperty("Submitted (Late)");
    	});
    	tblCol_AssignmentStatus.setPrefWidth(100);
    	
    	tblAllAssignments.getColumns().add(tblCol_AssignmentTitle);
    	tblAllAssignments.getColumns().add(tblCol_AssignmentDueDate);
    	tblAllAssignments.getColumns().add(tblCol_AssignmentStatus);
    	
    	tblAllAssignments.getItems().addAll(course.getAssignments());

    	tblAllAssignments.getSortOrder().add(tblCol_AssignmentDueDate);
    	tblAllAssignments.sort();
    	
    	// Create on click listener and populate side bar with assignment details
    	tblAllAssignments.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    	    if (newSelection != null) {
    	        Assignment selectedAssignment = tblAllAssignments.getSelectionModel().getSelectedItem();
    	        displayAssignmentDetails(selectedAssignment);
    	    }
    	});
    	
    	tblAllAssignments.getSelectionModel().select(0);
    }
    
    /**
     * Called by TableView Row Select Listener
     * Displays selected Assignment details on the side bar
     * @param assignment Selected assignment to display details of
     */
    public void displayAssignmentDetails(Assignment assignment) {
    	LL_AssignmentTitle.setText("Title: " + assignment.getTitle());
    	String assignmentStatus;
		Submission studentSubmission = assignment.searchStudentSubmission(student.getUsername());
		if(studentSubmission == null) {
			LL_AssignmentSubmissionDate.setText("Submission Date: N/A");
			if(LocalDateTime.now().isBefore(assignment.getDueDate()))
				assignmentStatus = "Incomplete";
			else
				assignmentStatus = "Late";
		}
		else {
			LL_AssignmentSubmissionDate.setText("Submission Date: " + studentSubmission.getSubmissionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
			if(studentSubmission.getSubmissionDate().isBefore(assignment.getDueDate()))
				assignmentStatus = "Submitted";
			else
				assignmentStatus = "Submitted (Late)";
		}
    	
    	LL_AssignmentStatus.setText("Status: " + assignmentStatus);
    	LL_AssignmentDateDue.setText("Due Date: " + assignment.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
    	LL_AssignmentInstructions.setText(assignment.getDescription());
    	
    	if(assignmentStatus.equals("Submitted") || assignmentStatus.equals("Submitted (Late)")) {
    		TA_AssignmentSubmission.setText(assignment.searchStudentSubmission(student.getUsername()).getSubmissionText());
    		TA_AssignmentSubmission.setEditable(false);
    		BN_AssignmentPublish.setDisable(true);
    	}
    	else {
    		TA_AssignmentSubmission.setText("");
    		TA_AssignmentSubmission.setEditable(true);
    		BN_AssignmentPublish.setDisable(false);
    	}
    }
    
    /**
     * Retrieves Student Submission and writes to Course Assignment
     * Executes on "Publish" submission click 
     */
    @FXML
    public void PublishAssignment() {
    	if(TA_AssignmentSubmission.getText().length() == 0) {
    		// TODO: Display pop up warning
    		System.out.println("Please enter your submission into the submission box!");
    		return;
    	}
    	
    	// Save submission to assignment
    	Assignment selectedAssignment = tblAllAssignments.getSelectionModel().getSelectedItem();
    	int selectedIndex = tblAllAssignments.getSelectionModel().getSelectedIndices().get(0);
    	
    	// Assignment Submission saved within Course obj
    	for(int i = 0; i < course.getAssignments().size(); i++) {
    		Assignment a = course.getAssignments().get(i);
    		if(a.getTitle().equals(selectedAssignment.getTitle())) {
    			course.getAssignments().get(i).addSubmission(student.getUsername(), TA_AssignmentSubmission.getText());
    			new Alert(AlertType.NONE, "Submission Accepted!", ButtonType.APPLY).show();
    			
    			System.out.println("Before Saving: list of registered students in course = " + course.getRegisteredStudents());
    			
    			// Save data into file
    			//course.getRegisteredStudents().add(student.getUsername());
    			DataController.saveCourse(course);
    			
    			// Refresh TableView
    			tblAllAssignments.getItems().clear();
    			tblAllAssignments.getItems().addAll(course.getAssignments());
    			tblAllAssignments.sort();
    			tblAllAssignments.getSelectionModel().select(selectedIndex);
    			break;
    		}
    	}
    }
    
    
    /*************************
     ** Student Grades Page Actions **
     *************************/
    
    /**
     * Initializes Grades Tab with appropriate information for each residing element
     */
    public void initGrades() {
    	if(course == null || student == null)
    		return;
    	
    	// Only perform on initial load
    	if(tblAssignmentSubmissions.getColumns().size() == 0) {
    		// Load TableView with all assignments (col 1 = title, col 2 = submission date, col 3 = due date, col 4 = grade)
        	TableColumn<Assignment, String> tblCol_AssignmentTitle = new TableColumn<>("Assignment Title");
        	tblCol_AssignmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        	tblCol_AssignmentTitle.setPrefWidth(200);
        	
        	TableColumn<Assignment, String> tblCol_SubmissionDate = new TableColumn<>("Submission Date");
        	tblCol_SubmissionDate.setCellValueFactory(a -> {
        		Submission s = a.getValue().searchStudentSubmission(student.getUsername());
        		if(s == null)
        			return new SimpleStringProperty("N/A");
        		else
        			return new SimpleStringProperty(s.getSubmissionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
        	});
        	tblCol_SubmissionDate.setPrefWidth(132);
        	
        	TableColumn<Assignment, String> tblCol_AssignmentDueDate = new TableColumn<>("Due Date");
        	tblCol_AssignmentDueDate.setCellValueFactory(a -> new SimpleStringProperty(a.getValue().getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"))));
        	tblCol_AssignmentDueDate.setPrefWidth(132);
        	tblCol_AssignmentDueDate.setSortType(TableColumn.SortType.ASCENDING);
        	
        	TableColumn<Assignment, String> tblCol_AssignmentGrade = new TableColumn<>("Grade");
        	tblCol_AssignmentGrade.setCellValueFactory(a -> {
        		Submission s = a.getValue().searchStudentSubmission(student.getUsername());
        		if(s == null)
        			return new SimpleStringProperty("-");
        		else {
        			if(s.getGrade() == -1)
        				return new SimpleStringProperty("Pending");
        			else
        				return new SimpleStringProperty(s.getGrade() + "");
        		}
        	});
        	tblCol_AssignmentGrade.setPrefWidth(90);
        	
        	tblAssignmentSubmissions.getColumns().add(tblCol_AssignmentTitle);
        	tblAssignmentSubmissions.getColumns().add(tblCol_SubmissionDate);
        	tblAssignmentSubmissions.getColumns().add(tblCol_AssignmentDueDate);
        	tblAssignmentSubmissions.getColumns().add(tblCol_AssignmentGrade);
        	
        	tblAssignmentSubmissions.getSortOrder().add(tblCol_AssignmentDueDate);
        	
        	// Create on click listener and populate side bar with assignment feedback
        	tblAssignmentSubmissions.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        	    if (newSelection != null) {
        	        Assignment selectedAssignment = tblAssignmentSubmissions.getSelectionModel().getSelectedItem();
        	        Submission studentSubmission = selectedAssignment.searchStudentSubmission(student.getUsername());
        	        
        	        if(studentSubmission != null) {
        	        	if(studentSubmission.getGrade() == -1) {
        	        		LL_FeedbackStatus.setText("Awaiting Professor Feedback");
        	        		LL_FeedbackStatus.setVisible(true);
        	        		LL_SubmissionGrade.setVisible(false);
        	        	}
        	        	else
        	        		LL_FeedbackStatus.setVisible(false);
        	        	
        	        	TA_Feedback.setText(studentSubmission.getFeedbackText());
        	        	String grade = studentSubmission.getGrade() == -1 ? "Not Graded" : studentSubmission.getGrade() + "";
        	        	LL_SubmissionGrade.setText("Submission Grade: " + grade);
        	        	LL_SubmissionGrade.setVisible(true);
        	        }
        	        else {
        	        	TA_Feedback.setText("");
        	        	LL_FeedbackStatus.setText("No Submission Received");
        	        	LL_FeedbackStatus.setVisible(true);
        	        	LL_SubmissionGrade.setVisible(false);
        	        }
        	    }
        	});
    	}
    	
    	// Reset TableView
    	tblAssignmentSubmissions.getItems().clear();
    	tblAssignmentSubmissions.getItems().addAll(course.getAssignments());
    	tblAssignmentSubmissions.sort();

    	// Load Course Grade
    	LL_CourseGrade.setText(course.getStudentAutomaticGrade(student.getUsername()) + "");
    	String completed = course.getSpecificStudentSubmissions(student.getUsername()).size() + " / " + course.getAssignments().size();
    	LL_AssignmentsCompleted.setText(completed);
    	
    	// Load Course Analytics
    	LL_AnalyticsAverageBody.setText(course.getClassAverage() + "");
    	LL_AnalyticsRangeBody.setText(course.getClassMinGrade() + ", " + course.getClassMaxGrade());
    	LL_AnalyticsMedianBody.setText(course.getClassMedian() + "");
    }

    /****************
     * Util Methods *
     ****************/
    
    /**
     * Return to Student Dashboard
     */
    @FXML
    public void GoBack() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(LAYOUT_STUDENT_DASHBOARD_VIEW));
        Stage stage = (Stage)LL_Title.getScene().getWindow();
        stage.setTitle("Student Course Overview Dashboard");
        LoadNewScene(loader, stage);
        StudentDashboardController controller = (StudentDashboardController) loader.getController();
        controller.start(student);
    }
    
    /**
     * Log current user out of their account
     * Helper function retrieves current Stage and passes to logout function call
     */
    @FXML
    public void LogoutHelper() {
    	Stage stage = (Stage)LL_Title.getScene().getWindow();
    	Logout(stage);
    }

}
