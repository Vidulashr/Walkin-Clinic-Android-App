package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;





public class PatientForm extends AppCompatActivity {
    //Goal of this class : Take all of users registration info, store it into data IF ALL FIELDS ARE PROPERLY ENTERED
    //Once store inside database, return them back to login screen so they can log on!

    //ALL MY EditText Variables
    EditText username,password,firstName,lastName,phoneNumber,address,email;
    //My Button var
    Button submitEntries;
    //Button to verify address
    //My Database var
    DatabaseSQLiteHelper db;
    RadioButton verifyAdd;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_form);
        //Associate Variables TO The Actual Buttons
        username =(EditText) findViewById(R.id.username);
        password =(EditText) findViewById(R.id.password);
        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        phoneNumber =(EditText) findViewById(R.id.phoneNumber);
        address =(EditText) findViewById(R.id.address);
        email = (EditText)findViewById(R.id.email);
        submitEntries = (Button)findViewById(R.id.submitEntries);
        verifyAdd = (RadioButton)findViewById(R.id.locationverification);

        //Instance of DatabaseSQLiteHelper
        db = new DatabaseSQLiteHelper(this);


        //I want to check if the susbmit button has been clicked!

        submitEntries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If Clicked, I want to very if entries are correct!
                String user = username.getText().toString();
                String pass = hashPass(password.getText().toString());
                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String pNumb = phoneNumber.getText().toString();
                String addy = address.getText().toString();
                String mail = email.getText().toString();

                //I first want to make sure none are null
                if(user.isEmpty() || pass.isEmpty() || fName.isEmpty() || lName.isEmpty() || pNumb.isEmpty() || addy.isEmpty() || mail.isEmpty()){
                    //One is empty, Toast an error message!
                    toastMessage("PLEASE FILL IN FIELDS!");
                }

                else{
                    //I want to check if name and last name entered are valid and address and E-mail are valid!
                    if(isValidEmail(mail) && isValidName(fName) && isValidName(lName) && isValidPhoneNum(pNumb) && isValidAddress(addy)){

                        //I now want to check if there's been a duplicate of e-mail. telephone number and username!
                        if(db.checkAvbUser(user) && db.checkAvbPhone(pNumb) && db.checkAvbEmail(mail)){
                            //I'll add the data!
                            boolean added = db.insertNewData(user,pass,fName,lName,pNumb,addy,mail,"P");

                            if(added){
                                toastMessage("You're Now Registered! ");
                                //If registered, I want to return them to the login screen
                                moveBackToLogIn();
                            }
                            else{
                                toastMessage("asdasfsadgsdfghs ");
                            }
                        }

                        else{
                            //Toast --> You've entered a duplicate!
                            toastMessage("CERTAIN INFO ALREADY ASSOCIATED TO ANOTHER ACCOUNT");
                        }
                    }
                    else{
                        //Toast --> invalid entry!
                        if(!isValidEmail(mail)){toastMessage("Invalid Email");}
                        if(!isValidName(fName)){toastMessage("Invalid First Name");}
                        if(!isValidName(lName)){toastMessage("Invalid Last Name");}
                        if(!isValidPhoneNum(pNumb)){toastMessage("Invalid Phone Number");}
                        if(!isValidAddress(addy)){toastMessage("Invalid Address");}

                    }
                }


            }
        });
        verifyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInGoogleMaps(v);
            }
        });
        



    }


    public boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }




    public boolean isValidName(String name){
        //Basically just have to check that there's no numbers or spaces?
        for (int i = 0 ; i < name.length() ; i++){
            char c = name.charAt(i);
            //I want to check if a that char is a letter
            if(!Character.isLetter(c)){
                return false;
            }

        }
        return true;
    }



    public boolean isValidPhoneNum(String number){
        if(number.length() > 15 ){
            return false; }

        if(number.length() < 10 ){
            return false; }

        for (int i = 0 ; i < number.length() ; i++){
            char c = number.charAt(i);
            if(!Character.isDigit(c)){
                return false;} }
        return true; }



    //Last thing to validate is address!
    //Well an address is compose of digits and then street name!

    //Last thing to validate is address!
    //Well an address is compose of digits and then street name!

    public boolean isValidAddress( String address ) {
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

    public void toastMessage(String x){
        Toast.makeText(this,x, Toast.LENGTH_SHORT).show();
    }


    public String hashPass (String unhashedPass){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(unhashedPass.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            return  String.format("%064x", new BigInteger( 1, digest ));
        }

        catch (NoSuchAlgorithmException e){
            return "error";
        }

    }
    
    public void openInGoogleMaps(View view) {
        EditText addToValidate = (EditText) findViewById(R.id.address);
        Uri gmmIntentUri = Uri.parse("http://maps.google.co.in/maps?q="+addToValidate.getText());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }

    private void moveBackToLogIn(){
        Intent newActivity;
        newActivity = new Intent(this,LogIn.class);
        startActivity(newActivity);
    }

    public boolean passStrength(String pass){
        if (pass.length()<8){
            return false; }
        int numCount = 0; int upCount = 0;
        int speCount = 0; int loCount = 0;
        for (int i = 0;i<pass.length();i++){
            char c = pass.charAt(i);
            if(Character.isDigit(c)){
                numCount +=1;
            }
            else if (Character.isAlphabetic(c)){
                if (Character.isLowerCase(c)){
                    loCount+=1; }
                upCount+=1; }

            else{ speCount+=1; } }
        if (numCount==0){
            return false;}
        if (upCount==0){
            return false;}
        if (loCount==0){
            return false;}
        if (speCount==0){
            return false;}
        return true;}



}


