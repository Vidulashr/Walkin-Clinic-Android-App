package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ClinicViewer extends AppCompatActivity {
    Button searchButt;
    EditText searchByAddress;
    DatabaseSQLiteHelper db ;
    //Spinner Stuff
    Spinner dayStart,startHour,endHour,startAP,endAP,searchByService;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_viewer);
        db = new DatabaseSQLiteHelper(this);


        //All of my text entry stuff
        searchButt = findViewById(R.id.findClinics);
        searchByAddress = findViewById(R.id.clinicAddress_Search);
        searchByService = findViewById(R.id.clinicService_Search);

        //All my spinner stuff
        dayStart = findViewById(R.id.dayspin);
        startHour = findViewById(R.id.fromtime);
        endHour = findViewById(R.id.aftertime);
        startAP = findViewById(R.id.fromAP);
        endAP = findViewById(R.id.afterAP);

        //Transfered Data
        final String getData = getIntent().getStringExtra("currUser");
        final int patientID = getIntent().getIntExtra("patientID",-1);
        //Fetch the userID
        final List<String> day = new ArrayList<String>();
        day.add("Monday");
        day.add("Tuesday");
        day.add("Wednesday");
        day.add("Thursday");
        day.add("Friday");
        day.add("Saturday");
        day.add("Sunday");
        day.add("DAY");



        //My Hints
        List<String> AP = new ArrayList<String>();
        AP.add("AM");
        AP.add("PM");
        AP.add(" ");

        //My Hints for Spinner
        List<String> hours = new ArrayList<String>();
        hours.add("1");
        hours.add("2");
        hours.add("3");
        hours.add("4");
        hours.add("5");
        hours.add("6");
        hours.add("7");
        hours.add("8");
        hours.add("9");
        hours.add("10");
        hours.add("11");
        hours.add("12");
        hours.add("H");


        List<String> servicenames = new ArrayList<String>();
        ArrayList<Service> services= db.getAllServices();

        System.out.println(services.size());

        if (services.size()!=0){
            for (int i = 0;i<services.size();i++){
                servicenames.add(services.get(i).getName());
            }
            servicenames.add("Search By Service");}
        else{
            servicenames.add("No Services Currently Available");
            servicenames.add("Search By Service");
        }



        HintAdapter hintadapterservice = new HintAdapter( this, servicenames, android.R.layout.simple_spinner_item);
        hintadapterservice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        HintAdapter hintadapter = new HintAdapter( this, AP, android.R.layout.simple_spinner_item);
        hintadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        HintAdapter hintadapterhour = new HintAdapter( this, hours, android.R.layout.simple_spinner_item);
        hintadapterhour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        HintAdapter hintadapterday = new HintAdapter( this, day, android.R.layout.simple_spinner_item);
        hintadapterday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        searchByService.setAdapter(hintadapterservice);
        searchByService.setSelection(hintadapterservice.getCount());

        startAP.setAdapter(hintadapter);
        startAP.setSelection(hintadapter.getCount());

        endAP.setAdapter(hintadapter);
        endAP.setSelection(hintadapter.getCount());

        startHour.setAdapter(hintadapterhour);
        startHour.setSelection(hintadapterhour.getCount());

        dayStart.setAdapter(hintadapterday);
        dayStart.setSelection(hintadapterday.getCount());

        endHour.setAdapter(hintadapterhour);
        endHour.setSelection(hintadapterhour.getCount());

        searchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //If the button has been clicked, retrieved the data entered
                String enteredAddres = searchByAddress.getText().toString();
                String enteredService = searchByService.getSelectedItem().toString();
                String begAP = startAP.getSelectedItem().toString();
                String endingAP = endAP.getSelectedItem().toString();
                String firstHour = startHour.getSelectedItem().toString();
                String secondHour = endHour.getSelectedItem().toString();
                String daySpec = dayStart.getSelectedItem().toString();

                String datatoTransfer = daySpec+"-"+firstHour+"-"+begAP+"-"+secondHour+"-"+endingAP+'-';


                if(enteredAddres.isEmpty()){
                    if(enteredService.equals("Search By Service")||enteredService.equals("No Services Currently Available")){
                        //Transfer hour data
                        if(validateHour(firstHour,"00",begAP,secondHour,"00",endingAP)){
                            MoveToClinicView(datatoTransfer,0,patientID);
                        }
                        else{
                            toastMessage("Invalid Hours Entered");
                        }
                    }
                    else{
                        //transfer service data
                        MoveToClinicView(enteredService,1,patientID);
                    }
                }
                else{
                    //Transfer address Data
                    MoveToClinicView(enteredAddres,2,patientID);
                }

}

    });}



    public boolean validateHour(String h1, String m1,String ap, String h2,String m2, String ap2){
        Integer hour1 = Integer.valueOf(h1);
        Integer hour2 = Integer.valueOf(h2);
        Integer min1 = Integer.valueOf(m1);
        Integer min2 = Integer.valueOf(m2);

        if (ap.equals(ap2)){ //if am and am or pm and pm
            if (hour1>hour2){ //if hour before is greater than hour after
                return false;
            }

            else if (hour1.equals(hour2)){
                if (min1>min2){ //if min before is greater than min after
                    return false;
                }
                else if (min1.equals(min2)){
                    return false;
                }
                else{
                    return true;
                }
            }
            else{
                return true;
            }
        }
        else if (ap.equals("PM")&&ap2.equals("AM")){
            return false;
        }
        else{
            return true;
        }
    }


    public void toastMessage(String x){
        Toast.makeText(this,x, Toast.LENGTH_SHORT).show();
    }


    private void MoveToClinicView(String x,int y,int z){
            Intent newScreen = new Intent(this,DisplayClinic.class);
            newScreen.putExtra("inputedVal",x);
            newScreen.putExtra("type",y);
            newScreen.putExtra("patientID",z);
            startActivity(newScreen); }






}
