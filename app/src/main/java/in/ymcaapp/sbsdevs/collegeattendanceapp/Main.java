package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;

public class Main extends AppCompatActivity {

    public static Toolbar MaterialToolBar;
    private SharedPrefUtil sharedPrefUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefUtil = new SharedPrefUtil(getApplicationContext());

        MaterialToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(MaterialToolBar);

        Intent getAttemptType = getIntent();
        String AttemptType = getAttemptType.getStringExtra(Constants.LoginExtra);

        if(!sharedPrefUtil.getIsLoggedIn()) {
            if (AttemptType.matches(Constants.LoginAttempt)) {
                setTitle("LOGIN");
                startLoginFragment();
            }
        }
        else {
            setTitle(Constants.TeacherDashboard);
            startUserFragment();
        }
    }

    public void startLoginFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.containerFragment, new LoginFragment());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public void startUserFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.containerFragment,new OptionsForTeacher(),"UserClassInfo");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

    return super.onOptionsItemSelected(item);}



    }
