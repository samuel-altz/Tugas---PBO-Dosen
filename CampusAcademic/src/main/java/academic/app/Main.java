
package academic.app;

import academic.dao.EnrollmentDAO;
import academic.db.DatabaseConnection;
import academic.model.Enrollment;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
                try (Connection conn =
                DatabaseConnection.getConnection()) {

            System.out.println("Database connection successful!");

        } catch (SQLException e) {

            System.out.println("Connection failed!");
            System.out.println(e.getMessage());
        }
        
        EnrollmentDAO dao = new EnrollmentDAO();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n=== CAMPUS ACADEMIC INFORMATION ===");
                System.out.println("1. Enroll Student in Course");
                System.out.println("2. View All Enrollments");
                System.out.println("3. Update Student Grade");
                System.out.println("4. Cancel Enrollment");
                System.out.println("5. Manage Lecturers");
                System.out.println("6. Manage Courses");
                System.out.println("0. Exit");
                System.out.print("Choose menu: ");

                String choice = scanner.nextLine();

                try {
                    switch (choice) {
                        case "1": {
                            int studentId = readInt(
                                scanner, "Student ID: "
                            );

                            int courseId = readInt(
                                scanner, "Course ID: "
                            );

                            System.out.print("Semester: ");
                            String semester = scanner.nextLine().trim();

                            if (studentId <= 0 || courseId <= 0
                                    || semester.isEmpty()) {
                                System.out.println("Invalid enrollment data.");
                                break;
                            }

                            Enrollment enrollment = new Enrollment(
                                studentId, courseId, semester
                            );

                            int rows = dao.createEnrollment(enrollment);

                            System.out.println(
                                rows == 1 ? "Enrollment created!"
                                          : "Enrollment not created."
                            );

                            break;
                        }

                        case "2": {
                            dao.showAllEnrollments();
                            break;
                        }

                        case "3": {
                            int enrollmentId = readInt(
                                scanner, "Enrollment ID: "
                            );

                            System.out.print("Grade (A-E): ");
                            String grade = scanner.nextLine()
                                    .trim().toUpperCase();

                            if (!grade.matches("[A-E]")) {
                                System.out.println("Invalid grade.");
                                break;
                            }

                            int rows = dao.updateGrade(
                                enrollmentId, grade
                            );

                            System.out.println(
                                rows > 0 ? "Grade updated!"
                                         : "Enrollment not found or unchanged."
                            );

                            break;
                        }

                        case "4": {
                            int enrollmentId = readInt(
                                scanner, "Enrollment ID: "
                            );

                            int rows = dao.deleteEnrollment(enrollmentId);

                            System.out.println(
                                rows > 0 ? "Enrollment deleted!"
                                         : "Enrollment not found."
                            );

                            break;
                        }
                        
                        case "5": {
                        new LecturerMenu().run(scanner);
                        break;
                        }

                        case "6": {
                        new CourseMenu().run(scanner);
                        break;
                        }

                        case "0": {
                            System.out.println("Goodbye!");
                            return;
                        }

                        default:
                            System.out.println("Invalid menu.");
                    }

                } catch (NumberFormatException e) {

                    System.out.println(
                        "Input error: Please enter a valid number."
                    );

                } catch (SQLException e) {

                    System.out.println("Database operation failed!");
                    System.out.println("Message: " + e.getMessage());
                    System.out.println("SQL State: " + e.getSQLState());
                }
            }
        }
    }

    private static int readInt(Scanner scanner, String label) {
        System.out.print(label);

        return Integer.parseInt(
            scanner.nextLine().trim()
        );
    }
}