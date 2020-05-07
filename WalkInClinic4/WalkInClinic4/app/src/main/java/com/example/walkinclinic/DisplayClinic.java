package com.example.walkinclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DisplayClinic extends AppCompatActivity {
    DatabaseSQLiteHelper db ;
    ArrayList<ArrayList<String>> arrayList;
    ListView listOfClinics;
    Button clickedBooked;
    TextView inputedVal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_clinic);

        //Instantiate Database
        final DatabaseSQLiteHelper db = new DatabaseSQLiteHelper(this);

        clickedBooked = findViewById(R.id.userBookName);
        listOfClinics = findViewById(R.id.listOfClinics);
        inputedVal = findViewById(R.id.clinicName2);

        //Get transferred Data!
        final int typeOfSearch = getIntent().getIntExtra("type",-1);
        final String searchData = getIntent().getStringExtra("inputedVal");
        final int patientID = getIntent().getIntExtra("patientID",-1);
        //Now I want to see what kind of search the user entered





        //Fetch the proper data
        if(typeOfSearch == 2){
            arrayList = db.retrieveClinicDataByAddy(searchData); }

        else if(typeOfSearch == 1) {
            arrayList = db.retrieveClinicDataByService(searchData);}

        else if(typeOfSearch == 0){
            ArrayList<String> wordData = new ArrayList<String>();
            String addWord = "";
            for (int i = 0 ; i < searchData.length();i++){
                char c = searchData.charAt(i);
                if(c == '-'){
                    wordData.add(addWord);
                    addWord = ""; }
                else{addWord = addWord + c;} }
            arrayList = db.retrieveClinicDataByHours(wordData); }

        else{toastMessage("Error --> Server Problem"); }






        //I now want to populate my list
        ClinicListAdapter adapter = new ClinicListAdapter(this,R.layout.cliniclist,arrayList,this);
        listOfClinics.setAdapter(adapter);

        listOfClinics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int pos, long mylng) {
                ArrayList<String> selectedFromList =((ArrayList<String>) listOfClinics.getItemAtPosition(pos));
                String name = selectedFromList.get(0);
                inputedVal.setText(name);
            }
        });

        //Check if the book has been clicked
        clickedBooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If they clicked the button, I want to book them into the database!
                //I want to get the clinic name they entered
                String clinicName = inputedVal.getText().toString();
                //Now that I have the clinicName I know it's unique, I want to fetch the clinic ID
                String clinicID = db.getClinicID(clinicName);
                String stringPatID = Integer.toString(patientID);

                if(clinicID == "error"){
                    toastMessage("Unable to Book Appointment");
                }
                else{
                    if(!db.CheckIfBooked(stringPatID)){
                        boolean added = db.bookUser(stringPatID,clinicID);
                        if(added){
                            toastMessage("You've been booked");
                            db.setNewWaitTime(clinicID);}
                        else{toastMessage("Error Has Occured");}}
                    else{
                        toastMessage("You're Currently Already Checked In!"); }}
            }
        });

    }

    public void toastMessage(String x){
        Toast.makeText(this,x, Toast.LENGTH_SHORT).show(); }

    /*private void MoveToPatientHome(String clinicIDtoRate){
        Intent newScreen = new Intent(this,PatientHome.class);
        newScreen.putExtra("usedClinic",clinicIDtoRate);
        startActivity(newScreen);
    }*/

}
