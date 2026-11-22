package academic.model;

public class Course {

    private int courseId;
    private String courseCode;
    private String courseName;
    private int lecturerId;

    public Course() {
    }

    public Course(String courseCode, String courseName, int lecturerId) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.lecturerId = lecturerId;
    }

    public Course(int courseId, String courseCode, String courseName, int lecturerId) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.lecturerId = lecturerId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    @Override
    public String toString() {
        return "ID: " + courseId + " | Kode: " + courseCode
                + " | Nama: " + courseName + " | ID Dosen: " + lecturerId;
    }
}
