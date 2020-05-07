package com.example.walkinclinic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class ClinicRating extends AppCompatActivity {
    DatabaseSQLiteHelper db;
    Button submitRate,dontRate;
    TextView clinicname;
    EditText comment;
    RatingBar rating;


    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_rating);
        submitRate = (Button) findViewById(R.id.btn_RateClinic);
        clinicname = (TextView) findViewById(R.id.clinicNameRate);
        comment = (EditText) findViewById(R.id.text_Comment);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        db = new DatabaseSQLiteHelper(this);
        dontRate = (Button) findViewById(R.id.btn_DontRateClinic);



        final String clinicID = getIntent().getStringExtra("clinicIDString");
        //We require the Clinic's Name
        toastMessage("THIS IS THE CLINIC ID = " + clinicID);
        final String clinicsName = db.getClinicName(clinicID);
        final String getData = getIntent().getStringExtra("currUser");

        if (clinicsName != "error") {
            clinicname.setText(clinicsName);
        }

        //An error has occured, move them back to the patient home
        else {
            toastMessage("Error has occured, Please return to patient home");
        }


        submitRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gather all the required data
                String commentToStore = comment.getText().toString();
                String ratingToStore = Float.toString(rating.getRating());
                //We don't allow 0 stars
                if ((ratingToStore == "0" ||  clinicsName == "error")) {
                    toastMessage("Please Select Amount of Stars"); }

                else{
                    if(db.insertNewRating(clinicID, ratingToStore, commentToStore)){
                        toastMessage("Rating Successfuly Sent");
                        MoveToPatientHome(getData);
                    }

                    else{
                        toastMessage("Server Error"); } }
            }
        });


        dontRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToPatientHome(getData); }
        });

    }

    private void MoveToPatientHome(String getData) {
        Intent newScreen = new Intent(this, PatientHome.class);
        //For an error not to occur, we need to send back the username data with the key "USER_Name"
        newScreen.putExtra("USER_Name", getData);
        startActivity(newScreen);
    }

    public void toastMessage(String x) {
        Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
    }

}


