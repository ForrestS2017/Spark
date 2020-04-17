package model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

public class Course {
	private String title;
	private ArrayList<Student> registeredStudents;
	private Professor professor;
	private ArrayList<Assignment> assignments;
	private ArrayList<Announcement> announcements;
	
	public Course(String title, Professor professor) {
		this.title = title;
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

	public String getTitle() {
		return title;
	}

	public Professor getProfessor() {
		return professor;
	}

	public ArrayList<Student> getStudents() {
		return registeredStudents;
	}

	public ArrayList<Assignment> getAssignments() {
		return assignments;
	}

	public ArrayList<Announcement> getAnnouncements() {
		return announcements;
	}

	public class Announcement {
		private String title, description;
		private long publishDate;
		
		public Announcement(String title, String description) {
			this.title = title;
			this.description = description;
			publishDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public LocalDateTime getPublishDate() {
			return LocalDateTime.ofInstant(Instant.ofEpochSecond(publishDate),ZoneId.systemDefault());
		}
	}
}
