package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by abhey singh on 03-11-2015.
 */
public class TeacherProfileFragment extends Fragment {

    private TextView TeacherName,TeacherEmail;
    private ImageView TeacherProfilePic;
    private SharedPrefUtil sharedPrefUtil;
    public TeacherProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefUtil = new SharedPrefUtil(getActivity());
        setHasOptionsMenu(true);
        Main.MaterialToolBar.setNavigationIcon(R.mipmap.ic_launcher);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.teacherprofilefragment,container,false);
        getActivity().setTitle("My Profile");
        TeacherEmail = (TextView)v.findViewById(R.id.teacherEmail);
        TeacherName = (TextView)v.findViewById(R.id.teacherName);
        TeacherEmail.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        TeacherName.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        TeacherProfilePic = (ImageView)v.findViewById(R.id.teacherProfileDP);
        TeacherProfilePic.setImageResource(R.drawable.ic_profile);
        TeacherName.setText("" + sharedPrefUtil.getUserName());
        TeacherEmail.setText("" + sharedPrefUtil.getUserEmail());
        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id==android.R.id.home){
            Intent backRedirect = new Intent(getActivity(),Main.class);
            backRedirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(backRedirect);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
