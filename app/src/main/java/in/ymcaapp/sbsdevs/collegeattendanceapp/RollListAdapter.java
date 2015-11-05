package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abhey singh on 30-10-2015.
 */
public class RollListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<StudentObject> attendanceList;
    public RollListAdapter(Context context,List<StudentObject> attendanceList) {
        this.context=context;
        this.attendanceList=attendanceList;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return attendanceList.size();
    }

    @Override
    public Object getItem(int position) {
        return attendanceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = new ViewHolder();


        StudentObject oneStudent = attendanceList.get(position);

        if (convertView==null){
            v = inflater.inflate(R.layout.studentrollcustomitem,parent,false);
            holder.AddAttendance = (Button)v.findViewById(R.id.addAttendance);
            holder.AttendanceGiven = (TextView)v.findViewById(R.id.attendCount);
            holder.RollNumber = (TextView)v.findViewById(R.id.studentRollNumber);
            holder.SubAttendance = (Button)v.findViewById(R.id.subAttendance);

            holder.AttendanceGiven.setTypeface(Util_Class.setNewTextStyle(context));
            holder.AddAttendance.setTypeface(Util_Class.setNewTextStyle(context));
            holder.RollNumber.setTypeface(Util_Class.setNewTextStyle(context));
            holder.SubAttendance.setTypeface(Util_Class.setNewTextStyle(context));
            v.setTag(holder);
        }

        else{
            holder = (ViewHolder)v.getTag();
        }

        holder.RollNumber.setText(""+oneStudent.getRollNumber());
        holder.AttendanceGiven.setText(""+oneStudent.getAttendance());

        final ViewHolder finalHolder = holder; //to access it for the on click of the add attendance button

        holder.AddAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendanceList.get(position).setAttendance(""+(Integer.parseInt(finalHolder.AttendanceGiven.getText().toString())+1));
                notifyDataSetChanged();
            }
        });

        holder.SubAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(finalHolder.AttendanceGiven.getText().toString())>0){
                attendanceList.get(position).setAttendance(""+(Integer.parseInt(finalHolder.AttendanceGiven.getText().toString())-1));
                notifyDataSetChanged();
                }
            }
        });


        return v;
    }

    public void changeTheList(List<StudentObject> attendanceList){
        this.attendanceList = attendanceList;
    }

    public List<StudentObject> getAttendanceList(){
        return attendanceList;
    }

    public static class ViewHolder{
        TextView RollNumber;
        TextView AttendanceGiven;
        Button AddAttendance;
        Button SubAttendance;
    }
}
