package com.example.walkinclinic;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ServiceViewer extends AppCompatActivity {
    ListView service_list;
    DatabaseSQLiteHelper db;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_viewer_patient);
        service_list = (ListView)findViewById(R.id.servicevieverlistview);
        db = new DatabaseSQLiteHelper(this);

        //Getting list of Services
        ArrayList<Service> list= db.getAllServices();

        ServiceListAdapter adapter = new ServiceListAdapter(this,R.layout.servicelist,list,this);
        service_list.setAdapter(adapter);

    }
}
