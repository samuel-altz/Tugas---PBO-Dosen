/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academic.app;
import academic.dao.LecturerDAO;
import academic.model.Lecturer;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author ment
 */

public class LecturerMenu {

    private final LecturerDAO dao = new LecturerDAO();

    public void run(Scanner scanner) {
        while (true) {
            System.out.println("MANAJEMEN DOSEN");
            System.out.println("1. Tambah Dosen");
            System.out.println("2. Lihat Semua Dosen");
            System.out.println("3. Ubah Data Dosen");
            System.out.println("4. Hapus Dosen");
            System.out.println("0. Kembali");
            System.out.print("Pilih menu: ");

            String choice = scanner.nextLine().trim();

            try {
            switch (choice) {
                case "1": {
                System.out.print("Kode Dosen: ");
                String code = scanner.nextLine().trim();
                System.out.print("Nama Lengkap: ");
                String name = scanner.nextLine().trim();

                if (code.isEmpty() || name.isEmpty()) {
                System.out.println("Data dosen tidak valid.");
                break;
                }

                int rows = dao.createLecturer(
                new Lecturer(code, name)
                );

                System.out.println(rows == 1 ? "Dosen berhasil ditambahkan" : "Dosen gagal ditambahkan");
                break;
                }

                case "2": {
                dao.showAllLecturers();
                break;
                }

               case "3": {
               int id = readInt(scanner, "ID Dosen: ");
               System.out.print("Kode Dosen Baru: ");
               String code = scanner.nextLine().trim();
               System.out.print("Nama Lengkap Baru: ");
               String name = scanner.nextLine().trim();

               if (id <= 0 || code.isEmpty() || name.isEmpty()) {
               System.out.println("Data dosen tidak valid.");
               break;
               }

               int rows = dao.updateLecturer(
               id, new Lecturer(code, name));

               System.out.println(rows > 0 ? "Data dosen diubah" : "Dosen tidak ada");
               break;
               }

               case "4": {
               int id = readInt(scanner, "ID Dosen: ");
               int rows = dao.deleteLecturer(id);
               System.out.println(rows > 0 ? "Dosen dihapus" : "Dosen tidak ditemukan");
               break;
               }

               case "0":
               return;

               default:
               System.out.println("Masukan Hanya 0 - 4");}

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
                System.out.println("Kode dosen sudah terdaftar.");
                break;
            case 1451:
                System.out.println("Tidak bisa dihapus karena dosen ini masih mengampu mata kuliah.");
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