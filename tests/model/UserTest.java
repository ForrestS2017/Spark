package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private String firstName_s = "Johnny";
    private String lastName_s = "Appleseed";
    private String userName_s = "ja00";
    private String password_s = "apples123";

    private String firstName_p = "Paul";
    private String lastName_p = "Gilmore";
    private String userName_p = "pg99";
    private String password_p = "HonorsCollege123";

    private String title = "Expository Writing";
    private String id = "355:101";


    @Test
    void getFirstName() {
        Student s = new Student(firstName_s,
                lastName_s,
                userName_s,
                password_s);
        assertEquals(firstName_s, s.getFirstName());
    }

    @Test
    void getLastName() {
        Student s = new Student(firstName_s,
                lastName_s,
                userName_s,
                password_s);
        assertEquals(lastName_s, s.getLastName());
    }

    @Test
    void getFullName() {
        Student s = new Student(firstName_s,
                lastName_s,
                userName_s,
                password_s);
        assertEquals(firstName_s + " " + lastName_s, s.getFullName());
    }

    @Test
    void getUsername() {
        Student s = new Student(firstName_s,
                lastName_s,
                userName_s,
                password_s);
        assertEquals(userName_s, s.getUsername());
    }

    @Test
    void getType() {
        Student s = new Student(firstName_s,
                lastName_s,
                userName_s,
                password_s);
        assertEquals(User.TYPE_STUDENT, s.getType());
    }

    @Test
    void getCourses() {
        Student s = new Student(firstName_s,
                lastName_s,
                userName_s,
                password_s);

        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title,
                id,
                p);
        s.addCourse(c);
        ArrayList<Course> cList = new ArrayList<>();
        cList.add(c);
        assertEquals(cList, s.getCourses());
    }

    @Test
    void addCourse() {
        Student s = new Student(firstName_s,
                lastName_s,
                userName_s,
                password_s);

        Professor p = new Professor(firstName_p,
                lastName_p,
                userName_p,
                password_p);
        Course c = new Course(title,
                id,
                p);
        s.addCourse(c);
        ArrayList<Course> cList = new ArrayList<>();
        cList.add(c);
        assertTrue(s.getCourses().contains(c));
    }

    @Test
    void verifyPassword() {
        Student s = new Student(firstName_s,
                lastName_s,
                userName_s,
                password_s);
        assertTrue(s.verifyPassword(password_s));
    }
}