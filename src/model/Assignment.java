package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Assignment {
	public String name, details;
	public LocalDateTime publishDate, submissionDate;
	public ArrayList<Submission> studentSubmissions;
	
	public Assignment(String name, String details, LocalDateTime submissionDate) {
		this.name = name;
		this.details = details;
		publishDate = LocalDateTime.now();
		this.submissionDate = submissionDate;
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
