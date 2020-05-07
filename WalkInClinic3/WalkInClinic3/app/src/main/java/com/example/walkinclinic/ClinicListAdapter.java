package com.example.walkinclinic;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ClinicListAdapter extends ArrayAdapter<ArrayList<String>> {
    private static final String TAG = "ClinicListAdapter";
    private Context mContext;
    int mResource;

    public ClinicListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ArrayList<String>> objects, Context mContext) {
        super(context, resource, objects);
        this.mContext = mContext;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = getItem(position).get(0);
        String phone = getItem(position).get(1);
        String address = getItem(position).get(2);
        String waitTime = getItem(position).get(3);
        String rating = getItem(position).get(4);
        if(rating == "-1"){rating="Be The First!";}

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);
        TextView tvName= (TextView) convertView.findViewById(R.id.listviewclinicName);
        TextView tvPhone= (TextView) convertView.findViewById(R.id.listviewclinicPhone);
        TextView tvAddress= (TextView) convertView.findViewById(R.id.listviewclinicAddress);
        TextView tvRating= (TextView) convertView.findViewById(R.id.listviewclinicRating);
        TextView tvWait= (TextView) convertView.findViewById(R.id.listviewclinicWait);
        RatingBar tvBar = (RatingBar) convertView.findViewById(R.id.listviewclinicRating2);

        tvName.setText(name);
        tvPhone.setText(phone);
        tvAddress.setText(address);
        tvRating.setText(rating);
        tvWait.setText(waitTime +" min");
        if(rating=="Be The First!") {
            tvBar.setRating(0);
        }

        else{
            tvBar.setRating(Float.valueOf(rating));
        }


        return convertView;

    }



}
