package model;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
	
	public void publishAssignment(String title, String description, LocalDateTime submissionDate) {
		assignments.add(new Assignment(title, description, submissionDate));
	}
	
	public void publishAnnouncement(String name, String description) {
		announcements.add(new Announcement(name, description));
	}

	public class Announcement {
		public String name, description;
		public long publishDate;
		
		public Announcement(String name, String description) {
			this.name = name;
			this.description = description;
			publishDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
		}
	}
}
