package model;

import java.util.ArrayList;
import java.util.Comparator;

public class User implements Comparable<User>{
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
        return inputPassword.isBlank() ? false : password.equals(inputPassword);
    }


    /**
     * Compares users based off username
     * @param object User to compare to
     * @return true if the username is equal
     */
    public boolean equals(Object object) {
        if (object != null && object instanceof User) {
            User otherUser = (User)object;
            return this.username.equals(otherUser.getUsername());
        } else {
            return false;
        }
    }

    /**
     * Compares users based off username
     * @param otherUser
     * @return
     */
    public int compareTo(User otherUser) {
        return this.getUsername().compareTo(otherUser.getUsername());
    }

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
}
