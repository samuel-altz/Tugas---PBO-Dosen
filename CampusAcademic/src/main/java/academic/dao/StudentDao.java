package academic.dao;

import academic.db.DatabaseConnection;
import academic.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    //membuat void insert untuk menambahkan mahasiswa baru kedalam database
    public void insert(Student student) {
        String sql = "INSERT INTO students (student_number, full_name) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getStudentNumber());
            pstmt.setString(2, student.getFullName());
            pstmt.executeUpdate();
            System.out.println("Data mahasiswa berhasil ditambahkan.");
            
            //error handling jika nim yang dimasukkan sama dengan nim yang ada di database
        } catch (SQLException e) {
            // ErrorCode 01 berarti ada yang duplikat(NIM sudah ada)
            if (e.getErrorCode() == 01) {
                System.err.println("GAGAL: NIM '" + student.getStudentNumber() + "' sudah terdaftar! Gunakan NIM lain.");
            } else {
                System.err.println("Error Database: " + e.getMessage());
            }
        }
    }

//    Untuk menampilkan seluruh data yang ada di tabel Student
    public List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                list.add(new Student(
                    rs.getInt("student_id"),
                    rs.getString("student_number"),
                    rs.getString("full_name")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error Database: " + e.getMessage());
        }
        return list;
    }

    //untuk mengubah data dari tabel Student
    public void update(Student student) {
        String sql = "UPDATE students SET student_number = ?, full_name = ? WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getStudentNumber());
            pstmt.setString(2, student.getFullName());
            pstmt.setInt(3, student.getStudentId());
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) System.out.println("Data berhasil diupdate.");
            else System.out.println("ID tidak ditemukan.");
            
        } catch (SQLException e) {
             // ErrorCode 01 berarti ada yang duplikat(NIM sudah ada)
            if (e.getErrorCode() == 01) {
                System.err.println("GAGAL: NIM '" + student.getStudentNumber() + "' sudah dipakai mahasiswa lain!");
            } else {
                System.err.println("Error Database: " + e.getMessage());
            }
        }
    }

    // untuk menghapus data dari tabel student
    public void delete(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) System.out.println("Data berhasil dihapus.");
            else System.out.println("ID tidak ditemukan.");
            
        } catch (SQLException e) {
            // Error Code 02 berarti tidak bisa menghapus karena ada fk(dari enrollment)
            if (e.getErrorCode() == 02) {
                System.err.println("GAGAL HAPUS: Mahasiswa ini sudah memiliki data Enrollment (mengambil mata kuliah). Hapus data enrollment-nya terlebih dahulu!");
            } else {
                System.err.println("Error Database: " + e.getMessage());
            }
        }
    }
}