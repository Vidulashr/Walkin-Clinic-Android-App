package com.example.walkinclinic;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ServiceListAdapter extends ArrayAdapter<Service> {
    private static final String TAG = "ServiceListAdapter";
    private Context mContext;
    int mResource;

    public ServiceListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Service> objects, Context mContext) {
        super(context, resource, objects);
        this.mContext = mContext;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        String role = getItem(position).getRole();
        String rate = getItem(position).getHourlyRate();
        String ID = getItem(position).getServiceID();

        Service service = new Service(name,role,rate,ID);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);
        TextView tvName= (TextView) convertView.findViewById(R.id.listservicename);
        TextView tvRole= (TextView) convertView.findViewById(R.id.listserviceROLE);
        TextView tvRate= (TextView) convertView.findViewById(R.id.listserviceRate);

        tvName.setText(name);
        tvRole.setText(role);
        tvRate.setText(rate);
        return convertView;

    }
}
