package model;

import util.DataController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Course implements Comparable<Course> {
    private String id;
    private String title;
    private ArrayList<String> registeredStudents;
    private String professor;
	private Map<String, Float> gradeBookAutomatic;
    private Map<String, Float> gradeBookFinal;
    private ArrayList<Assignment> assignments;
    private ArrayList<Announcement> announcements;
	public static Comparator<Course> BY_COURSE_TITLE = new Comparator<Course>() {
		@Override
		public int compare(Course o1, Course o2) {
			return o1.getTitle().compareTo(o2.getTitle());
		}
	};
	public static Comparator<Announcement> BY_ANNOUNCEMENT_PUBLISH_DATE = new Comparator<Announcement>() {
		@Override
		public int compare(Announcement o1, Announcement o2) {
			return o1.getPublishDate().compareTo(o2.getPublishDate());
		}
	};

    public Course(String title, String id, Professor professor) {
        this.title = title;
        this.id = id;
        this.professor = professor.getUsername();
        registeredStudents = (ArrayList<String>) new ArrayList<Student>().stream().map(User::getUsername).collect(Collectors.toList());
        assignments = new ArrayList<Assignment>();
        announcements = new ArrayList<Announcement>();
        gradeBookFinal = new HashMap<>();
		gradeBookAutomatic = new HashMap<>();
    }

    public void addStudent(Student student) {
        if (!registeredStudents.contains(student.getUsername())) {
            registeredStudents.add(student.getUsername());
        } else {
            // duplicate student cannot be added
        }
    }

    public ArrayList<String> getRegisteredStudents() {
        return this.registeredStudents;
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

    public String getId() {
        return id;
    }

    public Professor getProfessor() {
        return (Professor) DataController.readUsers().stream().filter(prof -> prof.getUsername().equals(professor)).findFirst().orElse(null);
    }

    public String getProfessorUsername() {
        return professor;
    }

    public ArrayList<Student> getStudents() {
        ArrayList<User> allUsers = DataController.readUsers();
        ArrayList<Student> courseStudents = new ArrayList<Student>();

        for (String username : registeredStudents) {
            for (User u : allUsers) {
                if (u.getUsername().equals(username)) {
                    courseStudents.add((Student) u);
                    break;
                }
            }
        }

        return courseStudents;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public boolean addAssignment(Assignment newAss) {
        if (assignments.contains(newAss) == true) {
            return false;
        }
        boolean added = assignments.add(newAss);
        DataController.saveCourse(this);
        return added;
    }

    public boolean removeAssignment(Assignment oldAss) {
        if (assignments.contains(oldAss) == true) {
            return assignments.remove(oldAss);
        }
        return false;
    }

	public ArrayList<Assignment.Submission> getSpecificStudentSubmissions(String studentID) {
		ArrayList<Assignment.Submission> studentSubs = new ArrayList<>();
		for (Assignment ass : getAssignments()) {
			for (Assignment.Submission sub : ass.getStudentSubmissions()) {
				if (sub.getUsername().equals(studentID)) {
					studentSubs.add(sub);
				}
			}
		}
		return studentSubs;
	}

    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    public boolean addAnnouncement(Announcement announcement) {
    	if (announcements.contains(announcement)) return false;
        return announcements.add(announcement);
    }

	public boolean removeAnnouncement(Announcement announcement) {
		return announcements.remove(announcement);
	}

    public void setFinalGrade(String studentID, float gpa) {
        gradeBookFinal.put(studentID, gpa);
    }

    public void updateAutomaticGrade(String studentID) {
    	float GPA = 0.0f;
    	int completedAss = getAssignments().size();
		for (Assignment.Submission s : getSpecificStudentSubmissions(studentID)) {
			if (s.getGrade()>= 0f) {
				GPA += s.getGrade();
			} else {
				completedAss--;
			}
		}
		gradeBookAutomatic.put(studentID, 100*GPA/(completedAss * 100));
	}

    public void removeFinalGrade(String studentID) {
        gradeBookFinal.remove(studentID);
    }

	public float getStudentAutomaticGrade(String studentID) {
		return gradeBookAutomatic.getOrDefault(studentID, -1.0f);
	}

    public float getStudentFinalGrade(String studentID) {
		return gradeBookFinal.getOrDefault(studentID, -1.0f);
    }

    public Map<String, Float> getGradeBookFinal() {
        return gradeBookFinal;
    }

    public float getClassAverage() {
        return (float) gradeBookFinal.values().stream().mapToDouble(Float::doubleValue).average().orElse(0.0f);
    }

    public float getClassMedian() {
        int size = gradeBookFinal.size();
        return (float) gradeBookFinal.values().stream().mapToDouble(Float::doubleValue).sorted()
                .skip((size - 1) / 2).min().orElse(0.0f);
    }

    public float getClassMaxGrade() {
        return gradeBookFinal.values().stream().max(Comparator.naturalOrder()).get();
    }

    public float getClassMinGrade() {
        return gradeBookFinal.values().stream().min(Comparator.naturalOrder()).get();
    }

    /**
     * Compares courses based off title
     *
     * @param object Course to compare to
     * @return true if the title is equal
     */
    public boolean equals(Object object) {
        if (object != null && object instanceof Course) {
            Course otherCourse = (Course) object;
            return this.getId().equals(otherCourse.getId());
        } else {
            return false;
        }
    }

    /**
     * Compares courses based off title
     *
     * @param otherCourse
     * @return
     */
    public int compareTo(Course otherCourse) {
        return this.getId().compareTo(otherCourse.getId());
    }

    public String toString() {
        return id + ": " + title;
    }

    public static class Announcement implements Comparable<Announcement> {
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
            return LocalDateTime.ofInstant(Instant.ofEpochSecond(publishDate), ZoneId.systemDefault());
        }

        /**
         * Compares announcements based off title
         *
         * @param object Announcement to compare to
         * @return true if the title is equal
         */
        public boolean equals(Object object) {
            if (object != null && object instanceof Announcement) {
                Announcement otherAnnouncement = (Announcement) object;
                return this.getTitle().equals(otherAnnouncement.getTitle());
            } else {
                return false;
            }
        }

        /**
         * Compares announcements based off title
         *
         * @param otherAnnouncement
         * @return
         */
        public int compareTo(Announcement otherAnnouncement) {
            return this.getTitle().compareTo(otherAnnouncement.getTitle());
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
