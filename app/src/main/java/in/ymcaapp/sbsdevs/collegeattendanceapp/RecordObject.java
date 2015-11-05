package in.ymcaapp.sbsdevs.collegeattendanceapp;

/**
 * Created by abhey singh on 30-10-2015.
 */
public class RecordObject {

    private String Department,Semester,Subject;

    public RecordObject(String Department,String Semester,String Subject){
        this.Department=Department;
        this.Semester=Semester;
        this.Subject=Subject;
    }


    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }
}
