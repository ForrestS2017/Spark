package model;

import java.util.ArrayList;

public class Student extends User{
	private float gpa;
	
	public Student(String firstName, String lastName, String username, String password) {
		super(firstName, lastName, username, password);
		gpa = 0.0f;
	}

	public float getGPA() {
		return gpa;
	}
}
