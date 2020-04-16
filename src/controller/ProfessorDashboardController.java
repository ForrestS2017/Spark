package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Course;

public class ProfessorDashboardController {

    /***********************************************
     ************** Widget References **************
     ***********************************************/

    /*******************************
     **** Non-Responsive Widgets ***
     *******************************/
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

    @FXML
    public void initialize() {
        /**
         * TODO:
         *  - Toggle visibility of @LL_NoCourses, @LV_CourseList, @BN_EnterCourses
         *  - Fill @LL_Subtitle, @LV_CourseList
         */
    }

    /**
     * Enter the course view for the selected course
     */
    @FXML
    private void EnterCourse(){ }
}
