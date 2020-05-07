package com.example.walkinclinic;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ServiceEditer extends AppCompatActivity {
    DatabaseSQLiteHelper db;
    ListView service_editor;
    Button servicefind,serviceadd;
    EditText servicename,servicerate,servicerole;


    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_editor_employee);
        service_editor = (ListView)findViewById(R.id.servicelistemployeelistview);
        db =new DatabaseSQLiteHelper(this);
        servicefind = (Button)findViewById(R.id.servicefind_employee);
        servicename = (EditText)findViewById(R.id.servicename_employee);
        servicerole = (EditText)findViewById(R.id.servicerole_employee);
        servicerate = (EditText)findViewById(R.id.servicerate_employee);
        serviceadd = (Button)findViewById(R.id.btn_addtoyourservices);


        //Getting list of Services
        ArrayList<Service> list= db.getAllServices();

        ServiceListAdapter adapter = new ServiceListAdapter(this,R.layout.servicelist,list,this);
        service_editor.setAdapter(adapter);


        servicefind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Service service = db.findService(servicename.getText().toString());

                if (service != null) {
                    servicerole.setText(String.valueOf(service.getRole()));
                    servicerate.setText(String.valueOf(service.getHourlyRate()));
                } else {
                    toastMessage("Service Not Found");
                }

            }
        });




    }


    //VALIDITY
    //Makes sure rate is a integer or boolean.
    private boolean validRate(String rate){
        try{
            Double.parseDouble(rate);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    //Makes sure role is either doctor,nurse or staff and nothing else.
    private boolean validRole(String role){
        if (role.equals("Doctor")||role.equals("doctor")||
                role.equals("staff")||role.equals("Staff")||role.equals("nurse")||role.equals("Nurse")){
            return true;
        }
        else {
            return false;
        }
    }




    public void toastMessage(String x){
        Toast.makeText(this,x, Toast.LENGTH_SHORT).show();
    }}

