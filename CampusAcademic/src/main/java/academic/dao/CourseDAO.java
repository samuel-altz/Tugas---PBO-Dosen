package academic.dao;

import academic.db.DatabaseConnection;
import academic.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDAO {

    public int createCourse(Course course) throws SQLException {

        String sql =
            "INSERT INTO courses "
            + "(course_code, course_name, lecturer_id) "
            + "VALUES (?, ?, ?)";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getLecturerId());

            return stmt.executeUpdate();
        }
    }

    public void showAllCourses() throws SQLException {

        String sql =
            "SELECT c.course_id, c.course_code, c.course_name, "
            + "c.lecturer_id, l.full_name AS lecturer_name "
            + "FROM courses c "
            + "LEFT JOIN lecturers l "
            + "ON c.lecturer_id = l.lecturer_id "
            + "ORDER BY c.course_id";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {

            boolean found = false;

            while (rs.next()) {
                found = true;

                int lecturerId = rs.getInt("lecturer_id");
                String lecturerIdText = rs.wasNull()
                        ? "-" : String.valueOf(lecturerId);

                String lecturerName = rs.getString("lecturer_name");
                if (lecturerName == null) {
                    lecturerName = "-";
                }

                System.out.println("--------------------------");
                System.out.println(
                    "Course ID   : " + rs.getInt("course_id")
                );
                System.out.println(
                    "Course Code : " + rs.getString("course_code")
                );
                System.out.println(
                    "Course Name : " + rs.getString("course_name")
                );
                System.out.println(
                    "Lecturer ID : " + lecturerIdText
                );
                System.out.println(
                    "Lecturer    : " + lecturerName
                );
            }

            if (!found) {
                System.out.println("Data mata kuliah tidak ditemukan.");
            }
        }
    }

    public int updateCourse(int courseId, Course course)
            throws SQLException {

        String sql =
            "UPDATE courses "
            + "SET course_code = ?, course_name = ?, lecturer_id = ? "
            + "WHERE course_id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setInt(3, course.getLecturerId());
            stmt.setInt(4, courseId);

            return stmt.executeUpdate();
        }
    }

    public int deleteCourse(int courseId) throws SQLException {

        String sql =
            "DELETE FROM courses WHERE course_id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, courseId);

            return stmt.executeUpdate();
        }
    }

    public boolean lecturerExists(int lecturerId) throws SQLException {

        String sql =
            "SELECT 1 FROM lecturers WHERE lecturer_id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, lecturerId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
