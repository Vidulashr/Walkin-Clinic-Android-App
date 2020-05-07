package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    //ALl what I do here is make sure the button has been clicked and then I'll transfer them over to login screen!
    Button mainBut;
    DatabaseSQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Associate the Button
        mainBut = (Button)findViewById(R.id.mainBut);

        //Now I want to check if it's been clicked! If it has been click... move to login screen
        mainBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //As previously stated, I'd like to move to the login activity!
                moveToLogInScreen();

            }
        });
    }

    private void moveToLogInScreen(){
        Intent newScreen = new Intent(this,LogIn.class);
        startActivity(newScreen);
    }
}
