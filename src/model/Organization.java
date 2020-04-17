package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Organization implements Serializable {
	private String organizationName;
	//private Admin admin;		//TODO: Create Admin class
	public ArrayList<Course> courses;
	public ArrayList<Professor> professors;
	public ArrayList<Student> students;
	
	public Organization(String name) {
		organizationName = name;
		courses = new ArrayList<Course>();
		professors = new ArrayList<Professor>();
		students = new ArrayList<Student>();
	}
	
	public String getOrganizationName() {
		return this.organizationName;
	}
	
	/**
     * Search for all courses a student is registered in
     */
    public ArrayList<Course> coursesStudentRegisteredIn(String username) {
    	ArrayList<Course> coursesRegisteredIn = new ArrayList<Course>();
    	
    	for(Course c : courses) {
    		for(Student s : c.getStudents()) {
    			if(s.getUsername().equalsIgnoreCase(username)) {
    				coursesRegisteredIn.add(c);
    				break;
    			}
    		}
    	}
    	
    	return coursesRegisteredIn;
    }
}
