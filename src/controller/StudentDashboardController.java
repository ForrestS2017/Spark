package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Course;
import model.Organization;
import model.Student;

public class StudentDashboardController {
    /***********************************************
     ************** Widget References **************
     ***********************************************/

    /*******************************
     **** Non-Responsive Widgets ***
     *******************************/
	@FXML Label LL_Header; 
	@FXML Label LL_Subtitle;
	@FXML Label LL_NoCourses;


    /*******************************
     ****** Responsive Widgets *****
     *******************************/
    @FXML ListView<Course> LV_CourseList;
    @FXML Button BN_EnterCourse;


    /***********************************************
     ****** Widget Methods & Events Listeners ******
     ***********************************************/
    
    private Organization org;
    private String username;
    
    @FXML
    public void initialize() {
    	// Only run once Organization object is initialized
		if(org == null)
			return;
		
        /**
         * TODO:
         *  - Toggle visibility of @LL_NoCourses, @LV_CourseList, @BN_EnterCourses
         *  - Fill @LL_Subtitle, @LV_CourseList
         */
		LL_Header.setText(org.getOrganizationName());
    	LL_Subtitle.setText(username + "'s Dashboard");
		
		System.out.println("Num students = " + org.students.size());
    	System.out.println("TESTING: Adding new Student to Organization");
    	org.students.add(new Student("Ben", "Ja", "benja", "password"));
    	
    	try {
    		System.out.println("TESTING: Saving Organization object to file. Reopen file and check if num students has increased!");
			model.UserIO.writeUserData(org.getOrganizationName(), org);
		} catch(IOException e1) {
			e1.printStackTrace();
		}
    }

    /**
     * Enter the course view for the selected course
     */
    @FXML
    private void EnterCourse() { 
    	
    }
    
    /**
     * Used to pass necessary information from login controller to current controller
     * @param username
     */
    public void loginPageToStudentDashboard(Organization org, String username) {
    	this.org = org;
    	this.username = username;
    	initialize();
    }
}
