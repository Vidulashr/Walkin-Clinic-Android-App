package com.example.walkinclinic;

import android.os.Bundle;
import android.se.omapi.SEService;
import android.widget.Button;
import android.widget.Spinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class ClinicHours extends AppCompatActivity{
    DatabaseSQLiteHelper db;
    Switch monswi,tueswi,wedswi,thurswi,friswi,satswi,sunswi;
    Spinner mondayAP,tuesdayAP,wedAP,thurAP,friAP,satAP,sunAP;
    Spinner mondayAP2,tuesdayAP2,wedAP2,thurAP2,friAP2,satAP2,sunAP2;
    Spinner mondayh1,tuesdayh1,wedh1,thurh1,frih1,sath1,sunh1;
    Spinner mondayh2,tuesdayh2,wedh2,thurh2,frih2,sath2,sunh2;
    Spinner mondaym1,tuesdaym1,wedm1,thurm1,frim1,satm1,sunm1;
    Spinner mondaym2,tuesdaym2,wedm2,thurm2,frim2,satm2,sunm2;
    TextView mon,tue,wed,thur,fri,sat,sun;
    Button submithours;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hours);
        db = new DatabaseSQLiteHelper(this);


        monswi = (Switch)findViewById(R.id.monday_switch);
        tueswi = (Switch)findViewById(R.id.tuesday_switch);
        wedswi = (Switch)findViewById(R.id.wednesday_switch);
        thurswi = (Switch)findViewById(R.id.thursday_switch);
        friswi = (Switch)findViewById(R.id.friday_switch);
        satswi = (Switch)findViewById(R.id.saturday_switch);
        sunswi = (Switch)findViewById(R.id.sunday_switch);

        mon = (TextView)findViewById(R.id.mondayhours);
        tue = (TextView)findViewById(R.id.tuesdayhours);
        wed = (TextView)findViewById(R.id.wednesdayhours);
        thur = (TextView)findViewById(R.id.thursdayhours);
        fri = (TextView)findViewById(R.id.fridayhours);
        sat = (TextView)findViewById(R.id.saturdayhours);
        sun = (TextView)findViewById(R.id.sundayhours);


        submithours = (Button)findViewById(R.id.btn_hoursSubmit);
        mondayAP = (Spinner)findViewById(R.id.mondayAPspinner);
        tuesdayAP = (Spinner)findViewById(R.id.tuesdayAPspinner);
        wedAP = (Spinner)findViewById(R.id.wednesdayAPspinner);
        thurAP = (Spinner)findViewById(R.id.thursdayAPspinner);
        friAP = (Spinner)findViewById(R.id.fridayAPspinner);
        satAP = (Spinner)findViewById(R.id.saturdayAPspinner);
        sunAP = (Spinner)findViewById(R.id.sundayAPspinner);

        mondayh1 = (Spinner)findViewById(R.id.mondayhourbefore);
        mondaym1 = (Spinner)findViewById(R.id.mondayminbefore);
        mondayAP2 = (Spinner)findViewById(R.id.mondayAPspinner2);
        mondayh2 = (Spinner)findViewById(R.id.mondayhourafter);
        mondaym2 = (Spinner)findViewById(R.id.mondayminafter);

        tuesdayh1 = (Spinner)findViewById(R.id.tuesdayhourbefore);
        tuesdaym1 = (Spinner)findViewById(R.id.tuesdayminbefore);
        tuesdayAP2 = (Spinner)findViewById(R.id.tuesdayAPspinner2);
        tuesdayh2 = (Spinner)findViewById(R.id.tuesdayhourafter);
        tuesdaym2 = (Spinner)findViewById(R.id.tuesdayminafter);

        wedh1 = (Spinner)findViewById(R.id.wedhourbefore);
        wedm1 = (Spinner)findViewById(R.id.wedminbefore);
        wedAP2 = (Spinner)findViewById(R.id.wednesdayAPspinner2);
        wedh2 = (Spinner)findViewById(R.id.wedhourafter);
        wedm2 = (Spinner)findViewById(R.id.wedminafter);

        thurh1 = (Spinner)findViewById(R.id.thurhourbefore);
        thurm1 = (Spinner)findViewById(R.id.thurminbefore);
        thurAP2 = (Spinner)findViewById(R.id.thursdayAPspinner2);
        thurh2 = (Spinner)findViewById(R.id.thurhourafter);
        thurm2 = (Spinner)findViewById(R.id.thurminafter);

        frih1 = (Spinner)findViewById(R.id.frihourbefore);
        frim1 = (Spinner)findViewById(R.id.friminbefore);
        friAP2 = (Spinner)findViewById(R.id.fridayAPspinner2);
        frih2 = (Spinner)findViewById(R.id.frihourafter);
        frim2 = (Spinner)findViewById(R.id.friminafter);

        sath1 = (Spinner)findViewById(R.id.sathourbefore);
        satm1 = (Spinner)findViewById(R.id.satminbefore);
        satAP2 = (Spinner)findViewById(R.id.saturdayAPspinner2);
        sath2 = (Spinner)findViewById(R.id.sathourafter);
        satm2 = (Spinner)findViewById(R.id.satminafter);

        sunh1 = (Spinner)findViewById(R.id.sunhourbefore);
        sunm1 = (Spinner)findViewById(R.id.sunminbefore);
        sunAP2 = (Spinner)findViewById(R.id.sundayAPspinner2);
        sunh2 = (Spinner)findViewById(R.id.sunhourafter);
        sunm2 = (Spinner)findViewById(R.id.sunminafter);


        List<String> AP = new ArrayList<String>();
        AP.add("AM");
        AP.add("PM");
        AP.add(" ");

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

        List<String> mins = new ArrayList<String>();
        mins.add("00");
        mins.add("15");
        mins.add("30");
        mins.add("45");
        mins.add("M");


        HintAdapter hintadapter = new HintAdapter( this, AP, android.R.layout.simple_spinner_item);
        hintadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        HintAdapter hintadapterhour = new HintAdapter( this, hours, android.R.layout.simple_spinner_item);
        hintadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        HintAdapter hintadaptermin = new HintAdapter( this, mins, android.R.layout.simple_spinner_item);
        hintadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mondayAP.setAdapter(hintadapter);
        mondayAP.setSelection(hintadapter.getCount());
        mondayAP2.setAdapter(hintadapter);
        mondayAP2.setSelection(hintadapter.getCount());
        tuesdayAP.setAdapter(hintadapter);
        tuesdayAP.setSelection(hintadapter.getCount());
        tuesdayAP2.setAdapter(hintadapter);
        tuesdayAP2.setSelection(hintadapter.getCount());
        wedAP.setAdapter(hintadapter);
        wedAP.setSelection(hintadapter.getCount());
        wedAP2.setAdapter(hintadapter);
        wedAP2.setSelection(hintadapter.getCount());
        thurAP.setAdapter(hintadapter);
        thurAP.setSelection(hintadapter.getCount());
        thurAP2.setAdapter(hintadapter);
        thurAP2.setSelection(hintadapter.getCount());
        friAP.setAdapter(hintadapter);
        friAP.setSelection(hintadapter.getCount());
        friAP2.setAdapter(hintadapter);
        friAP2.setSelection(hintadapter.getCount());
        satAP.setAdapter(hintadapter);
        satAP.setSelection(hintadapter.getCount());
        satAP2.setAdapter(hintadapter);
        satAP2.setSelection(hintadapter.getCount());
        sunAP.setAdapter(hintadapter);
        sunAP.setSelection(hintadapter.getCount());
        sunAP2.setAdapter(hintadapter);
        sunAP2.setSelection(hintadapter.getCount());


        mondayh1.setAdapter(hintadapterhour);
        mondayh1.setSelection(hintadapterhour.getCount());
        mondaym1.setAdapter(hintadaptermin);
        mondaym1.setSelection(hintadaptermin.getCount());
        mondayh2.setAdapter(hintadapterhour);
        mondayh2.setSelection(hintadapterhour.getCount());
        mondaym2.setAdapter(hintadaptermin);
        mondaym2.setSelection(hintadaptermin.getCount());

        tuesdayh1.setAdapter(hintadapterhour);
        tuesdayh1.setSelection(hintadapterhour.getCount());
        tuesdaym1.setAdapter(hintadaptermin);
        tuesdaym1.setSelection(hintadaptermin.getCount());
        tuesdayh2.setAdapter(hintadapterhour);
        tuesdayh2.setSelection(hintadapterhour.getCount());
        tuesdaym2.setAdapter(hintadaptermin);
        tuesdaym2.setSelection(hintadaptermin.getCount());

        wedh1.setAdapter(hintadapterhour);
        wedh1.setSelection(hintadapterhour.getCount());
        wedm1.setAdapter(hintadaptermin);
        wedm1.setSelection(hintadaptermin.getCount());
        wedh2.setAdapter(hintadapterhour);
        wedh2.setSelection(hintadapterhour.getCount());
        wedm2.setAdapter(hintadaptermin);
        wedm2.setSelection(hintadaptermin.getCount());

        thurh1.setAdapter(hintadapterhour);
        thurh1.setSelection(hintadapterhour.getCount());
        thurm1.setAdapter(hintadaptermin);
        thurm1.setSelection(hintadaptermin.getCount());
        thurh2.setAdapter(hintadapterhour);
        thurh2.setSelection(hintadapterhour.getCount());
        thurm2.setAdapter(hintadaptermin);
        thurm2.setSelection(hintadaptermin.getCount());

        frih1.setAdapter(hintadapterhour);
        frih1.setSelection(hintadapterhour.getCount());
        frim1.setAdapter(hintadaptermin);
        frim1.setSelection(hintadaptermin.getCount());
        frih2.setAdapter(hintadapterhour);
        frih2.setSelection(hintadapterhour.getCount());
        frim2.setAdapter(hintadaptermin);
        frim2.setSelection(hintadaptermin.getCount());

        sath1.setAdapter(hintadapterhour);
        sath1.setSelection(hintadapterhour.getCount());
        satm1.setAdapter(hintadaptermin);
        satm1.setSelection(hintadaptermin.getCount());
        sath2.setAdapter(hintadapterhour);
        sath2.setSelection(hintadapterhour.getCount());
        satm2.setAdapter(hintadaptermin);
        satm2.setSelection(hintadaptermin.getCount());

        sunh1.setAdapter(hintadapterhour);
        sunh1.setSelection(hintadapterhour.getCount());
        sunm1.setAdapter(hintadaptermin);
        sunm1.setSelection(hintadaptermin.getCount());
        sunh2.setAdapter(hintadapterhour);
        sunh2.setSelection(hintadapterhour.getCount());
        sunm2.setAdapter(hintadaptermin);
        sunm2.setSelection(hintadaptermin.getCount());
        //Fetch the transfered data from EmployeeHome
        final String sessionUser = Integer.toString(getIntent().getIntExtra("empID",-1));
        //checks wether clinic exists
        toastMessage("Hello this is your user ID : " + sessionUser);
        //if there is a clinic
        if (sessionUser != null){
            Integer clID = Integer.valueOf(sessionUser);
            mon.setText(db.findHourMonday(clID));
            toastMessage("This is the current data " + db.findHourMonday(clID));
            tue.setText(db.findHourTuesday(clID));
            wed.setText(db.findHourWednesday(clID));
            thur.setText(db.findHourThursday(clID));
            fri.setText(db.findHourFriday(clID));
            sat.setText(db.findHourSaturday(clID));
            sun.setText(db.findHourSunday(clID));
        }



        submithours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String monday, tuesday, wednesday, thursday, friday, saturday, sunday;
                if (monswi.isChecked()) {

                    String monh1 = mondayh1.getSelectedItem().toString();
                    String monh2 = mondayh2.getSelectedItem().toString();
                    String monAP = mondayAP.getSelectedItem().toString();
                    String monm1 = mondaym1.getSelectedItem().toString();
                    String monm2 = mondaym2.getSelectedItem().toString();
                    String monAP2 = mondayAP2.getSelectedItem().toString();

                    if (monh1.equals("H") || monh2.equals("H") || monAP.isEmpty() || monm1.equals("M") || monm2.equals("M") || monAP2.isEmpty()) {
                        toastMessage("Please fill hours for selected days!");
                        return;
                    } else {
                        if (validateHour(monh1, monm1, monAP, monh2, monm2, monAP2)) {
                            monday = monh1 + ":" + monm1 + monAP + " to " + monh2 + ":" + monm2 + monAP2;
                        }
                        else{
                            toastMessage("Invalid time selection for Monday");
                            return;
                        }
                    }
                }
                    else {
                    monday = "Not Open";
                }

                if (tueswi.isChecked()) {
                    String tueh1 = tuesdayh1.getSelectedItem().toString();
                    String tueh2 = tuesdayh2.getSelectedItem().toString();
                    String tueAP = tuesdayAP.getSelectedItem().toString();
                    String tuem1 = tuesdaym1.getSelectedItem().toString();
                    String tuem2 = tuesdaym2.getSelectedItem().toString();
                    String tueAP2 = tuesdayAP2.getSelectedItem().toString();

                    if (tueh1.equals("H")||tueh2.equals("H")||tueAP.isEmpty()||tuem1.equals("M")||tuem2.equals("M")||tueAP2.isEmpty()){
                        toastMessage("Please fill hours for selected days!");
                        return;
                    }
                    else{
                        if (validateHour(tueh1,tuem1,tueAP,tueh2,tuem2,tueAP2)){
                            tuesday = tueh1 + ":" + tuem1 + tueAP + " to " + tueh2 + ":" + tuem2 + tueAP2;}
                        else{
                            toastMessage("Invalid time selection for Tuesday");
                            return;
                        }
                    }
                } else {
                    tuesday = "Not Open";
                }

                if (wedswi.isChecked()) {
                    String wednh1 = wedh1.getSelectedItem().toString();
                    String wednh2 = wedh2.getSelectedItem().toString();
                    String wednAP = wedAP.getSelectedItem().toString();
                    String wednm1 = wedm1.getSelectedItem().toString();
                    String wednm2 = wedm2.getSelectedItem().toString();
                    String wednAP2 = wedAP2.getSelectedItem().toString();

                    if (wednh1.equals("H")||wednh2.equals("H")||wednAP.isEmpty()||wednm1.equals("M")||wednm2.equals("M")||wednAP2.isEmpty()){
                        toastMessage("Please fill hours for selected days!");
                        return;
                    }
                    else{
                        if (validateHour(wednh1,wednm1,wednAP,wednh2,wednm2,wednAP2)){
                            wednesday = wednh1 + ":" + wednm1 + wednAP + " to " + wednh2 + ":" + wednm2 + wednAP2;}
                        else{
                            toastMessage("Invalid time selection for Wednesday");
                            return;
                        }
                        }

                } else {
                    wednesday = "Not Open";
                }

                if (thurswi.isChecked()) {
                    String thuh1 = thurh1.getSelectedItem().toString();
                    String thuh2 = thurh2.getSelectedItem().toString();
                    String thuAP = thurAP.getSelectedItem().toString();
                    String thum1 = thurm1.getSelectedItem().toString();
                    String thum2 = thurm2.getSelectedItem().toString();
                    String thuAP2 = thurAP2.getSelectedItem().toString();

                    if (thuh1.equals("H")||thuh2.equals("H")||thuAP.isEmpty()||thum1.equals("M")||thum2.equals("M")||thuAP2.isEmpty()){
                        toastMessage("Please fill hours for selected days!");
                        return;
                    }
                    else{
                        if (validateHour(thuh1,thum1,thuAP,thuh2,thum2,thuAP2)){
                        thursday = thuh1 + ":" + thum1 + thuAP + " to " + thuh2 + ":" + thum2 + thuAP2;}
                        else {
                            toastMessage("Invalid time selection for Thursday");
                            return;
                        }
                        }
                } else {
                    thursday = "Not Open";
                }

                if (friswi.isChecked()) {
                    String fridh1 = frih1.getSelectedItem().toString();
                    String fridh2 = frih2.getSelectedItem().toString();
                    String fridAP = friAP.getSelectedItem().toString();
                    String fridm1 = frim1.getSelectedItem().toString();
                    String fridm2 = frim2.getSelectedItem().toString();
                    String fridAP2 = friAP2.getSelectedItem().toString();

                    if (fridh1.equals("H")||fridh2.equals("H")||fridAP.isEmpty()||fridm1.equals("M")||fridm2.equals("M")||fridAP2.isEmpty()){
                        toastMessage("Please fill hours for selected days!");
                        return;
                    }
                    else{
                        if (validateHour(fridh1,fridm1,fridAP,fridh2,fridm2,fridAP2)){
                        friday = fridh1 + ":" + fridm1 + fridAP + " to " + fridh2 + ":" + fridm2 + fridAP2;}
                        else {
                            toastMessage("Invalid time selection for Friday");
                            return;
                        }
                    }
                } else {
                    friday = "Not Open";
                }

                if (satswi.isChecked()) {
                    String satuh1 = sath1.getSelectedItem().toString();
                    String satuh2 = sath2.getSelectedItem().toString();
                    String satuAP = satAP.getSelectedItem().toString();
                    String satum1 = satm1.getSelectedItem().toString();
                    String satum2 = satm2.getSelectedItem().toString();
                    String satuAP2 = satAP2.getSelectedItem().toString();

                    if (satuh1.equals("H")||satuh2.equals("H")||satuAP.isEmpty()||satum1.equals("M")||satum2.equals("M")||satuAP2.isEmpty()){
                        toastMessage("Please fill hours for selected days!");
                        return;
                    }
                    else{
                        if (validateHour(satuh1,satum1,satuAP,satuh2,satum2,satuAP2)){
                        saturday = satuh1 + ":" + satum1 + satuAP + " to " + satuh2 + ":" + satum2 + satuAP2;}
                        else {
                            toastMessage("Invalid time selection for Saturday");
                            return;
                        }
                        }
                } else {
                    saturday = "Not Open";
                }

                if (sunswi.isChecked()) {
                    String sundh1 = sunh1.getSelectedItem().toString();
                    String sundh2 = sunh2.getSelectedItem().toString();
                    String sundAP = sunAP.getSelectedItem().toString();
                    String sundm1 = sunm1.getSelectedItem().toString();
                    String sundm2 = sunm2.getSelectedItem().toString();
                    String sundAP2 = sunAP2.getSelectedItem().toString();

                    if (sundh1.equals("H")||sundh2.equals("H")||sundAP.isEmpty()||sundm1.equals("M")||sundm2.equals("M")||sundAP2.isEmpty()){
                        toastMessage("Please fill hours for selected days!");
                        return;
                    }
                    else{
                        if (validateHour(sundh1,sundm1,sundAP,sundh2,sundm2,sundAP2)){
                        sunday = sundh1 + ":" + sundm1 + sundAP + " to " + sundh2 + ":" + sundm2 + sundAP2;}
                        else {
                            toastMessage("Invalid time selection for Sunday");
                            return;
                        }
                        }
                } else {
                    sunday = "Not Open";
                }

                System.out.println(monday);
                System.out.println(tuesday);
                System.out.println(wednesday);
                System.out.println(thursday);
                System.out.println(friday);
                System.out.println(saturday);
                System.out.println(sunday);

                //get clinic id, convert to integer
                String cliID =sessionUser;
                toastMessage("Current cliId = " + cliID);
                //checks if clinic profile is complete first
                if (cliID == null) {
                    toastMessage("Please complete Clinic Profile first!");


                } else {
                    //if clinic already submmited hours, it will only update
                    if (db.validateClinicHours(cliID)) {
                        db.updateClinicHours(cliID, monday, tuesday, wednesday, thursday, friday, saturday, sunday);
                        submithours.setText("Edit hours");

                        toastMessage("Hours Updated");

                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);


                    } else {
                        db.insertClinicHours(sessionUser, monday, tuesday, wednesday, thursday, friday, saturday, sunday);
                        submithours.setText("Edit hours");
                        toastMessage("Hours Added");

                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);

                    }

                }
            }
        });

    }

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

}
