package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;

public class Assignment {
	public String title, description;
	public long publishDate, submissionDate;
	public ArrayList<Submission> studentSubmissions;
	
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
			if(s.studentName.contentEquals(studentUsername)) {
				s.assignGrade(grade);
				return;
			}
		}
		// Student submission with given username not found
	}
	
	private class Submission {
		public String studentName, submissionText;
		private int grade;
		
		public Submission(String studentName, String submissionText) {
			this.studentName = studentName;
			this.submissionText = submissionText;
			grade = -1;
		}
		
		public void assignGrade(int grade) {
			this.grade = grade;
		}
	}
}
