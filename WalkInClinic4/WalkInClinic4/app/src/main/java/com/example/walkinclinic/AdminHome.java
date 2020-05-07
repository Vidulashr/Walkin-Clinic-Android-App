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

public class AdminHome extends AppCompatActivity {
    Button createservice,userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseSQLiteHelper db;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        createservice = (Button) findViewById(R.id.createService);
        userlist = (Button) findViewById(R.id.btn_users);
        db = new DatabaseSQLiteHelper(this);
        User currUser;
        //I can fetch the current user ID by doing :
        String sessionUser = getIntent().getStringExtra("USER_Name");
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


        Admin admin = new Admin(sessionUser);
        String message = "Welcome Admin";
        toastMessage("You're Currently LoggedIn as an " + admin.getRole());
        viewText.setText(message);

        createservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //I want to send them to the service form
                moveToServiceForm();
            }
        });

        userlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //I want to send them to the service form
                moveToUserList();
            }
        });


    }

    private void moveToServiceForm(){
        Intent newScreen = new Intent(this,ServiceForm.class);
        startActivity(newScreen);
    }

    private void moveToUserList(){
        Intent newScreen = new Intent(this,UserList.class);
        startActivity(newScreen);
    }

    public void toastMessage(String x){
        Toast.makeText(this,x, Toast.LENGTH_LONG).show();
    }

}
