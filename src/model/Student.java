package model;

import java.util.ArrayList;

public class Student {
	public String name, username;
	private String password;
	public ArrayList<Course> coursesRegistered;
	public float gpa;
	
	public Student(String username, String name) {
		this.username = username;
		this.name = name;
		password = "password";
		gpa = 0;
		coursesRegistered = new ArrayList<Course>();
	}
	
	public void addCourse(Course course) {
		if(!coursesRegistered.contains(course)) {
			coursesRegistered.add(course);
		}
		else {
			// course already registered for, no duplicates
		}
	}
}
