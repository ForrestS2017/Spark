package model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {
    // Student fields
    private String firstName_s = "Johnny";
    private String lastName_s = "Appleseed";
    private String userName_s = "ja00";
    private String password_s = "apples123";;

    // Professor fields
    private String firstName_p = "Paul";
    private String lastName_p = "Gilmore";
    private String userName_p = "pg99";
    private String password_p = "HonorsCollege123";

    // Course fields
    private String title_c = "Expository Writing";
    private String id = "355:101";

    // Assignment fields
    private String title_as = "New Assignment";
    private String description_as = "This is a new assignment";
    private long dueDate_as = 500;
    private boolean canResubmit_as = false;
    private boolean canSubmitAfterDeadline = false;

    // Submission fields
    private String submissionText_ss = "This is a my submission";

    @Test
    void getTitle() {
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        Assignment as = new Assignment(title_as,
                description_as,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(dueDate_as), ZoneId.systemDefault()),
                canResubmit_as,
                canSubmitAfterDeadline);
        assertEquals(title_as, as.getTitle());
    }

    @Test
    void getDescription() {
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        Assignment as = new Assignment(title_as,
                description_as,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(dueDate_as), ZoneId.systemDefault()),
                canResubmit_as,
                canSubmitAfterDeadline);
        assertEquals(description_as, as.getDescription());
    }

    @Test
    void getDueDate() {
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        Assignment as = new Assignment(title_as,
                description_as,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(dueDate_as), ZoneId.systemDefault()),
                canResubmit_as,
                canSubmitAfterDeadline);
        assertEquals(dueDate_as, as.getPublishDate().atZone(ZoneId.systemDefault()).toEpochSecond());
    }

    @Test
    void getCanResubmit() {
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        Assignment as = new Assignment(title_as,
                description_as,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(dueDate_as), ZoneId.systemDefault()),
                canResubmit_as,
                canSubmitAfterDeadline);
        assertEquals(canResubmit_as, as.getCanResubmit());
    }

    @Test
    void getCanSubmitAfterDeadline() {
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        Assignment as = new Assignment(title_as,
                description_as,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(dueDate_as), ZoneId.systemDefault()),
                canResubmit_as,
                canSubmitAfterDeadline);
        assertEquals(canSubmitAfterDeadline, as.getCanSubmitAfterDeadline());
    }

    @Test
    void getStudentSubmissions() {
        Student s = new Student(firstName_s,
                lastName_s,
                userName_s,
                password_s);
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        Assignment as = new Assignment(title_as,
                description_as,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(dueDate_as), ZoneId.systemDefault()),
                canResubmit_as,
                canSubmitAfterDeadline);
        c.publishAssignment(as);
        Assignment.Submission ss = new Assignment.Submission(s.getUsername(), submissionText_ss, null);
        c.getAssignments().get(0).addSubmission(ss.getUsername(), ss.getSubmissionText(), ss.getAttachment());
        ArrayList<Assignment.Submission> ssList = new ArrayList<>();
        ssList.add(ss);
        assertEquals(ssList,as.getStudentSubmissions());
    }

    @Test
    void searchStudentSubmission() {
        Student s = new Student(firstName_s,
                lastName_s,
                userName_s,
                password_s);
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        Assignment as = new Assignment(title_as,
                description_as,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(dueDate_as), ZoneId.systemDefault()),
                canResubmit_as,
                canSubmitAfterDeadline);
        c.publishAssignment(as);
        Assignment.Submission ss = new Assignment.Submission(s.getUsername(), submissionText_ss, null);
        c.getAssignments().get(0).addSubmission(ss.getUsername(), ss.getSubmissionText(), ss.getAttachment());
        assertEquals(ss,as.searchStudentSubmission(s.getUsername()));
    }
}
