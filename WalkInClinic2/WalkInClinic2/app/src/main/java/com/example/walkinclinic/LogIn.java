package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LogIn extends AppCompatActivity {
    //Initialize all variables
    EditText username,password;
    Button logInButt,registerButt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //I'll associate the buttons to the variables listed ontop!
        //Same thing for my EditText widgets
        logInButt = (Button)findViewById(R.id.logInButt);
        registerButt = (Button)findViewById(R.id.RegisterButt);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        final DatabaseSQLiteHelper db = new DatabaseSQLiteHelper(this);

        //So user can do 2 different actions in the page.. user can either log in or register

        //If the login button is clicked... I need to do the follow :
        // 1) Check if he even entered anything (Valid Entry)
        // 2) Check if it's an existent account
        // 3) If it's an existent account, grant him access to the app.

        logInButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if valid entries!
                String name = username.getText().toString();
                String pass = hashPass(password.getText().toString());
                //Now a whole lot to verify with their entries, I just need to check whether it's an empty field or not!

                //FOR TESTING PURPOSES
                if(db.validateLogIn(name,pass)){
                    System.out.println(db.getCurrentType(name));
                    System.out.println(db.getCurrentFirstName(name));
                }
                else {
                    System.out.println("NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                }

                //Workind code
                if(name.isEmpty() || pass.isEmpty()){
                    toastMessage("Please Enter Username or Password!");
                }


                else{
                    //I want to check my database whether this is a legit account or not!
                    if(db.validateLogIn(name,pass)){
                        toastMessage("Successful Login");

                        //if admin
                        if (db.getCurrentFirstName(name).equals("admin")){
                            moveToAdminHome(name);
                        }

                        //Otherwise, checks whether patient or employee
                        //if patient
                        else{
                            if (db.getCurrentType(name).equals("P")){
                                moveToPatientHome(name);
                            }
                            //if employee
                            else if (db.getCurrentType(name).equals("E")){
                                moveToEmployeeHome(name);
                            }
                        }
                    }
                    //if user/pass combo not valid
                    else{
                        toastMessage("Username or Password wrong, Try again.");

                    }

                }

            }
        });

        registerButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send user to the registration page! Where they can select either Patient or Employee!
                moveToSelectAccuntType();
            }
        });
    }


    public void toastMessage(String x){
        Toast.makeText(this,x, Toast.LENGTH_SHORT).show();
    }

    private void moveToSelectAccuntType(){
        Intent newScreen = new Intent(this,SelectAccountType.class);
        startActivity(newScreen);
    }


    private void moveToAdminHome(String userId){
        //Different thing about this new Intent is that I'm going to send an additional piece of data.
        //I'm going to send over the unique ID associated to the used that just logged in
        //Knowing the unique ID, I can know who exactly logged in!
        Intent newScreen = new Intent(this, AdminHome.class);
        newScreen.putExtra("USER_Name",userId);
        startActivity(newScreen);
    }

    private void moveToPatientHome(String userId){
        //Different thing about this new Intent is that I'm going to send an additional piece of data.
        //I'm going to send over the unique ID associated to the used that just logged in
        //Knowing the unique ID, I can know who exactly logged in!
        Intent newScreen = new Intent(this, PatientHome.class);
        newScreen.putExtra("USER_Name",userId);
        startActivity(newScreen);
    }


    private void moveToEmployeeHome(String userId){
        //Different thing about this new Intent is that I'm going to send an additional piece of data.
        //I'm going to send over the unique ID associated to the used that just logged in
        //Knowing the unique ID, I can know who exactly logged in!
        Intent newScreen = new Intent(this, EmployeeHome.class);
        newScreen.putExtra("USER_Name",userId);
        startActivity(newScreen);
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



}
