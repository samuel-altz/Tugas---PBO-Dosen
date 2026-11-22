package academic.model;

//membuat atribut student menjadi private
public class Student {
    private int studentId;
    private String studentNumber;
    private String fullName;

    public Student() {}

//    menambahkan this agar dapat memanggil atribut dari student
    public Student(int studentId, String studentNumber, String fullName) {
        this.studentId = studentId;
        this.studentNumber = studentNumber;
        this.fullName = fullName;
    }

    // Getter Setter
    public int getStudentId() { return studentId; }
    
    public void setStudentId(int studentId) { this.studentId = studentId; }
    
    public String getStudentNumber() { return studentNumber; }
    
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    
    public String getFullName() { return fullName; }
    
    public void setFullName(String fullName) { this.fullName = fullName; }

    @Override
    public String toString() {
        return "ID: " + studentId + " | NIM: " + studentNumber + " | Nama: " + fullName;
    }
}