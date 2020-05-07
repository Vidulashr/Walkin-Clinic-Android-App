package com.example.encrypteddatabase;

import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    SQL_Helper db;
    //Initialize all of my Buttons and my Edit Text Field
    private EditText editText1,editText2;
    private Button addBut, viewBut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SQL_Helper(this);
        //Initialize all my buttons and my text fields
        addBut = (Button)findViewById(R.id.addData);
        viewBut = (Button)findViewById(R.id.viewData);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);


        //I want to check if a button is clicked

        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //I want to add the code
                String userName = editText1.getText().toString();
                String passWord = editText2.getText().toString();
                //I now want to send this info to my database..
                //I'll actually want to hash first, but I'll do that later
                AddData(userName,passWord);
            }
        });

        viewBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toPrint = db.retrieveData();
                toastMessage(toPrint);
            }
        });


    }

    public void AddData(String userName, String password){
        boolean success = db.addData(userName,password);

        if(success){
            toastMessage("DATA HAS BEEN ADDED");
        }

        else{
            toastMessage("DATA HAS FAILED TO ADD");
        }
    }


    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
