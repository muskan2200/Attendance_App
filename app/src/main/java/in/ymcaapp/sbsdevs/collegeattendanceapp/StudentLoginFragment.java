package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Intent;
import android.graphics.DashPathEffect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONObject;

/**
 * Created by abhey singh on 04-11-2015.
 */
public class StudentLoginFragment extends Fragment {

    private Spinner DepartmentSpinner,BatchSpinner;
    private EditText RollNo;
    private Button StudentLoginButton;
    private StudentSharedPref sharedPrefUtil;
    private String DepTemp,BatchTemp;

    public StudentLoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefUtil = new StudentSharedPref(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.studentloginfragment,container,false);
        getActivity().setTitle("LOGIN");
        DepartmentSpinner = (Spinner)v.findViewById(R.id.departmentSpinner);
        BatchSpinner = (Spinner)v.findViewById(R.id.batchSpinner);
        RollNo = (EditText)v.findViewById(R.id.rollNoInput);
        StudentLoginButton = (Button)v.findViewById(R.id.studentLogin);
        DepartmentSpinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.support_simple_spinner_dropdown_item,Constants.DepartmentList));
        BatchSpinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.support_simple_spinner_dropdown_item,Util_Class.getBatchList()));
        //By Default Takiing values

        RollNo.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        StudentLoginButton.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        DepTemp = Constants.DepartmentList[0];
        BatchTemp = Util_Class.getBatchList().get(0);
        //
        DepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DepTemp = Constants.DepartmentList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        BatchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BatchTemp = Util_Class.getBatchList().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        StudentLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RollNo.getText().toString().trim().length() != 4) {
                    Util_Class.LongToast(getActivity(),"Invalid RollNumber");
                } else {
                    String Roll= DepTemp+"-"+RollNo.getText().toString()+"-"+BatchTemp;
                    sharedPrefUtil.setStudentRoll(Roll);
                    new LoginExecutor().execute(Constants.BaseCheckRollUrl+Roll);
                }
            }
        });
        return v;
    }

    private class LoginExecutor extends AsyncTask<String,Void,String> {

        MaterialDialog materialDialog;
        Boolean bool;
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
            if(bool){
                sharedPrefUtil.setIsLoggedIn();
                Intent redirect = new Intent(getActivity(),StudentMain.class);
                redirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(redirect);
            }
            else{
                Util_Class.LongToast(getActivity(), "Problem With Roll Number Or Connection Is Slow");
            }
        }
        @Override
        protected String doInBackground(String... params) {
            bool = getValidRoll(params[0]);
            return null;
        }
    }
    public Boolean getValidRoll(String Roll){

        JSONObject jsonObject;
        jsonObject = Util_Class.getJSONFromUrl(Roll);

        try {
            if (jsonObject.getBoolean("status")) {
                return true;
            }
            else if (jsonObject==null){
                return false;
            }
            return false;
        }
        catch (Exception j){
            j.printStackTrace();
        }
        return false;
    }
}