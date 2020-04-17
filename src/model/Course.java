package model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;

public class Course implements Comparable<Course>{
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

	/**
	 * Compares courses based off title
	 * @param object Course to compare to
	 * @return true if the title is equal
	 */
	public boolean equals(Object object) {
		if (object != null && object instanceof Course) {
			Course otherCourse = (Course)object;
			return this.getTitle().equals(otherCourse.getTitle());
		} else {
			return false;
		}
	}

	/**
	 * Compares courses based off title
	 * @param otherCourse
	 * @return
	 */
	public int compareTo(Course otherCourse) {
		return this.getTitle().compareTo(otherCourse.getTitle());
	}

	public static Comparator<Announcement> BY_ANNOUNCEMENT_PUBLISH_DATE = new Comparator<Announcement>() {
		@Override
		public int compare(Announcement o1, Announcement o2) {
			return o1.getPublishDate().compareTo(o2.getPublishDate());
		}
	};

	public class Announcement implements Comparable<Announcement>{
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

		/**
		 * Compares announcements based off title
		 * @param object Announcement to compare to
		 * @return true if the title is equal
		 */
		public boolean equals(Object object) {
			if (object != null && object instanceof Announcement) {
				Announcement otherAnnouncement = (Announcement)object;
				return this.getTitle().equals(otherAnnouncement.getTitle());
			} else {
				return false;
			}
		}

		/**
		 * Compares announcements based off title
		 * @param otherAnnouncement
		 * @return
		 */
		public int compareTo(Announcement otherAnnouncement) {
			return this.getTitle().compareTo(otherAnnouncement.getTitle());
		}
	}
}
