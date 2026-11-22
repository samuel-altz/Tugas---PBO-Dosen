/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academic.model;

public class Lecturer {

    private int lecturerId;
    private String lecturerCode;
    private String fullName;

    public Lecturer(
    String lecturerCode,
    String fullName) {

    this.lecturerCode = lecturerCode;
    this.fullName = fullName;
    }

    public Lecturer(
    int lecturerId,
    String lecturerCode,
    String fullName) {

    this.lecturerId = lecturerId;
    this.lecturerCode = lecturerCode;
    this.fullName = fullName;
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public String getLecturerCode() {
        return lecturerCode;
    }

    public String getFullName() {
        return fullName;
    }
}