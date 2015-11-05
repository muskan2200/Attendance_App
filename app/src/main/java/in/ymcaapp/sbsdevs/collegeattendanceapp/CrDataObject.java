package in.ymcaapp.sbsdevs.collegeattendanceapp;

/**
 * Created by abhey singh on 01-11-2015.
 */
public class CrDataObject {
    private String Name;
    private String Department;
    private String Semester;
    private String PhoneNumber;

    public CrDataObject(String name, String department, String semester, String phoneNumber) {
        this.Name = name;
        this.Department = department;
        this.Semester = semester;
        this.PhoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        this.Department = department;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        this.Semester = semester;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.PhoneNumber = phoneNumber;
    }
}
