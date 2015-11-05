package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abhey singh on 30-10-2015.
 */
public class TeacherRecordAdapter extends BaseAdapter {

    private Context context;
    private List<RecordObject> UserRecord;
    private LayoutInflater inflater;
    public TeacherRecordAdapter(Context context, List<RecordObject> UserRecord) {
        this.context=context;
        this.UserRecord=UserRecord;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return UserRecord.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.recordcustomitem,parent,false);
        RecordObject oneSubject = UserRecord.get(position);
        TextView DepartmentName = (TextView)v.findViewById(R.id.DepartmentValue);
        TextView SemesterName = (TextView)v.findViewById(R.id.SemesterValue);
        TextView SubjectName = (TextView)v.findViewById(R.id.SubjectValue);
        TextView DepartmentTile = (TextView)v.findViewById(R.id.DepartmentTile);
        TextView SemesterTile = (TextView)v.findViewById(R.id.SemesterTile);
        TextView SubjectTile = (TextView)v.findViewById(R.id.SubjectTile);

        DepartmentTile.setTypeface(Util_Class.setNewTextStyle(context));
        SemesterTile.setTypeface(Util_Class.setNewTextStyle(context));
        SubjectTile.setTypeface(Util_Class.setNewTextStyle(context));
        DepartmentName.setTypeface(Util_Class.setNewTextStyle(context));
        SemesterName.setTypeface(Util_Class.setNewTextStyle(context));
        SubjectName.setTypeface(Util_Class.setNewTextStyle(context));

        DepartmentName.setText(""+oneSubject.getDepartment());
        SemesterName.setText(""+oneSubject.getSemester());
        SubjectName.setText(""+oneSubject.getSubject());


        return v;
    }

    public List<RecordObject> getTheList(){
        return UserRecord;
    }

    public void changeTheList(List<RecordObject> UserRecord){
        this.UserRecord=UserRecord;

    }
}
