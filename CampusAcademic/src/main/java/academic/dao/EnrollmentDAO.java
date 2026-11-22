

package academic.dao;

import academic.db.DatabaseConnection;
import academic.model.Enrollment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnrollmentDAO {
    public int createEnrollment(Enrollment enrollment)
            throws SQLException {

        String sql =
            "INSERT INTO enrollments "
            + "(student_id, course_id, semester) "
            + "VALUES (?, ?, ?)";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, enrollment.getStudentId());
            stmt.setInt(2, enrollment.getCourseId());
            stmt.setString(3, enrollment.getSemester());

            return stmt.executeUpdate();
        }
    }
    
    public void showAllEnrollments() throws SQLException {
        String sql =
            "SELECT e.enrollment_id, s.student_number, "
            + "s.full_name AS student_name, "
            + "c.course_code, c.course_name, "
            + "l.full_name AS lecturer_name, "
            + "e.semester, e.grade "
            + "FROM enrollments e "
            + "JOIN students s ON e.student_id = s.student_id "
            + "JOIN courses c ON e.course_id = c.course_id "
            + "JOIN lecturers l ON c.lecturer_id = l.lecturer_id "
            + "ORDER BY e.enrollment_id";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                String grade = rs.getString("grade");

                if (grade == null) {
                    grade = "-";
                }

                System.out.println("--------------------------");
                System.out.println(
                    "Enrollment ID : " + rs.getInt("enrollment_id")
                );
                System.out.println(
                    "Student       : " + rs.getString("student_name")
                );
                System.out.println(
                    "Student Number: " + rs.getString("student_number")
                );
                System.out.println(
                    "Course        : " + rs.getString("course_name")
                );
                System.out.println(
                    "Course Code   : " + rs.getString("course_code")
                );
                System.out.println(
                    "Lecturer      : " + rs.getString("lecturer_name")
                );
                System.out.println(
                    "Semester      : " + rs.getString("semester")
                );
                System.out.println(
                    "Grade         : " + grade
                );
            }

            if (!found) {
                System.out.println("No enrollments found.");
            }
        }
    }
    
    public int updateGrade(int enrollmentId, String grade) throws SQLException {
        String sql =
            "UPDATE enrollments SET grade = ? "
            + "WHERE enrollment_id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, grade);
            stmt.setInt(2, enrollmentId);

            return stmt.executeUpdate();
        }
    }
    
    public int deleteEnrollment(int enrollmentId) throws SQLException {
        String sql =
            "DELETE FROM enrollments WHERE enrollment_id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, enrollmentId);

            return stmt.executeUpdate();
        }
    }    
}

