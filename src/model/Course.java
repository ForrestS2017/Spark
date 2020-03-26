package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Course {
	public String name;
	public ArrayList<Student> registeredStudents;
	public Professor professor;
	public ArrayList<Assignment> assignments;
	public ArrayList<Announcement> announcements;
	
	public Course(String name, Professor professor) {
		this.name = name;
		this.professor = professor;
		registeredStudents = new ArrayList<Student>();
		assignments = new ArrayList<Assignment>();
		announcements = new ArrayList<Announcement>();
	}
	
	public void addStudent(Student student) {
		if(!registeredStudents.contains(student)) {
			registeredStudents.add(student);
		}
		else {
			// duplicate student cannot be added
		}
	}
	
	public void publishAssignment(String name, String details, LocalDateTime submissionDate) {
		assignments.add(new Assignment(name, details, submissionDate));
	}
	
	public void publishAnnouncement(String name, String details) {
		announcements.add(new Announcement(name, details));
	}

	private class Announcement {
		public String name, details;
		public LocalDateTime publishDate;
		
		public Announcement(String name, String details) {
			this.name = name;
			this.details = details;
			publishDate = LocalDateTime.now();
		}
	}
}
