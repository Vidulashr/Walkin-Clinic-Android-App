package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ServiceForm extends AppCompatActivity{
    //Goal of this class: Take all info on service, stores it if its all valid.
    //Once info is stores, returns back to admin menu.

    //All editable variables
    EditText service_name,service_rate;
    Spinner service_role;
    TextView service_ID;
    ListView service_List;
    Button submit_service,find_service,delete_service,update_service;
    DatabaseSQLiteHelper db;



    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_form);
        Log.d("ServiceForm", "onCreate: Started");

        service_name =(EditText) findViewById(R.id.serviceName);
        service_role =(Spinner) findViewById(R.id.serviceRole);
        service_rate = (EditText)findViewById(R.id.listserviceRate);
        service_ID = (TextView)findViewById(R.id.service_IDvalue);
        service_List = (ListView) findViewById(R.id.viewingServices);
        submit_service = (Button)findViewById(R.id.btn_serviceSubmit);
        find_service = (Button)findViewById(R.id.btn_serviceFind);
        delete_service = (Button)findViewById(R.id.btn_serviceDelete);
        update_service = (Button)findViewById(R.id.btn_UpdateService);

        db = new DatabaseSQLiteHelper(this);

        List<String> roles = new ArrayList<String>();
        roles.add("Doctor");
        roles.add("Nurse");
        roles.add("Staff");
        roles.add("Default Role...");

        HintAdapter hintadapter = new HintAdapter( this, roles, android.R.layout.simple_spinner_item);
        hintadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        service_role.setAdapter(hintadapter);
        service_role.setSelection(hintadapter.getCount());

        //Getting list of Services
        ArrayList<Service> list= db.getAllServices();

        //Testing whether list is there
        for (int i = 0;i<list.size();i++){
            System.out.println("SERVICE");
            System.out.println(list.get(i).getName()+list.get(i).getRole()+list.get(i).getHourlyRate());
        }

        ServiceListAdapter adapter = new ServiceListAdapter(this,R.layout.servicelist,list,this);
        service_List.setAdapter(adapter);


        //NEW
        service_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int pos, long mylng) {
                Service selectedFromList =((Service) service_List.getItemAtPosition(pos));
                String name = selectedFromList.getName();
                String role = selectedFromList.getRole();
                String rate = selectedFromList.getHourlyRate();
                String id = selectedFromList.getServiceID();
                service_name.setText(name);
                service_rate.setText(rate);
                if (role.equals("Doctor")){
                    service_role.setSelection(0);}
                else if (role.equals("Nurse")){
                    service_role.setSelection(1);}
                else if (role.equals("Staff")){
                    service_role.setSelection(2);}
                else{
                    service_role.setSelection(3);}
                service_ID.setText(id);
            }
        });




        //ADDING SERVICE
        submit_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = service_name.getText().toString();
                String role = service_role.getSelectedItem().toString();
                String rate = service_rate.getText().toString();

                //checking if fields are not empty
                if(name.isEmpty() || role.isEmpty() || rate.isEmpty()){
                    //One is empty, Toast an error message!
                    toastMessage("PLEASE FILL IN FIELDS!");
                }
                else{
                    //checking if name,role are strings and rate is an integer
                    if (validName(name)&&validRate(rate)&&validRole(role)){
                        //checking if duplicate service
                        if (db.checkAvbService(name)) {
                            //now add service
                            boolean added = db.insertService(name, role, rate);

                            //if successfully added
                            if (added) {
                                toastMessage("SERVICE ADDED");
                                service_name.setText("");
                                service_role.setSelection(3);
                                service_rate.setText("");
                                service_ID.setText("");
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);

                            }
                            //if any sort of error
                            else {
                                toastMessage("ERROR IN ADDING");
                            }
                        }
                        else{
                            toastMessage("SERVICE ALREADY EXISTS");
                        }
                    }
                    else {
                        toastMessage("INVALID ENTRY");
                    }
                }
            }


        });

        //FINDING SERVICE
        find_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Service service = db.findService(service_name.getText().toString());

                if (service != null) {
                    String role = String.valueOf(service.getRole());
                    if (role.equals("Doctor")){
                        service_role.setSelection(0);}
                    else if (role.equals("Nurse")){
                        service_role.setSelection(1);}
                    else if (role.equals("Staff")){
                        service_role.setSelection(2);}
                    else{
                        service_role.setSelection(3);}
                    service_rate.setText(String.valueOf(service.getHourlyRate()));
                    service_ID.setText(String.valueOf(service.getServiceID()));
                } else {
                    toastMessage("Service Not Found");
                }

            }
        });


        //DELETING SERVICE
        delete_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = db.deleteService(service_name.getText().toString());

                if (result) {
                    toastMessage("SERVICE DELETED");
                    service_name.setText("");
                    service_role.setSelection(3);
                    service_rate.setText("");
                    service_ID.setText("");
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);

                }
                else
                    toastMessage("No Match Found");
            }
        });

        //UPDATING SERVICE
        update_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = service_name.getText().toString();
                String role = service_role.getSelectedItem().toString();
                String rate = service_rate.getText().toString();

                //checking if fields are not empty
                if(name.isEmpty() || role.isEmpty() || rate.isEmpty()){
                    //One is empty, Toast an error message!
                    toastMessage("PLEASE FILL IN FIELDS!");
                }
                else{
                    //checking if name,role are strings and rate is an integer
                    if (validName(name)&&validRate(rate)&&validRole(role)){
                            //now add service
                            boolean added = db.updateService(name, role, rate);

                            //if successfully added
                            if (added) {
                                toastMessage("SERVICE UPDATED");
                                service_name.setText("");
                                service_role.setSelection(3);
                                service_rate.setText("");
                                service_ID.setText("");
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);


                            }
                            //if any sort of error
                            else {
                                toastMessage("ERROR IN UPDATING");
                            }
                        }
                    else {
                        toastMessage("INVALID ENTRY");
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
            int afterDecCounter = 0;
            boolean reachedDecimal = false;
            for (int i = 0 ; i < rate.length() ; i++ ){
                char currChar = rate.charAt(i);
                if(reachedDecimal){afterDecCounter = afterDecCounter + 1;}
                if(currChar == '.'){
                    reachedDecimal = true;
                }
            }

            if(afterDecCounter <= 2 ){ return true; }
            else{return false;}
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    //Makes sure role is either doctor,nurse or staff and nothing else.
    //Capitalize --> and just check one possible combination

    public boolean validRole(String role){
        role = role.toUpperCase();
        if (role.equals("DOCTOR") ||
                role.equals("STAFF")||role.equals("NURSE")){
            return true;
        }
        else {
            return false;
        }
    }

    //makes sure name is only characters and spaces
    private boolean validName(String name){
        boolean test = true;
        for (int i = 0 ; i < name.length() ; i++){
            char c = name.charAt(i);
            //I want to check if a that char is a letter
            if((Character.isLetter(c))||(Character.isSpaceChar(c))){
                test = test;
            }
            else {
                test = false;
            }
        }
        return test;
    }

    public void toastMessage(String x){
        Toast.makeText(this,x, Toast.LENGTH_SHORT).show();
    }
}



