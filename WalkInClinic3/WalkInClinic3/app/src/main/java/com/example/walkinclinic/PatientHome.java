package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class PatientHome extends AppCompatActivity {
    Button viewservices,viewClinic,checkOut;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final DatabaseSQLiteHelper db;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        viewClinic = (Button)findViewById(R.id.btn_clinic_patient);
        checkOut = (Button)findViewById(R.id.btn_checkOUT);
        username = (TextView)findViewById(R.id.txt_username2);


        db = new DatabaseSQLiteHelper(this);
        User currUser;
        //I can fetch the current user ID by doing :
        final String sessionUser = getIntent().getStringExtra("USER_Name");
        username.setText(sessionUser);

        final int patientID = db.fetchUserId(sessionUser);
        final String patientString = Integer.toString(patientID);
        TextView viewText = (TextView) findViewById(R.id.textView2);


        //So knowing the username of the user that just logged on, I can retrieve his data!
        //I can also retrieve where he's a "P" --> Patient or "E" --> Employee for my Database!
        //I'll use this info to create an object of type Patient or Employee!
        int getID = db.fetchUserId(sessionUser);
        String firstName = db.getCurrentFirstName(sessionUser);
        String lastName = db.getCurrentLastName(sessionUser);
        String phone = db.getCurrentPhone(sessionUser);
        String email = db.getCurrentEmail(sessionUser);
        String address = db.getCurrentAddress(sessionUser);
        String type = db.getCurrentType(sessionUser);

        currUser = new Patient(sessionUser,firstName,lastName,phone,address,email,getID);
        String message = "Welcome "+currUser.getFirstName()+" "+currUser.getLastName();
        viewText.setText(message);


        //Function That Will Check If Buttons Get Clicked
        viewClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToClinicView(sessionUser,patientID); }});

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.CheckIfBooked(patientString)) {
                    String clinicBooked = db.getClinicBooked(patientString);
                    db.removeBooking(patientString);
                    MoveToClinicRating(clinicBooked, sessionUser);
                }

                else{
                    toastMessage("You Can't Check Out Until You're Booked! ");
                }
            }
        });

    }


    private void MoveToClinicView(String sessUser, int patientID){
        Intent newScreen = new Intent(this,ClinicViewer.class);
        newScreen.putExtra("currUser",sessUser);
        newScreen.putExtra("patientID",patientID);
        startActivity(newScreen);
    }

    private void MoveToClinicRating(String clinicID,String sessUser){
        Intent newScreen = new Intent(this,ClinicRating.class);
        newScreen.putExtra("currUser",sessUser);
        newScreen.putExtra("clinicIDString",clinicID);
        startActivity(newScreen);
    }


    public void toastMessage(String x){
        Toast.makeText(this,x, Toast.LENGTH_LONG).show();
    }

}
