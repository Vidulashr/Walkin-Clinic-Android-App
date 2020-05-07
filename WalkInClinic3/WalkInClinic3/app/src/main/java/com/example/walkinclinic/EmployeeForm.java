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


public class EmployeeForm extends AppCompatActivity {
    //Goal of this class : Take all of users registration info, store it into data IF ALL FIELDS ARE PROPERLY ENTERED
    //Once store inside database, return them back to login screen so they can log on!

    //ALL MY EditText Variables
    EditText username,password,firstName,lastName,phoneNumber,address,email;
    //My Button var
    Button submitEntries;
    //Button to verify address
    RadioButton verifyAdd;
    //My Database var
    DatabaseSQLiteHelper db;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_form);
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
                    if(isValidEmail(mail) && isValidName(fName) && isValidName(lName) && isValidPhoneNum(pNumb) && isValidAddress(addy)) {
                        //Checks pass word strength
                        if (passStrength(password.getText().toString())) {
                            //I now want to check if there's been a duplicate of e-mail. telephone number and username!
                            if (db.checkAvbUser(user) && db.checkAvbPhone(pNumb) && db.checkAvbEmail(mail)) {
                                //I'll add the data!
                                boolean added = db.insertNewData(user, pass, fName, lName, pNumb, addy, mail, "E");

                                if (added) {
                                    toastMessage("You're Now Registered! ");
                                    //If successful, I want to bring them back to login page
                                    moveBackToLogIn();
                                } else {
                                    toastMessage("DATABASE ERROR :(  ");
                                }
                            } else {
                                //Toast --> You've entered a duplicate!
                                toastMessage("CERTAIN INFO ALREADY ASSOCIATED TO ANOTHER ACCOUNT");
                            }
                        }
                        else{
                            toastMessage("Password is Not strong enough. Must contain at least 8 characters, a digit, a letter and a special character");
                        }
                    }
                    else{
                        //Toast --> invalid entry!
                        //Also notify them which one
                        if(!isValidEmail(mail)){toastMessage("Invalid E-mail "); }
                        if(!isValidName(fName)){toastMessage("Invalid First Name"); }
                        if(!isValidName(lName)){ toastMessage("Invalid Last Name "); }
                        if(!isValidPhoneNum(pNumb))toastMessage("Invalid Phone Numb");}


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



    /* I want to allow the following letters :
        [65,90]
        [96,122]
     */
    public boolean isValidName(String name){
        //I'll verify character by character and make sure it's within the allowed range
        for (int i = 0 ; i < name.length() ; i ++ ){
            char c = name.charAt(i);
            int integerVal = (int)c;

            //If c is not within the permissible range, return false
            if((integerVal < 65) || (integerVal > 122 ) || (integerVal > 90 && integerVal < 96) ){
                return false;}
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

    private void moveBackToLogIn(){
        Intent newActivity;
        newActivity = new Intent(this,LogIn.class);
        startActivity(newActivity);
    }


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
    
    public void openInGoogleMaps(View view) {
        EditText addToValidate = (EditText) findViewById(R.id.address);
        Uri gmmIntentUri = Uri.parse("http://maps.google.co.in/maps?q="+addToValidate.getText());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }


    //NEW method to check whether string is at least 8 characters long
    //and contains at least 1 digit, 1 upper case letter and 1 special character
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

    //For testing purposes
    boolean checkAvailableUser(String user){
        return db.checkAvbUser(user);
    }

    boolean addData(String user,String pass,String fName,String lName,String pNumb,String addy,String mail,String type){
        return db.insertNewData(user,pass,fName,lName,pNumb,addy,mail,type);
    }

    boolean deleteUser(String username){
        return db.deleteUser(username); }



}

