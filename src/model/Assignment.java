package model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;

public class Assignment implements Comparable<Assignment> {
	private String title, description;
	private long publishDate, dueDate;
	private ArrayList<Submission> studentSubmissions;
	
	public Assignment(String title, String description, LocalDateTime dueDate) {
		this.title = title;
		this.description = description;
		this.publishDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
		this.dueDate = dueDate.atZone(ZoneId.systemDefault()).toEpochSecond();
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

	public LocalDateTime getDueDate() {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(dueDate),ZoneId.systemDefault());
	}

	public ArrayList<Submission> getStudentSubmissions() {
		return studentSubmissions;
	}

	/**
	 * Compares assignments based off title
	 * @param object Assignment to compare to
	 * @return true if the title is equal
	 */
	public boolean equals(Object object) {
		if (object != null && object instanceof Assignment) {
			Assignment otherAssignment = (Assignment)object;
			return this.getTitle().equals(otherAssignment.getTitle());
		} else {
			return false;
		}
	}

	/**
	 * Compares assignments based off title
	 * @param otherAssignment
	 * @return
	 */
	public int compareTo(Assignment otherAssignment) {
		return this.getTitle().compareTo(otherAssignment.getTitle());
	}

	public static Comparator<Assignment> BY_PUBLISH_DATE = new Comparator<Assignment>() {
		@Override
		public int compare(Assignment o1, Assignment o2) {
			return o1.getPublishDate().compareTo(o2.getPublishDate());
		}
	};

	public Comparator<Submission> BY_SUBMISSION_GRADE = new Comparator<Submission>() {
		@Override
		public int compare(Submission o1, Submission o2) {
			long l = o1.getGrade();
			long r = o2.getGrade();
			return l < r ? -1 : r > l ? 1 : 0;
		}
	};

	public Comparator<Submission> BY_SUBMISSION_DATE = new Comparator<Submission>() {
		@Override
		public int compare(Submission o1, Submission o2) {
			long l = o1.getSubmissionDate().atZone(ZoneId.systemDefault()).toEpochSecond();
			long r = o2.getSubmissionDate().atZone(ZoneId.systemDefault()).toEpochSecond();
			return l < r ? -1 : r > l ? 1 : 0;
		}
	};

	private class Submission implements Comparable<Submission>{
		private String userName, submissionText;
		private long submissionDate;
		private long grade;
		
		public Submission(String userName, String submissionText) {
			this.userName = userName;
			this.submissionText = submissionText;
			this.submissionDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
			grade = -1;
		}

		public String getUserName() {
			return userName;
		}

		public long getGrade() {
			return grade;
		}

		public String getSubmissionText() {
			return submissionText;
		}

		public LocalDateTime getSubmissionDate() {
			return LocalDateTime.ofInstant(Instant.ofEpochSecond(submissionDate),ZoneId.systemDefault());
		}

		public void assignGrade(int grade) {
			this.grade = grade;
		}



		/**
		 * Compares submission based off user
		 * @param object Submission to compare to
		 * @return true if the user is equal
		 */
		public boolean equals(Object object) {
			if (object != null && object instanceof Submission) {
				Submission otherSubmission = (Submission)object;
				return this.getUserName().equals(otherSubmission.getUserName());
			} else {
				return false;
			}
		}

		/**
		 * Compares submissions based off user
		 * @param otherSubmission
		 * @return
		 */
		public int compareTo(Submission otherSubmission) {
			return this.getUserName().compareTo(otherSubmission.getUserName());
		}
	}
}
