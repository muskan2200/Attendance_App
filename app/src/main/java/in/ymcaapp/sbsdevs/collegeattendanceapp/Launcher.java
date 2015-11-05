package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class Launcher extends AppCompatActivity {

    private Button startLogin, StudentLogin;
    private SharedPrefUtil sharedPrefUtil;
    private StudentSharedPref studentSharedPref;
    private View noInternetView;
    private ShimmerTextView App_Name;
    private Shimmer shimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        sharedPrefUtil = new SharedPrefUtil(getApplicationContext());
        studentSharedPref = new StudentSharedPref(getApplicationContext());
        shimmer = new Shimmer();
        shimmer.setDirection(Shimmer.ANIMATION_DIRECTION_RTL);

        App_Name = (ShimmerTextView) findViewById(R.id.app_name_text);
        App_Name.setTypeface(Util_Class.setNewTextStyle(getApplicationContext()));

        shimmer.start(App_Name);

        if (sharedPrefUtil.getIsLoggedIn()) {
            Intent alreadyLoginRedirect = new Intent(getApplicationContext(), Main.class);
            alreadyLoginRedirect.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(alreadyLoginRedirect);
        }

        if (studentSharedPref.getIsLoggedIn()) {
            Intent alreadyLoginRedirect = new Intent(getApplicationContext(), StudentMain.class);
            alreadyLoginRedirect.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(alreadyLoginRedirect);
        }
        startLogin = (Button) findViewById(R.id.startLogin);
        startLogin.setTypeface(Util_Class.setNewTextStyle(getApplicationContext()));

        StudentLogin = (Button) findViewById(R.id.startLoginStudent);
        StudentLogin.setTypeface(Util_Class.setNewTextStyle(getApplicationContext()));

        final Intent UserCheck = new Intent(Launcher.this, Main.class);

        noInternetView = View.inflate(getApplicationContext(), R.layout.networkerrorlayout, null);
        final ImageView noInternetImage = (ImageView) noInternetView.findViewById(R.id.noInternetImages);

        startLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util_Class.isNetworkAvailable(getApplicationContext())) {
                    UserCheck.putExtra(Constants.LoginExtra, Constants.LoginAttempt);
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    startActivity(UserCheck);
                } else {
                    noInternetImage.setImageResource(Util_Class.getRandomImage(Launcher.this));
                    new MaterialDialog.Builder(Launcher.this)
                            .title("No Internet Connection")
                            .customView(noInternetView, true)
                            .negativeText("Cancel")
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }
        });

        StudentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util_Class.isNetworkAvailable(getApplicationContext())) {
                    Intent StudentLogin = new Intent(Launcher.this, StudentMain.class);
                    StudentLogin.putExtra(Constants.LoginExtra, Constants.LoginAttempt);
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                    startActivity(StudentLogin);
                } else {
                    noInternetImage.setImageResource(Util_Class.getRandomImage(Launcher.this));
                    new MaterialDialog.Builder(Launcher.this)
                            .title("No Internet Connection")
                            .customView(noInternetView, true)
                            .negativeText("Cancel")
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }
        });
    }
}
