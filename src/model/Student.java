/**
 * @author Luis Guzman
 * @author Forrest Smith
 */
package model;

/**
 * Class to represent a student
 */
public class Student extends User {
    private float gpa;

    public Student(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password, TYPE_PROFESSOR);
        gpa = 0.0f;
    }

    public float getGPA() {
        return gpa;
    }

    public void setGPA(float gpa) {
        this.gpa = gpa;
    }
}
