package com.example.encrypteddatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.Cursor;



import androidx.annotation.Nullable;

import java.util.ArrayList;



//SQL_Helper is made to assist with queries and to manage Database!
public class SQL_Helper extends SQLiteOpenHelper {
    //Here I'll list all relevant needed to create my SQL information needed for the creation of my table
    private static final String DATABASE_NAME = "SEG2105";
    private static final String TABLE_NAME = "accounts";
    private static final String COLUMN_NAME = "username";
    private static final String COLUMN2_NAME = "password";
    private static final String TAG = DATABASE_NAME;

    //Here are some relevant statements that will be useful!
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_NAME+" TEXT ," + COLUMN2_NAME + " TEXT )" ;
    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;



    //Create an Instance!
    //Context in Android --> Interface to global information about an application environment
    public SQL_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    //This will be called when the databse is created for the first time
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    //Will be called once Database needs to be upgraded
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //Let's delete the old one and form a new one
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }


    public boolean addData(String data1, String data2){
        Log.d(TAG, "CURRENTLY ADDING "+data1 + " and " + data2 +" TO "+ TABLE_NAME);
        //Create an instance of SQLiteDatabase and call the method getWritableDatabase, will allow me to write data into the database
        SQLiteDatabase db = this.getWritableDatabase();
        //I now want to create the data needed to be added
        ContentValues val = new ContentValues();

        val.put(COLUMN_NAME,data1);
        val.put(COLUMN2_NAME,data2);

        long res = db.insert(TABLE_NAME,null,val);
        //VERIFY IF DATA PROPERLY SAVED
        if(res == -1){
            return false; }

        else{
            return true; }

    }

    public String retrieveData() {
        //I want retrieve data stores in password and show them on screen
        //This will be important for the final version to be able to retrieve data from a database
        //Like before, I'll need to create an instance of the SQLiteDatabase!
        SQLiteDatabase db = this.getWritableDatabase();
        //Now that I have an instance of SQLiteDatabase, I want to send it a query
        String query = "SELECT * FROM " + TABLE_NAME;
        //Return type of the query will be a Cursor type
        Cursor returnValue = db.rawQuery(query, null);
        //Alright, now that I have the returned values from my query, I'll need to go through them!
        // I'll need to create an ArrayList to store data
        ArrayList<String> dataSaved = new ArrayList<String>();
        //I want to iterate through the Cursor obejct --> i'll use the method .moveToNext()

        while(returnValue.moveToNext()){
            //GetString() in Cursor requires the column you'd like to retrieve
            dataSaved.add(returnValue.getString(1));
        }
        //I'll just return the first value inside of the array to see if I'm actually storing data
        String firstOne = dataSaved.get(0);


        return  firstOne;
    }



}
