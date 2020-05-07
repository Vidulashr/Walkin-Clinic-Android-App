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

public class EmployeeHome extends AppCompatActivity {
    Button selectservices;
    Button yourclinic;
    Button viewwervices;
    Button edithours;
    TextView clinicName,clinicAddress,clinicPhone,username,warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseSQLiteHelper db;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);


        selectservices = (Button)findViewById(R.id.btn_service_employee);
        yourclinic = (Button)findViewById(R.id.btn_clinic_account);
        clinicName = (TextView)findViewById(R.id.txt_homeClinicName);
        clinicAddress = (TextView)findViewById(R.id.txt_homeClinicAddress);
        clinicPhone = (TextView)findViewById(R.id.txt_homeClinicPhone);
        username = (TextView)findViewById(R.id.txt_username);
        viewwervices = (Button)findViewById(R.id.btn_yourservices);
        edithours = (Button)findViewById(R.id.btn_addHours);
        warning = (TextView)findViewById(R.id.txt_completewarning);

        db = new DatabaseSQLiteHelper(this);
        User currUser;
        //I can fetch the current user ID by doing :
        final String sessionUser = getIntent().getStringExtra("USER_Name");

        username.setText(sessionUser);
        final int employeeID = db.fetchUserId(sessionUser);
        TextView viewText = (TextView) findViewById(R.id.textView2);

        if(db.findClinicID(employeeID)!=null){
            warning.setHeight(0); }

        //displaying current clinic info in home screen
        clinicName.setText(db.findClinicName(employeeID));
        clinicAddress.setText(db.findClinicAddress(employeeID));
        clinicPhone.setText(db.findClinicPhone(employeeID));
        toastMessage("This is your session ID = " + employeeID);

        //So knowing the username of the user that just logged on, I can retrieve his data!
        //I can also retrieve where he's a "P" --> Patient or "E" --> Employee for my Database!
        //I'll use this info to create an object of type Patient or Employee!
        int getID = db.fetchUserId(sessionUser);
        String firstName = db.getCurrentFirstName(sessionUser);
        String lastName = db.getCurrentLastName(sessionUser);
        String phone = db.getCurrentPhone(sessionUser);
        String email = db.getCurrentEmail(sessionUser);
        String address = db.getCurrentAddress(sessionUser);

            //CURRENT LOGGED ON USER IS AN EMPLOYEE
        currUser = new Employee(sessionUser,firstName,lastName,phone,address,email,getID);
        String message = "Welcome "+currUser.getFirstName()+" "+currUser.getLastName();
        viewText.setText(message);

        selectservices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToServiceEditer(employeeID);
            }
        });

        yourclinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToClinicAccount(employeeID);
            }
        });

        viewwervices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToServiceViewer(employeeID);
            }
        });

        edithours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToEditHours(employeeID);
            }
        });


    }

    //refreshes everytime you go back from updating hours,services or profile.
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


    private void moveToEditHours(int employeeID){
        Intent newScreen = new Intent(this,ClinicHours.class);
        newScreen.putExtra("empID",employeeID);
        startActivity(newScreen);
    }



    private void moveToServiceViewer(int employeeID){
        Intent newScreen = new Intent(this,ClinicServiceViewer.class);
        newScreen.putExtra("empID",employeeID);
        startActivity(newScreen);
    }



    private void moveToServiceEditer(int employeeID){
        Intent newScreen = new Intent(this,ServiceEditer.class);
        newScreen.putExtra("empID",employeeID);
        startActivity(newScreen);
    }

    private void moveToClinicAccount(int employeeID){
        Intent newScreen = new Intent(this,ClinicAccount.class);
        newScreen.putExtra("empID",employeeID);
        startActivity(newScreen);
    }

    public void toastMessage(String x){
        Toast.makeText(this,x, Toast.LENGTH_LONG).show();
    }

}
