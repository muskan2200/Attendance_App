package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;

public class StudentMain extends AppCompatActivity {

    public static Toolbar MaterialToolbar;
    private StudentSharedPref sharedPrefUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        sharedPrefUtil = new StudentSharedPref(getApplicationContext());
        MaterialToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(MaterialToolbar);
        setTitle(Constants.TeacherDashboard);

        Intent getAttemptType = getIntent();
        String AttemptType = getAttemptType.getStringExtra(Constants.LoginExtra);

        if(!sharedPrefUtil.getIsLoggedIn()) {
            if (AttemptType.matches(Constants.LoginAttempt)) {
                setTitle("LOGIN");
                startStudentLoginFragment();
            }
        }
        else {
            setTitle(Constants.TeacherDashboard);
            startStudentFragment();
        }
    }


    public void startStudentLoginFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.studentFragmentContainer, new StudentLoginFragment());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public void startStudentFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.studentFragmentContainer, new OptionsForStudentFragment());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

}