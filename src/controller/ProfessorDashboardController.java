package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Course;
import util.DataController;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProfessorDashboardController extends BasicWindow {

    private Stage mainStage;
    private FXMLLoader loader;

    private String username;

    private ArrayList<Course> courseList;
    private ObservableList<Course> obsList;

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

    }

    public void start(String inputUsername) {
        this.username = inputUsername;
        this.username = "fcs34";                //TODO: FOR TESTING PURPOSES. REMOVE IN PROD
        obsList = FXCollections.observableArrayList();
        courseList = DataController.readCourses();
        if (courseList == null || courseList.size() < 1) { ShowWarningNoCourses("No Courses in System!"); return;}
        //courseList.stream().filter(course -> course.getProfessorUsername().contentEquals(username)).forEach(course -> obsList.add(course));
        courseList.forEach(course -> {
            System.out.println("Course: " + course.getTitle() + "\nProf: " + course.getProfessorUsername());
            if (course.getProfessorUsername().equals(username)) obsList.add(course); });
        if (obsList == null || obsList.size() < 1) { ShowWarningNoCourses("Prof has no Courses!"); return;}
        LV_CourseList.setItems(obsList);
        LV_CourseList.setCellFactory(list -> {
            return new ListCell<Course>() {
                protected void updateItem(Course item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        this.setText(item.getTitle());
                    } else {
                        this.setText((String) null);
                    }
                }
            };
        });
        LV_CourseList.getSelectionModel().select(0);


    }

    public void start(Stage mainStage, FXMLLoader loader) {
        this.mainStage = mainStage;
        this.loader = loader;
    }

    /**
     * Enter the course view for the selected course
     */
    @FXML
    private void EnterCourse(){
        Course selection = LV_CourseList.getSelectionModel().getSelectedItem();
        if (selection == null) return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(LAYOUT_PROFESSOR_COURSE_VIEW));
        LoadNewScene(loader);
        ProfessorCourseViewController controller = (ProfessorCourseViewController) loader.getController();
        controller.start(selection.getTitle());
    }



    private void ShowWarningNoCourses(String s) {
        System.out.println(s);
        LV_CourseList.setVisible(false);
        LL_NoCourses.setVisible(true);
    }
}
