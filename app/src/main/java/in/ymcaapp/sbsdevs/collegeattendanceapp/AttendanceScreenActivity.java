package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AttendanceScreenActivity extends AppCompatActivity {

    private ListView StudentListView;
    private RollListAdapter rollListAdapter;
    public static Toolbar MaterialToolBar;
    private Button AllPresent;
    private String UrlToCall;
    private TextView NoDataText;
    private String Department,Semester,Subject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_screen);

        MaterialToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(MaterialToolBar);
        MaterialToolBar.setNavigationIcon(R.mipmap.ic_launcher);

        ///Getting Intent Extras For The Department And Semester
        Intent ForExtras = getIntent();

        Department = ForExtras.getStringExtra(Constants.Department);
        Semester = ForExtras.getStringExtra(Constants.Semester);
        Subject = ForExtras.getStringExtra(Constants.Subject);
        StudentListView = (ListView)findViewById(R.id.studentRollList);
        NoDataText = (TextView)findViewById(R.id.noDataText);
        NoDataText.setTypeface(Util_Class.setNewTextStyle(getApplicationContext()));
        NoDataText.setText("CONNECTIVITY PROBLEM OR NO DATA AVAILABLE");
        rollListAdapter =  new RollListAdapter(getApplicationContext(),new ArrayList<StudentObject>());

        UrlToCall = Constants.BaseGetRollUrl+"department="+Department+"&semester="+Semester;
        Log.e("URL",""+UrlToCall);
        new GetAttendanceExecutor().execute(UrlToCall);
        StudentListView.setAdapter(rollListAdapter);
        AllPresent = (Button)findViewById(R.id.allPresent);
        AllPresent.setTypeface(Util_Class.setNewTextStyle(getApplicationContext()));


        AllPresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<StudentObject> NewList = rollListAdapter.getAttendanceList();
                new MaterialDialog.Builder(AttendanceScreenActivity.this)
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input("Number Of Attendance", "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                                String NumberOfAttendance = charSequence.toString();
                                for (int i = 0; i < NewList.size(); i++) {
                                    NewList.get(i).setAttendance(NumberOfAttendance);
                                    rollListAdapter.changeTheList(NewList);
                                    rollListAdapter.notifyDataSetChanged();
                                }
                            }
                        })
                        .show();
            }
        });

        StudentListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int mLastFirstVisible = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view.getId() == StudentListView.getId()) {
                    final int currentFirstVisibleTerm = StudentListView.getFirstVisiblePosition();
                    if (currentFirstVisibleTerm > mLastFirstVisible) {
                        getSupportActionBar().hide();
                    } else if (currentFirstVisibleTerm < mLastFirstVisible) {
                        getSupportActionBar().show();
                    }
                    mLastFirstVisible = currentFirstVisibleTerm;
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_attendance_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.saveAttendance) {


            String UrlToUpdate="department="+ Department+"&semester="+Semester+"&subject="+Subject+"&jsondata="+Util_Class.getJsonOfList(rollListAdapter.getAttendanceList());
            final String CompleteUrl = Constants.BaseAddAttendanceUrl+UrlToUpdate;
            CompleteUrl.replace(" ", "%20");

            new MaterialDialog.Builder(AttendanceScreenActivity.this)
                    .title("Final Attendance")
                   .adapter(new ConfirmListAdapter(rollListAdapter.getAttendanceList()), new MaterialDialog.ListCallback() {
                       @Override
                       public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {

                       }
                   })
                    .positiveText("Yes!")
                    .contentColor(Color.BLACK)
                    .negativeText("No!")
                    .cancelable(true)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            new submitAttendanceExecutor().execute(CompleteUrl);
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            dialog.cancel();
                        }
                    })
                            .show();
            return true;
        }
        else if (id==android.R.id.home){
            Intent homeRedirect = new Intent(getApplicationContext(),Main.class);
            homeRedirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(homeRedirect);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class ConfirmListAdapter extends BaseAdapter{

        private List<StudentObject> finalAttendanceList;
        private LayoutInflater inflater  = LayoutInflater.from(getApplicationContext());
        public ConfirmListAdapter(List<StudentObject> finalAttendanceList) {
            this.finalAttendanceList=finalAttendanceList;
        }


        @Override
        public int getCount() {
            return finalAttendanceList.size();
        }

        @Override
        public Object getItem(int position) {
            return finalAttendanceList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.finalcheckattendanceecustom,parent,false);

            TextView confirmRoll = (TextView)view.findViewById(R.id.confirmRollNo);
            TextView confirmAttenance = (TextView)view.findViewById(R.id.confirmAttendance);
            confirmRoll.setTypeface(Util_Class.setNewTextStyle(getApplicationContext()));
            confirmAttenance.setTypeface(Util_Class.setNewTextStyle(getApplicationContext()));

            confirmRoll.setText(finalAttendanceList.get(position).getRollNumber());
            confirmAttenance.setText(finalAttendanceList.get(position).getAttendance());
            return view;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

    public void onBackPressed(){
        Intent backRedirect = new Intent(AttendanceScreenActivity.this,Main.class);
        backRedirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(backRedirect);
    }


    private class GetAttendanceExecutor extends AsyncTask<String,Void,List<StudentObject>>{

        MaterialDialog materialDialog;
        @Override
        protected void onPostExecute(List<StudentObject> studentObjects) {
            materialDialog.cancel();
            if (studentObjects==null || studentObjects.size()==0){
                NoDataText.setVisibility(View.VISIBLE);
                return;
            }
            rollListAdapter.changeTheList(studentObjects);
            rollListAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            materialDialog = new MaterialDialog.Builder(AttendanceScreenActivity.this)
                    .progress(true,0)
                    .cancelable(false)
                    .content("Getting Class List")
                    .show();
        }

        @Override
        protected List<StudentObject> doInBackground(String... params) {
            List<StudentObject> resultantList = new ArrayList<>();
            JSONObject jsonObject;
            JSONArray jsonArray = null;
            try{
                jsonObject = Util_Class.getJSONFromUrl(params[0]);
                jsonArray = jsonObject.getJSONArray("rollRecord");

                for(int element=0;element<jsonArray.length();element++){
                    resultantList.add(convertItemDetails(jsonArray.getJSONObject(element)));
                }
                return resultantList;

            }catch(Throwable j){
                j.printStackTrace();
            }
            return null;
        }
        }


    private StudentObject convertItemDetails(JSONObject jsonObject) throws JSONException{
        return new StudentObject(jsonObject.getString("rollno"),"0");
    }

    private class submitAttendanceExecutor extends AsyncTask<String,Void,Void>{

        MaterialDialog materialDialog;
        Boolean status;
        @Override
        protected void onPreExecute() {
            materialDialog = new MaterialDialog.Builder(AttendanceScreenActivity.this)
                    .progress(true,0)
                    .cancelable(false)
                    .content("Saving The Record To DataBase")
                    .show();
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            materialDialog.dismiss();
            if(status){
                new MaterialDialog.Builder(AttendanceScreenActivity.this)
                        .content("Your Attendance Is Saved To The DataBase")
                        .positiveText("OK")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                dialog.dismiss();
                                Intent LandingPageRedirect = new Intent(AttendanceScreenActivity.this,Main.class);
                                LandingPageRedirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(LandingPageRedirect);
                            }
                        })
                        .show();
            }
            else {
                new MaterialDialog.Builder(AttendanceScreenActivity.this)
                        .content("There Is Problem Connecting To Servers")
                        .positiveText("CANCEL")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        }
        @Override
        protected Void doInBackground(String... params) {
            status = SubmitAttendance(params[0]);
            return null;
        }
    }
    private Boolean SubmitAttendance(String url) {
        Boolean finalStatus = false;
        JSONObject jsonObject = Util_Class.getJSONFromUrl(url);
        try{
        finalStatus = jsonObject.getBoolean("status");
        }
        catch (Exception j){
            j.printStackTrace();
        }
        return finalStatus;
    }
}
