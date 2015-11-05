package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhey singh on 30-10-2015.
 */
public class UserClassesInfo extends Fragment {

    private ListView TeacherRecordList;
    public TeacherRecordAdapter teacherRecordAdapter;
    public ViewFlipper viewFlipper;
    private SharedPrefUtil sharedPrefUtil;
    private View noNetworkView;
    public ImageView noInternetImage;
    private TextView NoDataText;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Main.MaterialToolBar.setNavigationIcon(R.mipmap.ic_launcher);
        sharedPrefUtil = new SharedPrefUtil(getActivity());
        noNetworkView = View.inflate(getActivity(),R.layout.networkerrorlayout,null);
         noInternetImage = (ImageView)noNetworkView.findViewById(R.id.noInternetImages);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.userclassesinfofragment, container, false);
        getActivity().setTitle("Your Classes");
        TeacherRecordList = (ListView)v.findViewById(R.id.userClassesListView);
        teacherRecordAdapter = new TeacherRecordAdapter(getActivity(),new ArrayList<RecordObject>());
        NoDataText = (TextView)v.findViewById(R.id.NODATATEXT);
        NoDataText.setTypeface(Util_Class.setNewTextStyle(getActivity()));
        NoDataText.setText("NO DATA AVAILABLE");
        TeacherRecordList.setAdapter(teacherRecordAdapter);
        new getTeacherDataJson().execute();
        viewFlipper = (ViewFlipper)v.findViewById(R.id.viewFlipper);
        viewFlipper.setInAnimation(getActivity(),R.anim.right_in);
        viewFlipper.setOutAnimation(getActivity(),R.anim.right_out);
        TeacherRecordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent takeAttendance = new Intent(getActivity(), AttendanceScreenActivity.class);
                takeAttendance.putExtra(Constants.Department,teacherRecordAdapter.getTheList().get(position).getDepartment());
                takeAttendance.putExtra(Constants.Semester,teacherRecordAdapter.getTheList().get(position).getSemester());
                takeAttendance.putExtra(Constants.Subject,teacherRecordAdapter.getTheList().get(position).getSubject());
                getActivity().overridePendingTransition(R.anim.right_in,R.anim.right_out);
                startActivity(takeAttendance);
            }
        });

        return v;
    }

    public class getTeacherDataJson extends AsyncTask<String,Void,List<RecordObject>>{

        MaterialDialog  progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = new  MaterialDialog.Builder(getActivity())
                    .progress(true,0)
                    .cancelable(false)
                    .content("Connecting To The Server")
                    .contentGravity(GravityEnum.CENTER)
                    .show();
        }
        @Override
        protected void onPostExecute(List<RecordObject> TeachersDataList) {
            progressDialog.cancel();
            if(TeachersDataList==null || TeachersDataList.size()==0){
               NoDataText.setVisibility(View.VISIBLE);
                return;
            }
            viewFlipper.setVisibility(View.VISIBLE);
            teacherRecordAdapter.changeTheList(TeachersDataList);
            teacherRecordAdapter.notifyDataSetChanged();
        }

        @Override
        protected List<RecordObject> doInBackground(String... params) {
            List<RecordObject> resultantList = new ArrayList<>();
            JSONObject jsonObject;
            JSONArray jsonArray = null;
            String completeUrl = Constants.BaseGetClassesUrl+"email="+sharedPrefUtil.getUserEmail();
            completeUrl.replace(" ","%20");
            try{
                jsonObject = Util_Class.getJSONFromUrl(completeUrl);
                jsonArray = jsonObject.getJSONArray("classRecord");

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

    public RecordObject convertItemDetails(JSONObject oneElement ) throws JSONException{
        String Semester = oneElement.getString("semester");
        String Subject = oneElement.getString("subject");
        String Department = oneElement.getString("department");
        return new RecordObject(Department,Semester,Subject);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id==android.R.id.home){
            Intent backRedirect = new Intent(getActivity(),Main.class);
            getActivity().overridePendingTransition(R.anim.right_in,R.anim.right_out);
            backRedirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(backRedirect);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
