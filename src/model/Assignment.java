/**
 * @author Luis Guzman
 * @author Forrest Smith
 */
package model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getPublishDate() {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(publishDate), ZoneId.systemDefault());
    }

    public LocalDateTime getDueDate() {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(dueDate), ZoneId.systemDefault());
    }

    public ArrayList<Submission> getStudentSubmissions() {
        return studentSubmissions;
	}
	
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

    /********************
     * SUBMISSION CLASS *
     ********************/

    public static class Submission implements Comparable<Submission> {
        private String username, submissionText, feedbackText;
        private long submissionDate;
        private long grade;

        public Submission(String username, String submissionText) {
            this.username = username;
            this.submissionText = submissionText;
            this.submissionDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
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
            return feedbackText;
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
