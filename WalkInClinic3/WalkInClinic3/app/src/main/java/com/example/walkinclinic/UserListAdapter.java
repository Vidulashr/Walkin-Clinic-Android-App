package com.example.walkinclinic;

import java.util.ArrayList;
import android.content.Context;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserListAdapter extends ArrayAdapter<User> {
    private static final String TAG = "ServiceListAdapter";
    private Context mContext;
    int mResource;

    public UserListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> objects, Context mContext) {
        super(context, resource, objects);
        this.mContext = mContext;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String fname = getItem(position).getFirstName();
        String lname = getItem(position).getLastName();
        String uname = getItem(position).getUsername();
        String phone = getItem(position).getPhoneNumber();
        String address = getItem(position).getAddress();
        String email = getItem(position).getEmail();
        String type = getItem(position).getRole();
        int userid = getItem(position).getID();

        System.out.println(type);

        if (type.equals("Patient")){
            Patient puser = new Patient(uname,fname,lname,phone,address,email,userid);}
        else{
            Employee euser = new Employee(uname,fname,lname,phone,address,email,userid);
        }
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);
        TextView tvuName= (TextView) convertView.findViewById(R.id.listusername);
        TextView tvfname= (TextView) convertView.findViewById(R.id.listfirstname);
        TextView tvlname= (TextView) convertView.findViewById(R.id.listlastname);
        TextView tvemail = (TextView) convertView.findViewById(R.id.listemail);
        TextView tvtype = (TextView) convertView.findViewById(R.id.listype);

        tvuName.setText(uname);
        tvfname.setText(fname);
        tvlname.setText(lname);
        tvemail.setText(email);
        tvtype.setText(type);
        return convertView;

    }

}
