package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhey singh on 04-11-2015.
 */
public class TeacherCallFragment extends Fragment {

    private ListView TeacherNumberList;

    public TeacherCallFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contactteacherfragment,container,false);

        getActivity().setTitle("Teacher Numbers");
        TeacherNumberList = (ListView)v.findViewById(R.id.TeacherNumbersList);
        TeacherNumberList.setAdapter(new TeachersListAdapter(Constants.getSampleRecord()));
        return v;
    }


    public class TeachersListAdapter extends BaseAdapter {

        private List<TeacherDataObject> TeachersNumberList;
        private LayoutInflater mInflater;
        public TeachersListAdapter(List<TeacherDataObject> TeachersNumberList) {
            this.TeachersNumberList = TeachersNumberList;
            this.mInflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return  TeachersNumberList.size();
        }

        @Override
        public Object getItem(int position) {
            return TeachersNumberList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = mInflater.inflate(R.layout.teachercallcustom,parent,false);

            TextView NameOfTeacher = (TextView)view.findViewById(R.id.nameOfTeacher);
            TextView DepartmentOfTeacher = (TextView)view.findViewById(R.id.departmentOfTeacher);
            TextView PhoneOfTeacher = (TextView)view.findViewById(R.id.PgoneOfTeacher);
            TextView PositionOfTeacher = (TextView)view.findViewById(R.id.typeOfTeacher);
            Button CallToTeacher = (Button)view.findViewById(R.id.callTeacher);

            NameOfTeacher.setTypeface(Util_Class.setNewTextStyle(getActivity()));
            DepartmentOfTeacher.setTypeface(Util_Class.setNewTextStyle(getActivity()));
            PhoneOfTeacher.setTypeface(Util_Class.setNewTextStyle(getActivity()));
            PositionOfTeacher.setTypeface(Util_Class.setNewTextStyle(getActivity()));
            CallToTeacher.setTypeface(Util_Class.setNewTextStyle(getActivity()));
            NameOfTeacher.setText(TeachersNumberList.get(position).getName());
            DepartmentOfTeacher.setText(TeachersNumberList.get(position).getDepartment());
            PhoneOfTeacher.setText(TeachersNumberList.get(position).getPhone());
            PositionOfTeacher.setText(TeachersNumberList.get(position).getPosition());

            CallToTeacher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String PhoneNumber = "tel:"+TeachersNumberList.get(position).getPhone();
                    Intent CallToCr = new Intent(Intent.ACTION_CALL, Uri.parse(PhoneNumber));
                    startActivity(CallToCr);
                }
            });
            return view;
        }
    }
}
