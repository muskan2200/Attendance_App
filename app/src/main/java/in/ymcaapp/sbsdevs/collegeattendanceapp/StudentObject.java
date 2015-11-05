package in.ymcaapp.sbsdevs.collegeattendanceapp;

/**
 * Created by abhey singh on 30-10-2015.
 */
public class StudentObject {

    private String roll,attendence;

    public StudentObject(String rollNumber, String attendance) {
        this.roll = rollNumber;
        this.attendence = attendance;
    }

    public String getRollNumber() {
        return roll;
    }

    public void setRollNumber(String rollNumber) {
        this.roll= rollNumber;
    }

    public String getAttendance() {
        return attendence;
    }

    public void setAttendance(String attendance) {
        this.attendence = attendance;
    }
}
