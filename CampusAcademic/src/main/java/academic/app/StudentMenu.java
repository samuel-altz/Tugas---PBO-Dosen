package academic.app;

import academic.dao.StudentDao;
import academic.model.Student;
import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    public static void main(String[] args) {
        studentMenu();
    }

    public static void studentMenu() {
        StudentDao dao = new StudentDao();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== MENU MAHASISWA ===");
            System.out.println("1. Tambah Mahasiswa");
            System.out.println("2. Lihat Semua Mahasiswa");
            System.out.println("3. Update Mahasiswa");
            System.out.println("4. Hapus Mahasiswa");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            String pilih = scanner.nextLine();

            switch (pilih) {
                case "1":
                    System.out.print("NIM: ");
                    String nim = scanner.nextLine();
                    System.out.print("Nama Lengkap: ");
                    String nama = scanner.nextLine();
                    //memanggil fungsi insert dari StudentDao
                    dao.insert(new Student(0, nim, nama));
                    break;
                    
//              menampilkan seluruh data dengan memanggil fungsi list
//              jika data kosong(null) akan menampilkan output Belum ada data                  
                case "2":
                    List<Student> list = dao.getAll();
                    if (list.isEmpty()) System.out.println("(Belum ada data)");
                    else list.forEach(System.out::println);
                    break;
                    
                case "3":
                    System.out.print("ID Mahasiswa yang diupdate: ");
                    int idUpd = Integer.parseInt(scanner.nextLine());
                    System.out.print("NIM Baru: ");
                    String nimUpd = scanner.nextLine();
                    System.out.print("Nama Baru: ");
                    String namaUpd = scanner.nextLine();
//                  memanggil fungsi update untuk mengubah data
                    dao.update(new Student(idUpd, nimUpd, namaUpd));
                    break;
                case "4":
                    System.out.print("ID Mahasiswa yang dihapus: ");
//                  memfilter berdasarkan Id mahasiswa
                    int idDel = Integer.parseInt(scanner.nextLine());
//                  memanggil fungsi delete dari StudentDao
                    dao.delete(idDel);
                    break;
                case "0":
                    System.out.println("Keluar...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    } 
}