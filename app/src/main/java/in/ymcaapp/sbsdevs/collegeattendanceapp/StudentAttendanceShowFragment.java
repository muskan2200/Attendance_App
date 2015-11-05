package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhey singh on 04-11-2015.
 */
public class StudentAttendanceShowFragment extends Fragment {

    private ListView StudentAttendanceList;
    private AttendanceListAdapter attendanceListAdapter;
    private StudentSharedPref studentSharedPref;
    private TextView NoDataText;

    public StudentAttendanceShowFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.studentattendanceshowfragment,container,false);
        getActivity().setTitle("My Attendance");
        studentSharedPref= new StudentSharedPref(getActivity());
        StudentAttendanceList = (ListView)v.findViewById(R.id.StudentAttendanceShowListView);
        NoDataText = (TextView)v.findViewById(R.id.noDataText);
        NoDataText.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        NoDataText.setText("NO WORKING INTERNET");
        new GetAttendanceRecordExecutor().execute(Constants.BaseGetStudentList+studentSharedPref.getStudentRoll());
        attendanceListAdapter = new AttendanceListAdapter(getActivity(),new ArrayList<AttendanceShowObject>());
        StudentAttendanceList.setAdapter(attendanceListAdapter);
        return v;
    }




    private class GetAttendanceRecordExecutor extends AsyncTask<String,Void,List<AttendanceShowObject>> {

        MaterialDialog materialDialog;
        @Override
        protected void onPostExecute(List<AttendanceShowObject> MyAttendanceList) {
            materialDialog.cancel();
            if (MyAttendanceList==null || MyAttendanceList.size()==0){
                NoDataText.setVisibility(View.VISIBLE);
                return;
            }
            attendanceListAdapter.changeTheList(MyAttendanceList);
            attendanceListAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            materialDialog = new MaterialDialog.Builder(getActivity())
                    .progress(true,0)
                    .cancelable(false)
                    .content("Getting Attendance List")
                    .show();
        }

        @Override
        protected List<AttendanceShowObject> doInBackground(String... params) {
            List<AttendanceShowObject> resultantList = new ArrayList<>();
            JSONObject jsonObject;
            JSONArray jsonArray = null;
            try{
                jsonObject = Util_Class.getJSONFromUrl(params[0]);
                jsonArray = jsonObject.getJSONArray("attendance");

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

    private AttendanceShowObject convertItemDetails(JSONObject jsonObject) throws JSONException {
        return new AttendanceShowObject(jsonObject.getString("sub"),jsonObject.getString("value"));
    }





    public static class AttendanceShowObject {
        public String SubjectName;
        public String AttendanceValue;

        public AttendanceShowObject(String subjectName, String attendanceValue) {
            this.SubjectName = subjectName;
            this.AttendanceValue = attendanceValue;
        }

        public String getSubjectName() {
            return SubjectName;
        }

        public void setSubjectName(String subjectName) {
            this.SubjectName = subjectName;
        }

        public String getAttendanceValue() {
            return AttendanceValue;
        }

        public void setAttendanceValue(String attendanceValue) {
            this.AttendanceValue = attendanceValue;
        }
    }



}
