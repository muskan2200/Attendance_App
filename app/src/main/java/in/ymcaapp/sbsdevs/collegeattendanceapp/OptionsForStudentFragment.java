package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by abhey singh on 04-11-2015.
 */
public class OptionsForStudentFragment extends Fragment {

    private Button CheckAttendance,ContactTeacher,LogOut;
    private StudentSharedPref sharedPrefUtil;
    private TextView RollNoText;


    public OptionsForStudentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefUtil = new StudentSharedPref(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.optionsforstudentfragment,container,false);
        getActivity().setTitle("DashBoard");
        RollNoText = (TextView)v.findViewById(R.id.rollNoTextView);
        RollNoText.setText(""+sharedPrefUtil.getStudentRoll());
        CheckAttendance = (Button)v.findViewById(R.id.checkAttendance);
        ContactTeacher = (Button)v.findViewById(R.id.contactTeacher);
        LogOut = (Button)v.findViewById(R.id.logOut);
        LogOut.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        RollNoText.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        CheckAttendance.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        ContactTeacher.setTypeface(Util_Class.setNewTextStyle(getActivity()));

        CheckAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCheckAttendanceFragment();
            }
        });

        ContactTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startContactTeacherFragment();
            }
        });

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefUtil.logOutUser();
                Intent LauncherRedirect = new Intent(getActivity(),Launcher.class);
                LauncherRedirect.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LauncherRedirect);
            }
        });
        return v;
    }

    public void startCheckAttendanceFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.studentFragmentContainer, new StudentAttendanceShowFragment());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void startContactTeacherFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.studentFragmentContainer,new TeacherCallFragment());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
