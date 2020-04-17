package model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;

public class Assignment {
	private String title, description;
	private long publishDate, submissionDate;
	private ArrayList<Submission> studentSubmissions;
	
	public Assignment(String title, String description, LocalDateTime submissionDate) {
		this.title = title;
		this.description = description;
		publishDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
		this.submissionDate = submissionDate.atZone(ZoneId.systemDefault()).toEpochSecond();
		studentSubmissions = new ArrayList<Submission>();
	}
	
	public void addSubmission(String studentName, String submissionText) {
		Submission s = new Submission(studentName, submissionText);
		studentSubmissions.add(s);
	}
	
	public void gradeSubmission(String studentUsername, int grade) {
		// Search for student submission
		for(Submission s : studentSubmissions) {
			if(s.userName.contentEquals(studentUsername)) {
				s.assignGrade(grade);
				return;
			}
		}
		// Student submission with given username not found
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getPublishDate() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(publishDate),ZoneId.systemDefault());
	}

	public LocalDateTime getSubmissionDate() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(submissionDate),ZoneId.systemDefault());
	}

	public ArrayList<Submission> getStudentSubmissions() {
		return studentSubmissions;
	}

	private class Submission {
		private String userName, submissionText;
		private int grade;
		
		public Submission(String userName, String submissionText) {
			this.userName = userName;
			this.submissionText = submissionText;
			grade = -1;
		}

		public String getUserName() {
			return userName;
		}

		public int getGrade() {
			return grade;
		}

		public String getSubmissionText() {
			return submissionText;
		}

		public void assignGrade(int grade) {
			this.grade = grade;
		}
	}
}
