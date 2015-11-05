package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abhey singh on 30-10-2015.
 */
public class LoginFragment extends Fragment {

    private AutoCompleteTextView LoginId,LoginPassword;
    private Button LoginButton,ForgotPasswordButton;
    private SharedPrefUtil sharedPrefUtil;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        Main.MaterialToolBar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.teacherloginfragment,container,false);
        sharedPrefUtil = new SharedPrefUtil(getActivity());
        LoginId = (AutoCompleteTextView)v.findViewById(R.id.loginId);
        LoginPassword = (AutoCompleteTextView)v.findViewById(R.id.loginPassword);
        LoginButton = (Button)v.findViewById(R.id.loginButton);
        ForgotPasswordButton = (Button)v.findViewById(R.id.forgotPasswordbutton);
        LoginButton.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        LoginId.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        LoginPassword.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        ForgotPasswordButton.setTypeface(Util_Class.setNewTextStyle(getActivity()));

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Util_Class.isNetworkAvailable(getActivity())) {
                    if (Util_Class.isValidEmail(LoginId.getText().toString()) && LoginPassword.getText().toString().trim().length() > 0) {
                        sharedPrefUtil.setUserEmail(LoginId.getText().toString());
                        sharedPrefUtil.setUserPassword(LoginPassword.getText().toString());
                        new LoginExecutor().execute(LoginId.getText().toString(), LoginPassword.getText().toString());
                    } else {
                        Util_Class.LongToast(getActivity(), "There Is Some Problem With Email ID And Password");
                    }
                }

                else {
                    Util_Class.LongToast(getActivity(),"Check Inernet Connection");
                }
            }
        });

        ForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MaterialDialog.Builder(getActivity())
                        .positiveText("GO")
                        .input("Enter Your E-Mail Id", "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                                if (Util_Class.isNetworkAvailable(getActivity())){
                                    if (Util_Class.isValidEmail(charSequence.toString())){
                                        materialDialog.dismiss();
                                        new ForgotPasswordExecutor().execute(charSequence.toString());
                                    }
                                    else {
                                        Util_Class.LongToast(getActivity(),"Wrong E-mail Id");
                                        materialDialog.dismiss();
                                    }
                                }
                                else {
                                    Util_Class.LongToast(getActivity(),"No Network Available");
                                    materialDialog.dismiss();
                                }
                            }
                        })
                        .show();
            }
        });
        return v;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home){
            Intent backRedirect = new Intent(getActivity(),Launcher.class);
            backRedirect.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(backRedirect);
        }
        return super.onOptionsItemSelected(item);
    }

   private class LoginExecutor extends AsyncTask<String,Void,String>{

       MaterialDialog materialDialog;
       @Override
       protected void onPreExecute() {
           materialDialog = new MaterialDialog.Builder(getActivity())
                   .progress(true,0)
                   .cancelable(false)
                   .content("Connecting To Our Servers")
                   .show();
       }
       @Override
       protected void onPostExecute(String s) {
           materialDialog.cancel();
           if(s!=null){
               sharedPrefUtil.setUserName(s);
               sharedPrefUtil.setIsLoggedIn();
               Intent redirect = new Intent(getActivity(),Main.class);
               redirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(redirect);
               }
           else{
               Util_Class.LongToast(getActivity(), "There Is Some Problem With Email ID And Password");
           }
       }
       @Override
       protected String doInBackground(String... params) {
           String TeacherName;
           TeacherName = getValidTeacherName(params[0], params[1]);
           return TeacherName;
       }
   }

    public String getValidTeacherName(String EmailId, String Password){

        JSONObject jsonObject;
        String TeacherName = null;
        String HalfLoginUrl = "email="+EmailId+"&password="+Password;
        String completeUrl = Constants.BaseLoginUrl+HalfLoginUrl;
        completeUrl.replace(" ","%20");

        jsonObject = Util_Class.getJSONFromUrl(completeUrl);

        try {
            if (!jsonObject.getBoolean("status")) {
                return null;
            }
            else {
                TeacherName = jsonObject.getString("name");
            }
        }
        catch (Exception j){
            j.printStackTrace();
        }
        return TeacherName;
    }

    private class ForgotPasswordExecutor extends AsyncTask<String,Void,Void>{

        MaterialDialog materialDialog;
        Boolean status;
        @Override
        protected void onPreExecute() {
            materialDialog = new MaterialDialog.Builder(getActivity())
                    .progress(true,0)
                    .cancelable(false)
                    .content("Connecting To Our Servers")
                    .show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            materialDialog.dismiss();
            if(status){
                new MaterialDialog.Builder(getActivity())
                        .content("Your New Password Is Sent To Your Email ID")
                        .show();
            }
            else {
                Util_Class.LongToast(getActivity(),"No InterNet");
            }

        }

        @Override
        protected Void doInBackground(String... params) {
            status = getForgotPasswordStatus(params[0]);
            return null;
        }
    }

    public boolean getForgotPasswordStatus(String EmailId){
        JSONObject jsonObject;
        String CompleteUrl = Constants.BaseForgotPasswordUrl+"Email="+EmailId;
        Boolean status = false;
        CompleteUrl.replace(" ","%20");
        jsonObject = Util_Class.getJSONFromUrl(CompleteUrl);

        try{
            if (jsonObject.getBoolean("status")){
                status = true;
            }
        }catch (Exception j){
            j.printStackTrace();
        }

        return status;
    }
}
