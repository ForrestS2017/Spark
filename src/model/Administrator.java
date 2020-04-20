/**
 * @author Forrest Smith
 */
package model;

/**
 * Class to represent an Administrator
 */

public class Administrator extends User {

    public Administrator(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password, TYPE_ADMINISTRATOR);
    }
}
