package com.example.walkinclinic;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ClinicServiceViewer extends AppCompatActivity {
    ListView service_list;
    DatabaseSQLiteHelper db;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_viewer_clinic);
        service_list = (ListView)findViewById(R.id.list_clinicservices);
        db = new DatabaseSQLiteHelper(this);
        final String sessionUser = String.valueOf(getIntent().getIntExtra("empID",-1));

        //Getting list of AServices
        ArrayList<Service> list= db.getAllServicesforClinic(db.findClinicID(Integer.valueOf(sessionUser)));


        System.out.println(list);

        ServiceListAdapter adapter = new ServiceListAdapter(this,R.layout.servicelist,list,this);
        service_list.setAdapter(adapter);

    }
}

