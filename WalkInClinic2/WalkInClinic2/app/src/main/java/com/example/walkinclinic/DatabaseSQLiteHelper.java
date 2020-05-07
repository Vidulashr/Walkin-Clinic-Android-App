package com.example.walkinclinic;

//Import required packages!
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.os.strictmode.SqliteObjectLeakedViolation;

import androidx.annotation.Nullable;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.security.NoSuchAlgorithmException;
import java.util.List;

//Made to assist queries and manage database!
public class DatabaseSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SEG2105";
    private static final int DATABASE_VERSION = 11;

    //user table
    private static final String TABLE_NAME = "accounts";
    private static final String COLUMN0 = "ID";
    private static final String COLUMN1 = "username";
    private static final String COLUMN2 = "password";
    private static final String COLUMN3 = "firstName";
    private static final String COLUMN4 = "lastName";
    private static final String COLUMN5 = "phoneNumber";
    private static final String COLUMN6 = "address";
    private static final String COLUMN7 = "email";
    private static final String COLUMN8 = "type";

    //service table
    private static final String TABLE_SERVICE = "services";
    private static final String COLUMN9 = "serviceID";
    private static final String COLUMN10 = "serviceName";
    private static final String COLUMN11 = "serviceRole";
    private static final String COLUMN12 = "serviceRate";

    public DatabaseSQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create user table
        String createDatabaseQuery = " CREATE TABLE "+ TABLE_NAME + " ( " +
                COLUMN0 +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN1+" TEXT, " +
                COLUMN2 +" TEXT, " +
                COLUMN3 +" TEXT, " +
                COLUMN4 +" TEXT, "+
                COLUMN5+" TEXT, "+
                COLUMN6 +" TEXT, "+
                COLUMN7+" TEXT, "+
                COLUMN8+" TEXT ); ";

        //create service table
        String createServiceDatabase = " CREATE TABLE "+ TABLE_SERVICE + " ( " +
                COLUMN9 +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN10 +" TEXT, " +
                COLUMN11 +" TEXT, " +
                COLUMN12 +" TEXT ); ";
        //I want to create my database here!
        db.execSQL(createDatabaseQuery);
        db.execSQL(createServiceDatabase);

        //Adding admin access
        String pass = hashPass("5T5ptQ");
        db.execSQL("INSERT INTO accounts (username, password, firstName, lastName, phoneNumber, address, email, type) VALUES('admin','"+pass+"','admin','admin','admin','admin','admin','Admin')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DELETE OLD DATABASE
        String deleteDatabase = "drop table if exists "+TABLE_NAME;
        String deleteServiceDatabase = "drop table if exists "+TABLE_SERVICE;
        db.execSQL(deleteDatabase);
        db.execSQL(deleteServiceDatabase);
        //Create a new one
        onCreate(db);

    }




    //I want to be able to add data and to check if there's any duplicates!
    public boolean insertNewData(String username, String password,String firstName, String lastName, String phoneNumber, String address, String email,String type ) {
        //This will allow me to read and write into database!
        SQLiteDatabase db = this.getWritableDatabase();
        //Create an instance of ContentValues
        ContentValues data = new ContentValues();
        //data.put(COLUMN, VALUE);
        data.put(COLUMN1, username);
        data.put(COLUMN2, password);
        data.put(COLUMN3, firstName);
        data.put(COLUMN4, lastName);
        data.put(COLUMN5, phoneNumber);
        data.put(COLUMN6, address);
        data.put(COLUMN7, email);
        data.put(COLUMN8, type);

        //Now i want to submit this new data into my database
        long insert = db.insert(TABLE_NAME, null, data);

        //If the data WAS NOT successfully added, the value of insert will be -1

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public User findUser(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN1 + " = \"" + username + "\"";
        Cursor cursor = db.rawQuery(query,null);
        User user;
        if (cursor.moveToFirst()){
            if(cursor.getString(8).equals("P")){
                user = new Patient();
                user.setUsername(cursor.getString(1));
                user.setFirstName(cursor.getString(3));
                user.setLastName(cursor.getString(4));
                user.setPhoneNumber(cursor.getString(5));
                user.setAddress(cursor.getString(6));
                user.setEmail(cursor.getString(7));
                cursor.close();
            }

            else{
                user= new Employee();
                user.setUsername(cursor.getString(1));
                user.setFirstName(cursor.getString(3));
                user.setLastName(cursor.getString(4));
                user.setPhoneNumber(cursor.getString(5));
                user.setAddress(cursor.getString(6));
                user.setEmail(cursor.getString(7));
                cursor.close();}
        }
        else {
            user = null;
        }
        db.close();
        return user;
    }

    public boolean deleteUser(String username){
        //Cannot try to remove admin
        if(username.equals("admin")){
            return false;}

        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN1 + " = \"" + username + "\"";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            String idStr = cursor.getString(0);
            db.delete(TABLE_NAME,COLUMN0+" ="+idStr,null);
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }


    //DONE
    public boolean insertService(String name, String role, String rate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COLUMN10, name);
        data.put(COLUMN11, role);
        data.put(COLUMN12, rate);
        long insert = db.insert(TABLE_SERVICE,null, data);

        if (insert==-1) {
            return false;
        }
        else{
            return true;
        }
    }

    //DONE
    public boolean deleteService(String servicename){
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_SERVICE + " WHERE " + COLUMN10 + " = \"" + servicename + "\"";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            String idStr = cursor.getString(0);
            db.delete(TABLE_SERVICE,COLUMN9+" ="+idStr,null);
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    //DONE
    public Service findService(String servicename){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_SERVICE + " WHERE " + COLUMN10 + " = \"" + servicename + "\"";
        Cursor cursor = db.rawQuery(query,null);
        Service service = new Service();
        if (cursor.moveToFirst()){
            service.setServiceID(cursor.getString(0));
            service.setName(cursor.getString(1));
            service.setRole(cursor.getString(2));
            service.setHourlyRate(cursor.getString(3));
            cursor.close();}
        else {
            service=null;}
        db.close();
        return service;
    }

    //DONE
    public boolean updateService(String name, String role, String rate){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COLUMN10, name);
        data.put(COLUMN11, role);
        data.put(COLUMN12, rate);
        long update = db.update(TABLE_SERVICE, data,COLUMN10+ "=?",new String[]{name});
        if (update==-1){
            return false;
        }
        else{
            return true;}
    }

    //LIST OF SERVICES IN ORDER TO VIEW
    public ArrayList<Service> getAllServices(){
        ArrayList<Service> services = new ArrayList<Service>();
        String query = "SELECT * FROM " + TABLE_SERVICE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    Service service = new Service();
                    service.setServiceID(cursor.getString(0));
                    service.setName(cursor.getString(1));
                    service.setRole(cursor.getString(2));
                    service.setHourlyRate(cursor.getString(3));
                    services.add(service);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return services;
    }

    //LIST OF ALL THE USERS TO VIEW, ONLY SHOWS PATIENTS AND EMPLOYEES NOT THE ADMIN
    public ArrayList<User> getAllUsers(){
        ArrayList<User> users = new ArrayList<User>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    //if admin, dont add to list
                    if (cursor.getString(1).equals("admin")){
                    }
                    //else add to list of users
                    else{
                        if (cursor.getString(8).equals("P")) {
                            Patient user = new Patient();
                            user.setUsername(cursor.getString(1));
                            user.setFirstName(cursor.getString(3));
                            user.setLastName(cursor.getString(4));
                            user.setPhoneNumber(cursor.getString(5));
                            user.setAddress(cursor.getString(6));
                            user.setEmail(cursor.getString(7));
                            users.add(user);
                        }
                        //if employee
                        else if(cursor.getString(8).equals("E")){
                            Employee user = new Employee();
                            user.setUsername(cursor.getString(1));
                            user.setFirstName(cursor.getString(3));
                            user.setLastName(cursor.getString(4));
                            user.setPhoneNumber(cursor.getString(5));
                            user.setAddress(cursor.getString(6));
                            user.setEmail(cursor.getString(7));
                            users.add(user);}}

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return users;
    }


    //METHODS THAT WILL CHECK IF  E-MAIL OR USERNAME & PHONE NUMBER ARE AVAILABLE!

    public boolean checkAvbEmail(String email){
        //We just want to read from database!
        SQLiteDatabase db = getReadableDatabase();
        //The Query I want to execute!
        Cursor cursor = db.rawQuery("Select * from accounts where email=?",new String[]{email});
        //This will check if there was a result, it will return false, meaning it's already in use!
        if (cursor.getCount()>0){return false;}
        //It will return true if that e-mail is available
        else{return true;}
    }

    public boolean checkAvbPhone(String phoneNum){
        //We just want to read from database!
        SQLiteDatabase db = getReadableDatabase();
        //The Query I want to execute!
        Cursor cursor = db.rawQuery("Select * from accounts where username=?",new String[]{phoneNum});
        //This will check if there was a result, it will return false, meaning it's already in use!
        if (cursor.getCount()>0){return false;}
        //It will return true if that e-mail is available
        else{return true;}
    }

    public boolean checkAvbUser(String user){
        //We just want to read from database!
        SQLiteDatabase db = getReadableDatabase();
        //The Query I want to execute!
        Cursor cursor = db.rawQuery("Select * from accounts where username=?",new String[]{user});
        //This will check if there was a result, it will return false, meaning it's already in use!
        if (cursor.getCount()>0){return false;}
        //It will return true if that e-mail is available
        else{return true;}
    }


    //Verify If Proper LogIn!
    public boolean validateLogIn (String username, String password){
        SQLiteDatabase db = getReadableDatabase();
        //I want to execute the following query
        Cursor cursor = db.rawQuery("Select * from accounts where username=? and password=?",new String[]{username,password});
        if(cursor.getCount()>0){return true;}
        else{return false;}
    }

    //Useless method, keeping it for now!
    public int fetchUserId(String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from accounts where username=?",new String[]{username});
        //I should only have one result!
        //I now want to just return the users ID!
        if(cursor.moveToFirst()) {
            int x = cursor.getInt(0);
            return x;
        }
        else{
            return -1;
        }

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

    public boolean checkAvbService(String servicename){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from services where serviceName=?",new String[]{servicename});
        if (cursor.getCount()>0){
                System.out.println("IM HEREEEEEEEEEEEE");
                System.out.println(cursor.getCount());
                return false;}
        else{ return true;}}



    //Retrieve Entire Cursor Data
    public Cursor retrieveData (String username){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from accounts where username=?",new String[]{username});
    }

    public Cursor retrieveServiceData (String name){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery( "Select * from services where name=?",new String[]{name});
    }





    //Retrieve Data Individually Methods !


    public String getCurrentFirstName(String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from accounts where username=?",new String[]{username});
        if(data.moveToFirst()){
            return data.getString(3);
        }

        return "error";
    }


    public String getCurrentLastName (String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from accounts where username=?",new String[]{username});
        if(data.moveToFirst()){
            return data.getString(4);
        }

        return "error";
    }


    public String getCurrentPhone (String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from accounts where username=?",new String[]{username});
        if(data.moveToFirst()){
            return data.getString(5);
        }
        return "error";
    }

    public String getCurrentAddress (String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from accounts where username=?",new String[]{username});
        if(data.moveToFirst()){
            return data.getString(6);
        }
        return "error";
    }

    public String getCurrentEmail (String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from accounts where username=?",new String[]{username});
        if(data.moveToFirst()){
            return data.getString(7);
        }
        return "error";
    }


    public String getCurrentType (String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from accounts where username=?",new String[]{username});
        if(data.moveToFirst()){
            return data.getString(8);
        }
        return "error";
    }

    public String getCurrentServiceID(String name){
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from services where serviceName=?",new String[]{name});
        if(data.moveToFirst()){
            return data.getString(9);
        }
        return "error";
    }

    public String getCurrentServiceName (String name){
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from services where serviceName=?",new String[]{name});
        if(data.moveToFirst()){
            return data.getString(10);
        }
        return "error";
    }

    public String getCurrentServiceRole (String role){
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from services where serviceName=?",new String[]{role});
        if(data.moveToFirst()){
            return data.getString(11);
        }
        return "error";
    }

    public String getCurrentServiceRate (String rate){
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from services where serviceName=?",new String[]{rate});
        if(data.moveToFirst()){
            return data.getString(12);
        }
        return "error";
    }


}






