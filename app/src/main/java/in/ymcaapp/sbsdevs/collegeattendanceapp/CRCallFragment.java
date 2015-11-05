package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by abhey singh on 01-11-2015.
 */
public class CRCallFragment extends Fragment {

    private ListView CrNumberList;

    public CRCallFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Main.MaterialToolBar.setNavigationIcon(R.mipmap.ic_launcher);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contactrepresentativefragment, container, false);
        getActivity().setTitle("Cr Numbers");
        CrNumberList = (ListView)v.findViewById(R.id.CRNumbersList);
        CrNumberList.setAdapter(new CrListAdapter(Constants.getSampleCrRecord()));
        return v;
    }

    public class CrListAdapter extends BaseAdapter{

        private List<CrDataObject> CrNumberList;
        private LayoutInflater mInflater;
        public CrListAdapter(List<CrDataObject> CrNumberList) {
            this.CrNumberList = CrNumberList;
            this.mInflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return CrNumberList.size();
        }

        @Override
        public Object getItem(int position) {
            return CrNumberList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = mInflater.inflate(R.layout.representativecustom,parent,false);

            TextView NameOfCr = (TextView)view.findViewById(R.id.nameOfCr);
            TextView DepartmentOfCr = (TextView)view.findViewById(R.id.departmentOfCr);
            TextView SemesterOfCr = (TextView)view.findViewById(R.id.semesterOfCr);
            TextView PhoneOfCr = (TextView)view.findViewById(R.id.PgoneOfCr);
            Button CallToCr = (Button)view.findViewById(R.id.callCR);
            NameOfCr.setTypeface(Util_Class.setNewTextStyle(getActivity()));
            DepartmentOfCr.setTypeface(Util_Class.setNewTextStyle(getActivity()));
            SemesterOfCr.setTypeface(Util_Class.setNewTextStyle(getActivity()));
            PhoneOfCr.setTypeface(Util_Class.setNewTextStyle(getActivity()));
            CallToCr.setTypeface(Util_Class.setNewTextStyle(getActivity()));

            NameOfCr.setText(CrNumberList.get(position).getName());
            DepartmentOfCr.setText(CrNumberList.get(position).getDepartment());
            SemesterOfCr.setText(CrNumberList.get(position).getSemester());
            PhoneOfCr.setText(CrNumberList.get(position).getPhoneNumber());

            CallToCr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String PhoneNumber = "tel:"+CrNumberList.get(position).getPhoneNumber();
                    Intent CallToCr = new Intent(Intent.ACTION_CALL, Uri.parse(PhoneNumber));
                    startActivity(CallToCr);
                }
            });

            return view;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id==android.R.id.home){
            Intent backRedirect = new Intent(getActivity(),Main.class);
            backRedirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(backRedirect);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
