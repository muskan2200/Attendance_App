package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abhey singh on 05-11-2015.
 */
public class AttendanceListAdapter extends BaseAdapter {
    private List<StudentAttendanceShowFragment.AttendanceShowObject> AttendanceList;
    private LayoutInflater minflater;
    private Context context;
    public AttendanceListAdapter(Context context,List<StudentAttendanceShowFragment.AttendanceShowObject> AttendanceList) {
        this.AttendanceList = AttendanceList;
        this.context=context;
        this.minflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return AttendanceList.size();
    }

    @Override
    public Object getItem(int position) {
        return AttendanceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = minflater.inflate(R.layout.studentattendanceshowcustom,parent,false);

        TextView SubjectName = (TextView)v.findViewById(R.id.subjectName);
        TextView Attendance = (TextView)v.findViewById(R.id.noofattendance);
        Attendance.setTypeface(Util_Class.setNewTextStyle(context));
        SubjectName.setTypeface(Util_Class.setNewTextStyle(context));
        SubjectName.setText("" + AttendanceList.get(position).getSubjectName());
        Attendance.setText(""+AttendanceList.get(position).getAttendanceValue());
        return v;
    }

    public  void changeTheList(List<StudentAttendanceShowFragment.AttendanceShowObject> AttendanceList){
        this.AttendanceList=AttendanceList;
    }
}
