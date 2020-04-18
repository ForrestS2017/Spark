package model;

import java.util.ArrayList;

public class Professor extends User{
	
	public Professor(String firstName, String lastName, String username, String password) {
		super(firstName, lastName, username, password, TYPE_PROFESSOR);
	}
}
