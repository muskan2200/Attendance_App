package in.ymcaapp.sbsdevs.collegeattendanceapp;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by abhey singh on 30-10-2015.
 */
public class Constants {

    public static final String IsLoggedIn = "isUserLoggedIn";
    public static final String SharedPrefName = "UserSharedPref";
    public static final String UserName = "UserName";
    public static final String UserEmail = "UserEmail";
    public static final String UserPassword = "UserPassword";
    public static final String Department = "Department";
    public static final String Semester = "Semester";
    public static final String Subject = "Subject";

    public static final String StudentSharedPrefName = "StudentSharedPref";
    public static final String IsStudentLogin = "isStudentLogin";
    public static final String LoggedInStudentRoll = "StudentRoll";


    public static final String LoginExtra = "ExtraForLoginIntent";
    public static final String LoginAttempt ="LoginAttempt";
    public static final String RegisterAttempt ="RegisterAttempt";
    public static final String TeacherDashboard = "DashBoard";


    public static final String WebDev2Call = "tel:"+"8826989343";
    public static final String WebDev1Call = "tel:"+"9911321154";
    public static final String AndroidDevCall = "tel:"+"9050567809";

    public static final String BaseLoginUrl = "http://www.sbsdevattend.tk/loginservice.php?";
    public static final String BaseGetClassesUrl = "http://www.sbsdevattend.tk/classservice.php?";
    public static final String BaseGetRollUrl = "http://www.sbsdevattend.tk/rollnoservice.php?";
    public static final String BaseForgotPasswordUrl = "http://www.sbsdevattend.tk/forgotpasswordservice.php?";
    public static final String BaseCrNumberUrl = "http://www.sbsdevattend.tk/crnumbers.php?id=appcall";
    public static final String BaseAddAttendanceUrl = "http://www.sbsdevattend.tk/updateservice.php?";
    public static final String BaseGetStudentList = "http://www.sbsdevattend.tk/attendancerecordservice.php?roll=";
    public static final String BaseCheckRollUrl = "http://www.sbsdevattend.tk/checkrollservice.php?roll=";

    public static final String[] DepartmentList = new String[]{"CE","IT","MECH1","MECH2","ECE","EIC","EL"};



    public static ArrayList<CrDataObject> getSampleCrRecord(){
        ArrayList<CrDataObject> sampleCrList = new ArrayList<>();

        sampleCrList.add(new CrDataObject("Rahul Yadav","Ce","3","9050567809"));
        sampleCrList.add(new CrDataObject("Nitin Yadav","Ce","3","9911321154"));
        sampleCrList.add(new CrDataObject("Aman Yadav","Ce","5","8607058234"));
        sampleCrList.add(new CrDataObject("Pawan Yadav","Ce","6","9971955542"));
        sampleCrList.add(new CrDataObject("Virendra Yadav","Ce","8","8826989343"));
        sampleCrList.add(new CrDataObject("Sandeep Yadav","Ce","6","9416261864"));
        sampleCrList.add(new CrDataObject("Randeep Yadav","Ce","3","8527124207"));
        sampleCrList.add(new CrDataObject("Mahesh Singh Yadav","Ce","3","8447416193"));
        sampleCrList.add(new CrDataObject("Namit Yadav","Ce","3","8800124989"));
        sampleCrList.add(new CrDataObject("Ajay Yadav","Ce","3","8059873071"));
        sampleCrList.add(new CrDataObject("Amit Yadav","Ce","3","9466310225"));

        return sampleCrList;
    }
    public static ArrayList<TeacherDataObject> getSampleRecord(){
        ArrayList<TeacherDataObject> sampleCrList = new ArrayList<>();

        sampleCrList.add(new TeacherDataObject("Rahul Yadav","Ce","H.O.D","9050567809"));
        sampleCrList.add(new TeacherDataObject("Nitin Yadav","Ce","H.O.D","9050567809"));
        sampleCrList.add(new TeacherDataObject("Aman Yadav","Ce","H.O.D","9050567809"));
        sampleCrList.add(new TeacherDataObject("Pawan Yadav","Ce","H.O.D","9050567809"));
        sampleCrList.add(new TeacherDataObject("Mahesh Yadav","Ce","H.O.D","9050567809"));
        sampleCrList.add(new TeacherDataObject("Namit Yadav","Ce","H.O.D","9050567809"));
        sampleCrList.add(new TeacherDataObject("Ajay Yadav","Ce","H.O.D","9050567809"));
        sampleCrList.add(new TeacherDataObject("Amit Yadav","Ce","H.O.D","9050567809"));

        return sampleCrList;
    }

    public static ArrayList<StudentAttendanceShowFragment.AttendanceShowObject> getSampleAttendanceRecord(){
        ArrayList<StudentAttendanceShowFragment.AttendanceShowObject> sampleList = new ArrayList<>();

        sampleList.add(new StudentAttendanceShowFragment.AttendanceShowObject("DECO","3"));
        sampleList.add(new StudentAttendanceShowFragment.AttendanceShowObject("Physics","1"));
        sampleList.add(new StudentAttendanceShowFragment.AttendanceShowObject("Maths","4"));
        sampleList.add(new StudentAttendanceShowFragment.AttendanceShowObject("Physics2","1"));
        sampleList.add(new StudentAttendanceShowFragment.AttendanceShowObject("DECO","3"));
        sampleList.add(new StudentAttendanceShowFragment.AttendanceShowObject("Physics","1"));
        sampleList.add(new StudentAttendanceShowFragment.AttendanceShowObject("Maths","4"));
        sampleList.add(new StudentAttendanceShowFragment.AttendanceShowObject("Physics2","1"));
        return sampleList;
    }

}
