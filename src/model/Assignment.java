/**
 * @author Luis Guzman
 * @author Forrest Smith
 */
package model;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class to represent an assignment
 */
public class Assignment implements Comparable<Assignment> {
    /**
     * Formatter for assignment dates
     */
    public static DateTimeFormatter DTFORMATTER_LONG = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
    public static Comparator<Assignment> BY_PUBLISH_DATE = new Comparator<Assignment>() {
        @Override
        public int compare(Assignment o1, Assignment o2) {
            return o1.getPublishDate().compareTo(o2.getPublishDate());
        }
    };
    public static Comparator<Submission> BY_SUBMISSION_GRADE = new Comparator<Submission>() {
        @Override
        public int compare(Submission o1, Submission o2) {
            long l = o1.getGrade();
            long r = o2.getGrade();
            return l < r ? -1 : r > l ? 1 : 0;
        }
    };
    public static Comparator<Submission> BY_SUBMISSION_DATE = new Comparator<Submission>() {
        @Override
        public int compare(Submission o1, Submission o2) {
            long l = o1.getSubmissionDate().atZone(ZoneId.systemDefault()).toEpochSecond();
            long r = o2.getSubmissionDate().atZone(ZoneId.systemDefault()).toEpochSecond();
            return l < r ? -1 : r > l ? 1 : 0;
        }
    };
    private String title, description;
    private long publishDate, dueDate;
    private boolean canResubmit, canSubmitAfterDeadline;
    private ArrayList<Submission> studentSubmissions;

    public Assignment(String title, String description, LocalDateTime dueDate, boolean canResubmit, boolean canSubmitAfterDeadline) {
        this.title = title;
        this.description = description;
        this.publishDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
        this.dueDate = dueDate.atZone(ZoneId.systemDefault()).toEpochSecond();
        this.canResubmit = canResubmit;
        this.canSubmitAfterDeadline = canSubmitAfterDeadline;
        studentSubmissions = new ArrayList<Submission>();
    }

    public void addSubmission(String studentName, String submissionText, File attachment) {
        Submission s = new Submission(studentName, submissionText, attachment);
        
    	// Check if submission with studentName already exists, replace contents
    	if(studentSubmissions.contains(s)) {
    		studentSubmissions.remove(s);
    		studentSubmissions.add(s);
    	}
    	else
    		studentSubmissions.add(s);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPublishDate() {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(publishDate), ZoneId.systemDefault());
    }

    public LocalDateTime getDueDate() {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(dueDate), ZoneId.systemDefault());
    }

    public boolean getCanResubmit() {
        return canResubmit;
    }
    
    public boolean getCanSubmitAfterDeadline() {
    	return canSubmitAfterDeadline;
    }

    public ArrayList<Submission> getStudentSubmissions() {
        return studentSubmissions;
	}

    /**
     * Get a specific student's submission for this assignment
     * @param username Student's ID
     * @return Student's submission if it exists
     */
	public Submission searchStudentSubmission(String username) {
		for(Submission s : studentSubmissions) {
			if(s.getUsername().equals(username))
				return s;
		}
		return null;
	}

    /**
     * Compares assignments based off title
     *
     * @param object Assignment to compare to
     * @return true if the title is equal
     */
    public boolean equals(Object object) {
        if (object != null && object instanceof Assignment) {
            Assignment otherAssignment = (Assignment) object;
            return this.getTitle().equals(otherAssignment.getTitle());
        } else {
            return false;
        }
    }

    /**
     * Compares assignments based off title
     *
     * @param otherAssignment
     * @return
     */
    public int compareTo(Assignment otherAssignment) {
        return this.getTitle().compareTo(otherAssignment.getTitle());
    }

    @Override
    public String toString() {
        return title;
    }

    /**
     * Class to represent an assignment submission
     */
    public static class Submission implements Comparable<Submission> {
        private String username, submissionText, feedbackText;
        private long submissionDate;
        private long grade;
        private File attachment;

        public Submission(String username, String submissionText, File attachment) {
            this.username = username;
            this.submissionText = submissionText;
            this.submissionDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
            this.attachment = attachment;
            grade = -1;
        }

        public String getUsername() {
            return username;
        }

        public long getGrade() {
            return grade;
        }

        public String getSubmissionText() {
            return submissionText;
        }
        
        public LocalDateTime getSubmissionDate() {
            return LocalDateTime.ofInstant(Instant.ofEpochSecond(submissionDate), ZoneId.systemDefault());
        }

        public void assignGrade(long grade) {
            this.grade = grade;
        }

        public void assignFeedback(String feedback) {
            this.feedbackText = feedback;
        }

        public void removeFeedback() {
            this.feedbackText = null;
            this.grade = -1;
        }

        public String getFeedbackText() {
            return feedbackText != null ? feedbackText : "";
        }

        public File getAttachment() {
            return attachment;
        }

        /**
         * Compares submission based off user
         *
         * @param object Submission to compare to
         * @return true if the user is equal
         */
        public boolean equals(Object object) {
            if (object != null && object instanceof Submission) {
                Submission otherSubmission = (Submission) object;
                return this.getUsername().equals(otherSubmission.getUsername());
            } else {
                return false;
            }
        }

        /**
         * Compares submissions based off user
         *
         * @param otherSubmission
         * @return
         */
        public int compareTo(Submission otherSubmission) {
            return this.getUsername().compareTo(otherSubmission.getUsername());
        }

        @Override
        public String toString() {
            return new StringBuilder()
                    .append(getUsername())
                    .append("\t\t|\t")
                    .append(getSubmissionDate().format(DTFORMATTER_LONG))
                    .append("\t|\t")
                    .append(getGrade() > -1.0 ? getGrade() : "Not Graded")
                    .toString();
        }
    }
}
