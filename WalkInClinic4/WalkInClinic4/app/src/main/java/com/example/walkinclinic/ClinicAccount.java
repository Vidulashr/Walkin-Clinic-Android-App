package com.example.walkinclinic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class ClinicAccount extends AppCompatActivity {
    DatabaseSQLiteHelper db;
    Switch licenseToggle;
    Button submitClinic,updateClinic,showcurrent;
    RadioButton verifyAdd;
    TextView title;
    EditText clinicName,clinicAddress,clinicPhone, clinicDescription;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinicaccount);

        db = new DatabaseSQLiteHelper(this);
        final int sessionUser = getIntent().getIntExtra("empID",-1);
        toastMessage("This is your session ID = " + sessionUser);
        clinicName = (EditText) findViewById(R.id.txt_clinicName);
        clinicAddress = (EditText) findViewById(R.id.txt_clinicAddress);
        clinicPhone = (EditText) findViewById(R.id.txt_clinicPhone);
        clinicDescription = (EditText) findViewById(R.id.txt_clinicDescription);
        licenseToggle = ((Switch) findViewById(R.id.toggle_license));
        submitClinic = (Button) findViewById(R.id.btn_completeClinic);
        verifyAdd = (RadioButton)findViewById(R.id.location_verificationclinic);
        updateClinic = (Button)findViewById(R.id.btn_updateClinic);
        title = (TextView)findViewById(R.id.text_completeprofile);
        showcurrent = (Button)findViewById(R.id.btn_currentClinic);

        //title changes if profile previously completed
        if (db.findClinicID(sessionUser)!=null){
            submitClinic.setEnabled(false); }


        submitClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String name = clinicName.getText().toString();
                    String address = clinicAddress.getText().toString();
                    String phone = clinicPhone.getText().toString();
                    String description = clinicDescription.getText().toString();
                    Boolean licensed = licenseToggle.isChecked();

                    if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                        //One is empty, Toast an error message!
                        toastMessage("PLEASE FILL IN MANDATORY * FIELDS!"); }

                    else {
                        if (isValidAddress(address) && isValidPhoneNum(phone)) {
                            if (db.checkAvbClinic(name)) {
                                boolean added = db.insertNewClinic(name, address, phone, description, licensed, Integer.toString(sessionUser));

                                if (added) {
                                    toastMessage("COMPLETED CLINIC PROFILE");
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);}

                                else { toastMessage("ERROR IN PROFILE COMPLETION"); } }
                            else { toastMessage("CLINIC NAME ALREADY TAKEN"); } }
                        else { toastMessage("INVALID ENTRY, TRY AGAIN!"); } }
                }
        });


        updateClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = clinicName.getText().toString();
                String address = clinicAddress.getText().toString();
                String phone = clinicPhone.getText().toString();
                String description = clinicDescription.getText().toString();
                Boolean licensed = licenseToggle.isChecked();

                System.out.println(name);
                System.out.println(address);
                System.out.println(phone);
                System.out.println(description);
                System.out.println(licensed);

                if(name.isEmpty() || address.isEmpty() || phone.isEmpty()){
                    //One is empty, Toast an error message!
                    toastMessage("PLEASE FILL IN MANDATORY * FIELDS!"); }


                else {
                    if (isValidAddress(address) && isValidPhoneNum(phone) && db.checkAvbClinic(name)) {
                            boolean added = db.updateClinic(name, address, phone, description, licensed, String.valueOf(sessionUser));

                            if (added) { toastMessage("UPDATED CLINIC PROFILE"); }

                            else {toastMessage("ERROR IN PROFILE UPDATE"); } }

                    else { toastMessage("INVALID ENTRY, TRY AGAIN!"); }}
            }});


        verifyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInGoogleMaps(v);
            }
        });


        showcurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clinicName.setText(db.findClinicName(sessionUser));
                clinicAddress.setText(db.findClinicAddress(sessionUser));
                clinicPhone.setText(db.findClinicPhone(sessionUser));
                clinicDescription.setText(db.findClinicDescription(sessionUser));
                if (true){
                    licenseToggle.setChecked(true);
                }
                else{
                    licenseToggle.setChecked(false);
                }
            }
        });


    }



    //VALIDITY




    public boolean isValidPhoneNum(String number){
        if(number.length() > 15 ){
            return false;
        }

        if(number.length() < 10 ){

            return false;
        }
        for (int i = 0 ; i < number.length() ; i++){
            char c = number.charAt(i);
            if(!Character.isDigit(c)){
                return false;
            }


        }
        return true;
    }

    //Last thing to validate is address!
    //Well an address is compose of digits and then street name!

    private boolean isValidAddress( String address ) {
        int count = 0;
        for (int i = 0; i < address.length(); i++) {
            if (!Character.isDigit(address.charAt(i)) && i == 0){
                return false;
            }
            if (Character.isWhitespace(address.charAt(i))) {
                count++;
            }
            if (! ( Character.isDigit(address.charAt(i)) || Character.isLetter(address.charAt(i)) || Character.isWhitespace(address.charAt(i)) ) ) {
                return false;
            }
        }
        if (count == 2) {
            return true;
        }
        return false;
    }

    public void openInGoogleMaps(View view) {
        EditText addToValidate = (EditText) findViewById(R.id.txt_clinicAddress);
        Uri gmmIntentUri = Uri.parse("http://maps.google.co.in/maps?q="+addToValidate.getText());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }

    public void toastMessage(String x){
        Toast.makeText(this,x, Toast.LENGTH_SHORT).show();
    }

}



