package com.example.walkinclinic;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ServiceEditer extends AppCompatActivity {
    DatabaseSQLiteHelper db;
    ListView service_editor;
    Button servicefind,serviceadd,servicedelete;
    EditText servicename,servicerate;
    Spinner servicerole;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_editor_employee);
        service_editor = (ListView)findViewById(R.id.servicelistemployeelistview);
        db =new DatabaseSQLiteHelper(this);
        servicefind = (Button)findViewById(R.id.servicefind_employee);
        servicename = (EditText)findViewById(R.id.servicename_employee);
        servicerole = (Spinner)findViewById(R.id.servicerole_employee);
        servicerate = (EditText)findViewById(R.id.servicerate_employee);
        serviceadd = (Button)findViewById(R.id.btn_addtoyourservices);
        servicedelete = (Button)findViewById(R.id.btn_deleteServiceClinic);
        final String sessionUser = Integer.toString(getIntent().getIntExtra("empID",-1));
        final int SessUser = getIntent().getIntExtra("empID",-1);

        List<String> roles = new ArrayList<String>();
        roles.add("Doctor");
        roles.add("Nurse");
        roles.add("Staff");
        roles.add("Modify Role...");

        HintAdapter hintadapter = new HintAdapter( this, roles, android.R.layout.simple_spinner_item);
        hintadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        servicerole.setAdapter(hintadapter);
        servicerole.setSelection(hintadapter.getCount());

        //Getting list of Services
        ArrayList<Service> list= db.getAllServices();

        ServiceListAdapter adapter = new ServiceListAdapter(this,R.layout.servicelist,list,this);
        service_editor.setAdapter(adapter);

        //NEW
        service_editor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int pos, long mylng) {
                Service selectedFromList =((Service) service_editor.getItemAtPosition(pos));
                String name = selectedFromList.getName();
                String role = selectedFromList.getRole();
                String rate = selectedFromList.getHourlyRate();
                servicename.setText(name);
                servicerate.setText(rate);
                if (role.equals("Doctor")){
                    servicerole.setSelection(0);}
                else if (role.equals("Nurse")){
                    servicerole.setSelection(1);}
                else if (role.equals("Staff")){
                    servicerole.setSelection(2);}
                else{
                    servicerole.setSelection(3);}
                }
        });


        servicefind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Service service = db.findService(servicename.getText().toString());

                if (service != null) {
                    String role = String.valueOf(service.getRole());
                    if (role.equals("Doctor")){
                        servicerole.setSelection(0);}
                    else if (role.equals("Nurse")){
                        servicerole.setSelection(1);}
                    else if (role.equals("Staff")){
                        servicerole.setSelection(2);}
                    else{
                        servicerole.setSelection(3);}
                    servicerate.setText(String.valueOf(service.getHourlyRate()));
                } else {
                    toastMessage("Service Not Found");
                }

            }
        });

        serviceadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(servicename.getText());
                String role = servicerole.getSelectedItem().toString();
                String rate = String.valueOf(servicerate.getText());

                //if fields are not filled up
                if (name.isEmpty()||role.isEmpty()||rate.isEmpty()){
                    toastMessage("Fill in fields!");
                }

                else {
                    //service needs to be available and provided by admin
                    if (db.findService(name) == null) {
                        toastMessage("Service is not Available");
                    }

                    //service is provided by admin
                    else {
                        String serviceID = db.getCurrentServiceID(name);
                        String clinicID = db.findClinicID(SessUser);


                        System.out.println(clinicID);
                        System.out.println(serviceID);
                        System.out.println(name);
                        System.out.println(role);
                        System.out.println(rate);


                        if (clinicID == null) {
                            toastMessage("You have not completed your CLINIC profile.");
                        }
                        else {
                            String relationID = db.getRelationID(clinicID,serviceID);
                            if (db.validateClinicService(relationID)) {
                                toastMessage("You already have this service!");
                            }
                            else {
                                Boolean result = db.insertNewRelation(clinicID, serviceID, name, role, rate);

                                if (result) {
                                    toastMessage("Service added to your clinic");
                                }
                                else {
                                    toastMessage("Error in adding service. Try again!");
                                }
                            }
                        }
                    }
                }


            }
        });

        servicedelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(servicename.getText());
                String role = servicerole.getSelectedItem().toString();;
                String rate = String.valueOf(servicerate.getText());

                if (name.isEmpty()||role.isEmpty()||rate.isEmpty()){
                    toastMessage("Indicate which Service!");
                }
                else {
                    String serviceID = db.getCurrentServiceID(name);
                    String clinicID = db.findClinicID(SessUser);

                    if (clinicID == null){
                        toastMessage("You have not completed your CLINIC profile.");
                    }
                    else {
                        String relationID = db.getRelationID(clinicID,serviceID);
                        Boolean result = db.deleteRelation(relationID);

                        if (result) {
                            toastMessage("Service removed from your clinic");
                        } else {
                            toastMessage("Error in deleting service. Try again!");
                        }
                    }
                }
            }
        });


    }


    //VALIDITY
    //Makes sure rate is a integer or boolean.
    public boolean validRate(String rate){
        try{
            Double.parseDouble(rate);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    //Makes sure role is either doctor,nurse or staff and nothing else.
    public boolean validRole(String role){
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

