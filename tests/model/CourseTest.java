package model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    // Student fields
    private String firstName_s = "Johnny";
    private String lastName_s = "Appleseed";
    private String userName_s = "ja00";
    private String password_s = "apples123";;
    private float finalGrade_s = 95.5f;

    private String firstName_s1 = "George";
    private String lastName_s1 = "Washington";
    private String userName_s1 = "gw00";
    private String password_s1 = "presidency";;
    private float finalGrade_s1 = 55.5f;

    // Professor fields
    private String firstName_p = "Paul";
    private String lastName_p = "Gilmore";
    private String userName_p = "pg99";
    private String password_p = "HonorsCollege123";

    // Course fields
    private String title_c = "Expository Writing";
    private String id = "355:101";

    // Announcement fields
    private String title_an = "New Announcement";
    private String description_an = "This is a new announcement";
    private long publishDate_an = 500;

    // Assignment fields
    private String title_as = "New Assignment";
    private String description_as = "This is a new assignment";
    private long dueDate_as = 500;
    private boolean canResubmit_as = false;
    private boolean canSubmitAfterDeadline = false;

    // Submission fields
    private String submissionText_ss = "This is a my submission";

    @Test
    void getRegisteredStudents() {
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
        c.registerStudent(s);
        ArrayList<String> sList = new ArrayList<>();
        sList.add(s.getUsername());
        assertEquals(sList,c.getRegisteredStudents());
    }

    @Test
    void getTitle() {
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        assertEquals(title_c, c.getTitle());
    }

    @Test
    void getId() {
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        assertEquals(id, c.getId());
    }

    @Test
    void getProfessor() {
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        assertEquals(p, c.getProfessor());
    }

    @Test
    void getProfessorUsername() {
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        assertEquals(userName_p, c.getProfessorUsername());
    }

    @Test
    void getStudents() {
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
        c.registerStudent(s);
        ArrayList<Student> sList = new ArrayList<>();
        sList.add(s);
        assertEquals(sList,c.getStudents());
    }

    @Test
    void getAssignments() {
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
        ArrayList<Assignment> asList = new ArrayList<>();
        asList.add(as);
        assertEquals(asList, c.getAssignments());
    }

    @Test
    void publishAssignment() {
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
        assertTrue(c.getAssignments().contains(as));
    }

    @Test
    void removeAssignment() {
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
        c.removeAssignment(as);
        assertFalse(c.getAssignments().contains(as));
    }

    @Test
    void getSpecificStudentSubmissions() {
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
        assertEquals(ssList, c.getSpecificStudentSubmissions(s.getUsername()));
    }

    @Test
    void getAnnouncements() {
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        Course.Announcement an = new Course.Announcement(title_an, description_an);
        c.publishAnnouncement(an);
        ArrayList<Course.Announcement> anList = new ArrayList<>();
        anList.add(an);
        assertEquals(anList, c.getAnnouncements());
    }

    @Test
    void publishAnnouncement() {
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        Course.Announcement an = new Course.Announcement(title_an, description_an);
        c.publishAnnouncement(an);
        ArrayList<Course.Announcement> anList = new ArrayList<>();
        anList.add(an);
        assertTrue(c.getAnnouncements().contains(an));
    }

    @Test
    void removeAnnouncement() {
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        Course.Announcement an = new Course.Announcement(title_an, description_an);
        c.publishAnnouncement(an);
        ArrayList<Course.Announcement> anList = new ArrayList<>();
        anList.add(an);
        anList.remove(an);
        assertTrue(c.getAnnouncements().contains(an));
    }

    @Test
    void setFinalGrade() {
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
        c.setFinalGrade(s.getUsername(),finalGrade_s);
        assertEquals(finalGrade_s, c.getGradeBookFinal().get(s.getUsername()));
    }

    @Test
    void removeFinalGrade() {
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
        c.setFinalGrade(s.getUsername(),finalGrade_s);
        c.removeFinalGrade(s.getUsername());
        assertFalse(c.getGradeBookFinal().containsKey(s.getUsername()));
    }

    @Test
    void getStudentAutomaticGrade() {
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
        c.updateAutomaticGrade(s.getUsername());
        assertEquals(0f, c.getGradeBookAutomatic().get(s.getUsername()));
    }

    @Test
    void getStudentFinalGrade() {
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
        c.setFinalGrade(s.getUsername(),finalGrade_s);
        assertEquals(finalGrade_s, c.getGradeBookFinal().get(s.getUsername()));
    }

    @Test
    void getGradeBookFinal() {
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
        c.setFinalGrade(s.getUsername(),finalGrade_s);
        Map<String, Float> fMap = new HashMap<>();
        fMap.put(s.getUsername(), finalGrade_s);
        assertEquals(fMap, c.getGradeBookFinal());
    }

    @Test
    void getGradeBookAutomatic() {
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        Map<String, Float> fMap = new HashMap<>();
        assertEquals(fMap, c.getGradeBookFinal());
    }

    @Test
    void getClassAverage() {
        Student s = new Student(firstName_s,
                lastName_s,
                userName_s,
                password_s);
        Student s1 = new Student(firstName_s1,
                lastName_s1,
                userName_s1,
                password_s1);
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        c.setFinalGrade(s.getUsername(),finalGrade_s);
        c.setFinalGrade(s1.getUsername(),finalGrade_s1);
        Map<String, Float> fMap = new HashMap<>();
        fMap.put(s.getUsername(), finalGrade_s);
        fMap.put(s1.getUsername(), finalGrade_s1);
        assertEquals(75.5, c.getClassAverage());
    }

    @Test
    void getClassMedian() {
        Student s = new Student(firstName_s,
                lastName_s,
                userName_s,
                password_s);
        Student s1 = new Student(firstName_s1,
                lastName_s1,
                userName_s1,
                password_s1);
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        c.setFinalGrade(s.getUsername(),finalGrade_s);
        c.setFinalGrade(s1.getUsername(),finalGrade_s1);
        Map<String, Float> fMap = new HashMap<>();
        fMap.put(s.getUsername(), finalGrade_s);
        fMap.put(s1.getUsername(), finalGrade_s1);
        assertEquals(finalGrade_s1, c.getClassMedian());
    }

    @Test
    void getClassMaxGrade() {
        Student s = new Student(firstName_s,
                lastName_s,
                userName_s,
                password_s);
        Student s1 = new Student(firstName_s1,
                lastName_s1,
                userName_s1,
                password_s1);
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        c.setFinalGrade(s.getUsername(),finalGrade_s);
        c.setFinalGrade(s1.getUsername(),finalGrade_s1);
        Map<String, Float> fMap = new HashMap<>();
        fMap.put(s.getUsername(), finalGrade_s);
        fMap.put(s1.getUsername(), finalGrade_s1);
        assertEquals(finalGrade_s, c.getClassMaxGrade());
    }

    @Test
    void getClassMinGrade() {
        Student s = new Student(firstName_s,
                lastName_s,
                userName_s,
                password_s);
        Student s1 = new Student(firstName_s1,
                lastName_s1,
                userName_s1,
                password_s1);
        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title_c,
                id,
                p);
        c.setFinalGrade(s.getUsername(),finalGrade_s);
        c.setFinalGrade(s1.getUsername(),finalGrade_s1);
        Map<String, Float> fMap = new HashMap<>();
        fMap.put(s.getUsername(), finalGrade_s);
        fMap.put(s1.getUsername(), finalGrade_s1);
        assertEquals(finalGrade_s1, c.getClassMaxGrade());
    }
}
