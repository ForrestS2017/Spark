/**
 * @author Forrest Smith
 */
package model;

import util.DataController;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Base class to represent any user of Spark
 */

public class User implements Comparable<User> {
    //Constants for serialization
    public static String TYPE_PROFESSOR = "PROFESSOR";
    public static String TYPE_STUDENT = "STUDENT";
    public static String TYPE_ADMINISTRATOR = "ADMINISTRATOR";
    public static Comparator<User> BY_USERNAME = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o1.getUsername().compareTo(o2.getUsername());
        }
    };
    public static Comparator<User> BY_LAST_NAME = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            int result = o1.getLastName().compareToIgnoreCase(o2.getLastName());
            return result == 0 ? result : o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
        }
    };
    public static Comparator<User> BY_FIRST_NAME = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            int result = o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
            return result == 0 ? result : o1.getLastName().compareToIgnoreCase(o2.getLastName());
        }
    };
    protected String type;
    private String firstName, lastName, username;
    private ArrayList<String> courses;
    private String password;

    public User(String firstName, String lastName, String username, String password, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.courses = new ArrayList<String>();
        this.password = "password";                 //TODO: Implement passwords and then replace this!!!
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * Get user's first and lass name as one string
     *
     * @return String containing user's first and last name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setType(String newType) {
        this.type = newType;
    }

    /**
     * Get courses a student is enrolled in or a professor is teaching
     *
     * @return User's registered courses
     */
    public ArrayList<Course> getCourses() {
        ArrayList<Course> allCourses = DataController.readCourses();
        ArrayList<Course> studentCourses = new ArrayList<Course>();
        /*courses.forEach(courseTitle -> studentCourses.add(
                (Course) allCourses.get(
                        allCourses.indexOf(new Course(courseTitle, null))))); */

        for (Course c : allCourses) {
            if (c.getRegisteredStudents().contains(this.username)) {
                studentCourses.add(c);
            }
        }

        return studentCourses;
    }

    /**
     * Add a new course and notify the caller
     *
     * @param course = Course to be added
     * @return true if we successfully added a new course
     */
    public boolean addCourse(Course course) {
        if (course != null && courses.contains(course.getTitle()) == false) {
            return courses.add(course.getTitle());
        }
        return false;
    }

    /**
     * Verify a user's password
     *
     * @return true if password is correct
     */
    public boolean verifyPassword(String inputPassword) {
        return inputPassword.length() == 0 ? false : password.equals(inputPassword);
    }

    /**
     * Compares users based off username
     *
     * @param object User to compare to
     * @return true if the username is equal
     */
    public boolean equals(Object object) {
        if (object != null && object instanceof User) {
            User otherUser = (User) object;
            return this.username.equals(otherUser.getUsername());
        } else {
            return false;
        }
    }

    /**
     * Compares users based off username
     *
     * @param otherUser
     * @return
     */
    public int compareTo(User otherUser) {
        return this.getUsername().compareTo(otherUser.getUsername());
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(username + " - ")
                .append(firstName)
                .append(" ")
                .append(lastName).toString();
    }
}
