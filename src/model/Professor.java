package model;

import java.util.ArrayList;

public class Professor {
	public String name, username;
	private String password;
	public ArrayList<Course> coursesTeaching;
	
	public Professor(String name, String username) {
		this.name = name;
		this.username = username;
		password = "password";
		coursesTeaching = new ArrayList<Course>();
	}
	
	public void addCourse(Course course) {
		if(!coursesTeaching.contains(course)) {
			coursesTeaching.add(course);
		}
		else {
			// cannot add duplicate course
		}
	}
}
