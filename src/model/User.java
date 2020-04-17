package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String firstName, lastName, username;
    private ArrayList<Course> courses;
    private String password;

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.courses = new ArrayList<Course>();
        this.password = "password";                 //TODO: Implement passwords and then replace this!!!
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    /**
     * Add a new course and notify the caller
     * @param course = Course to be added
     * @return true if we successfully added a new course
     */
    public boolean addCourse(Course course) {
        if (course != null && courses.contains(course) == false)
        {
            return courses.add(course);
        }
        return false;
    }

    /**
     * Verify a user's password
     * @return true if password is correct
     */
    public boolean verifyPassword(String inputPassword) {
        return inputPassword.length()==0 ? false : password.equals(inputPassword);
    }
}
