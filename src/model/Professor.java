/**
 * @author Luis Guzman
 * @author Forrest Smith
 */
package model;

/**
 * Class to represent a professor
 */

public class Professor extends User {

    public Professor(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password, TYPE_PROFESSOR);
    }
}
