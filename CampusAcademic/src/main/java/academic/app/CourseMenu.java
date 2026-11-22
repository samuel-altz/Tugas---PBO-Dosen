package academic.app;

import academic.dao.CourseDAO;
import academic.model.Course;
import java.sql.SQLException;
import java.util.Scanner;

public class CourseMenu {

    private final CourseDAO dao = new CourseDAO();

    public void run(Scanner scanner) {
        while (true) {
            System.out.println("\nMANAJEMEN MATA KULIAH");
            System.out.println("1. Tambah Mata Kuliah");
            System.out.println("2. Lihat Semua Mata Kuliah");
            System.out.println("3. Ubah Mata Kuliah");
            System.out.println("4. Hapus Mata Kuliah");
            System.out.println("0. Kembali");
            System.out.print("Pilih menu: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1": {
                        System.out.print("Kode Mata Kuliah: ");
                        String code = scanner.nextLine().trim();
                        System.out.print("Nama Mata Kuliah: ");
                        String name = scanner.nextLine().trim();
                        int lecturerId = readInt(scanner, "ID Dosen: ");

                        if (code.isEmpty() || name.isEmpty()
                                || lecturerId <= 0) {
                            System.out.println("Data mata kuliah tidak valid.");
                            break;
                        }

                        if (!dao.lecturerExists(lecturerId)) {
                            System.out.println(
                                "Dosen dengan ID " + lecturerId
                                + " tidak ditemukan."
                            );
                            break;
                        }

                        int rows = dao.createCourse(
                            new Course(code, name, lecturerId)
                        );

                        System.out.println(rows == 1
                                ? "Mata kuliah berhasil ditambahkan"
                                : "Mata kuliah gagal ditambahkan");
                        break;
                    }

                    case "2": {
                        dao.showAllCourses();
                        break;
                    }

                    case "3": {
                        int id = readInt(scanner, "ID Mata Kuliah: ");
                        System.out.print("Kode Mata Kuliah Baru: ");
                        String code = scanner.nextLine().trim();
                        System.out.print("Nama Mata Kuliah Baru: ");
                        String name = scanner.nextLine().trim();
                        int lecturerId = readInt(scanner, "ID Dosen Baru: ");

                        if (id <= 0 || code.isEmpty() || name.isEmpty()
                                || lecturerId <= 0) {
                            System.out.println("Data mata kuliah tidak valid.");
                            break;
                        }

                        if (!dao.lecturerExists(lecturerId)) {
                            System.out.println(
                                "Dosen dengan ID " + lecturerId
                                + " tidak ditemukan."
                            );
                            break;
                        }

                        int rows = dao.updateCourse(
                            id, new Course(code, name, lecturerId)
                        );

                        System.out.println(rows > 0
                                ? "Data mata kuliah diubah"
                                : "Mata kuliah tidak ditemukan");
                        break;
                    }

                    case "4": {
                        int id = readInt(scanner, "ID Mata Kuliah: ");
                        int rows = dao.deleteCourse(id);

                        System.out.println(rows > 0
                                ? "Mata kuliah dihapus"
                                : "Mata kuliah tidak ditemukan");
                        break;
                    }

                    case "0":
                        return;

                    default:
                        System.out.println("Masukan Hanya 0 - 4");
                }

            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang benar");

            } catch (SQLException e) {
                handleSqlError(e);
            }
        }
    }

    private void handleSqlError(SQLException e) {
        switch (e.getErrorCode()) {
            case 1062:
                System.out.println("Kode mata kuliah sudah terdaftar.");
                break;
            case 1451:
                System.out.println(
                    "Tidak bisa dihapus karena mata kuliah ini "
                    + "sudah dipakai di enrollment."
                );
                break;
            case 1452:
                System.out.println(
                    "Dosen dengan ID tersebut tidak ditemukan."
                );
                break;
            default:
                System.out.println("Operasi database gagal!");
                System.out.println("Pesan: " + e.getMessage());
                System.out.println("SQL State: " + e.getSQLState());
        }
    }

    private static int readInt(Scanner scanner, String label) {
        System.out.print(label);
        return Integer.parseInt(
            scanner.nextLine().trim());
    }
}
