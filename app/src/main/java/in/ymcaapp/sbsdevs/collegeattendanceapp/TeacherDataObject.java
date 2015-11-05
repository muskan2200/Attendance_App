package in.ymcaapp.sbsdevs.collegeattendanceapp;

/**
 * Created by abhey singh on 04-11-2015.
 */
public class TeacherDataObject {
    private String Name;
    private String Department;
    private String Position;
    private String Phone;

    public TeacherDataObject(String name, String department, String position,String phone) {
        this.Department = department;
        this.Position = position;
        this.Phone = phone;
        this.Name=name;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        this.Department = department;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        this.Position = position;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
}
