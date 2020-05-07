package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class SelectAccountType extends AppCompatActivity {
    //This is an extremely simple class! All what it does is check what type of account the user wants to create
    //2 options --> 2 buttons, dependent on the button clicked, I can navigate them to the proper form
    Button patientSelected,employeeSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account_type);
        //Associate my variables to the actual buttons
        patientSelected = (Button)findViewById(R.id.patientSelected);
        employeeSelected = (Button)findViewById(R.id.employeeSelected);

        //I want to check which one will get clicked!

        patientSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //I want to set them to the patient form!
                moveToPatientForm();
            }
        });


        employeeSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //I want to send them to the employee form!
                moveToEmployeeForm();
            }
        });


    }

    //Methods to switch activities!

    private void moveToPatientForm(){
        Intent newScreen = new Intent(this, PatientForm.class);
        startActivity(newScreen);
    }

    private void moveToEmployeeForm() {
        Intent newScreen = new Intent(this,EmployeeForm.class);
        startActivity(newScreen);
    }


    }
