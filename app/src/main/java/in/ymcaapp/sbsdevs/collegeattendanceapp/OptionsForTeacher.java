package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Intent;
import android.net.Uri;
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

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by abhey singh on 30-10-2015.
 */
public class OptionsForTeacher extends Fragment{

    private Button TakeAttendance,ContactClassRep,NeedHelp,LogOut,MyProfile;
    private SharedPrefUtil  sharedPrefUtil;
    private TextView GreetingText,TodayDate;
    private Calendar calendar;


    public OptionsForTeacher() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.optionsforteacher,container,false);
        getActivity().setTitle(Constants.TeacherDashboard);
        sharedPrefUtil = new SharedPrefUtil(getActivity());
        TakeAttendance = (Button)v.findViewById(R.id.takeAttendanceButton);
        ContactClassRep = (Button)v.findViewById(R.id.contactClassRep);
        NeedHelp = (Button)v.findViewById(R.id.rateApp);
        LogOut = (Button)v.findViewById(R.id.logOut);
        MyProfile = (Button)v.findViewById(R.id.teacherProfileManager);
        GreetingText = (TextView)v.findViewById(R.id.TeacherGreetingText);
        TodayDate = (TextView)v.findViewById(R.id.TodayDate);

        TakeAttendance.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        ContactClassRep.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        NeedHelp.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        LogOut.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        MyProfile.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        GreetingText.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        TodayDate.setTypeface(Util_Class.setNewTextStyle(getActivity()));

        //HouseKeeping To Get The Today
        calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String date = dateFormat.format(calendar.getTime());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        String day = dayFormat.format(calendar.getTime());

        GreetingText.setText("Hey!! "+sharedPrefUtil.getUserName());
        TodayDate.setText(date+"("+day+")");
        TakeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUserClassesFragment();
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

        MyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTeacherProfileFragment();
            }
        });
        //defining view for the dialog to contact devs.
        final View dialogView = inflater.inflate(R.layout.contactuslayout,null);

        Button AndroidDevCall = (Button)dialogView.findViewById(R.id.androidDev);
        Button WebDev1Call = (Button)dialogView.findViewById(R.id.webDev1);
        Button WebDev2Call = (Button)dialogView.findViewById(R.id.webDev2);

        final MaterialDialog.Builder helpDialog = new MaterialDialog.Builder(getActivity());

        helpDialog.title("Call Developers");
        AndroidDevCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CallUs = new Intent(Intent.ACTION_CALL, Uri.parse(Constants.AndroidDevCall));
                startActivity(CallUs);
            }
        });

        WebDev1Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CallUs = new Intent(Intent.ACTION_CALL, Uri.parse(Constants.WebDev1Call));
                startActivity(CallUs);
            }
        });

        WebDev2Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CallUs = new Intent(Intent.ACTION_CALL, Uri.parse(Constants.WebDev2Call));
                startActivity(CallUs);
            }
        });

        NeedHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog.customView(dialogView,true);
                helpDialog.show();
            }
        });

        ContactClassRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startClassRepFragment();
            }
        });
        return v;
    }

    public void startUserClassesFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerFragment,new UserClassesInfo());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
    public void startClassRepFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerFragment,new CRCallFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public void startTeacherProfileFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerFragment,new TeacherProfileFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}
