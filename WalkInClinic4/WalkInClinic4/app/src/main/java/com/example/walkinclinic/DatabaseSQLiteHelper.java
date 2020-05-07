package com.example.walkinclinic;

//Import required packages!
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.security.NoSuchAlgorithmException;
import java.util.List;

//Made to assist queries and manage database!
public class DatabaseSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SEG21052";
    private static final int DATABASE_VERSION = 32;

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

    //Booking Table
    private static final String TABLE_BOOKINGS = "bookings";
    private static final String COLUMN38 = "userID2";
    private static final String COLUMN39 = "clinicID2";


    //service table
    private static final String TABLE_SERVICE = "services";
    private static final String COLUMN9 = "serviceID";
    private static final String COLUMN10 = "serviceName";
    private static final String COLUMN11 = "serviceRole";
    private static final String COLUMN12 = "serviceRate";


    //clinic table
    private static final String TABLE_CLINIC = "clinics";
    private static final String COLUMN13 = "clinicID";
    private static final String COLUMN14 = "clinicName"; //mandotory
    private static final String COLUMN16 = "clinicAddress"; //mandotory
    private static final String COLUMN17 = "clinicPhone"; //mandotory
    private static final String COLUMN18 = "clinicDescription";
    private static final String COLUMN19 = "clinicLicense";
    private static final String COLUMN20 = "employeeID";
    private static final String COLUMN40 = "waitTime";



    //clinic service relation table
    private static final String TABLE_RELATIONSHIP = "serviceclinic";
    private static final String RELATIONID = "relationID";
    private static final String COLUMN21 = "clinID";
    private static final String COLUMN22 = "servID";
    private static final String COLUMN23 = "servname";
    private static final String COLUMN24 = "servrole";
    private static final String COLUMN25 = "servrate";

    //clinic hour table
    private static final String TABLE_HOUR = "clinichours";
    private static final String COLUMN30 = "hourclinicID";
    private static final String COLUMN31 = "mondayhour";
    private static final String COLUMN32 = "tuesdayhour";
    private static final String COLUMN33 = "wednesdayhour";
    private static final String COLUMN34 = "thursdayhour";
    private static final String COLUMN35 = "fridayhour";
    private static final String COLUMN36 = "saturdayhour";
    private static final String COLUMN37 = "sundayhour";


    //Rating Table
    private static final String TABLE_RATING = "ratings";
    private static final String COLUMN41 = "ratedClinID";
    private static final String COLUMN42 = "ratingScore";
    private static final String COLUMN43 = "ratingDesc";



    public DatabaseSQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create user table
        String createDatabaseQuery = " CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN1 + " TEXT, " +
                COLUMN2 + " TEXT, " +
                COLUMN3 + " TEXT, " +
                COLUMN4 + " TEXT, " +
                COLUMN5 + " TEXT, " +
                COLUMN6 + " TEXT, " +
                COLUMN7 + " TEXT, " +
                COLUMN8 + " TEXT ); ";

        //create service table
        String createServiceDatabase = " CREATE TABLE " + TABLE_SERVICE + " ( " +
                COLUMN9 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN10 + " TEXT, " +
                COLUMN11 + " TEXT, " +
                COLUMN12 + " TEXT ); ";


        String createRatingDatabase = " CREATE TABLE " + TABLE_RATING + " ( " +
                COLUMN41 + " TEXT, " +
                COLUMN42 + " TEXT, " +
                COLUMN43 + " TEXT ); ";


        //create clinic table
        String createClinicDatabase = " CREATE TABLE " + TABLE_CLINIC + " ( " +
                COLUMN13 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN14 + " TEXT, " +
                COLUMN16 + " TEXT, " +
                COLUMN17 + " TEXT, " +
                COLUMN18 + " TEXT, " +
                COLUMN19 + " TEXT, " +
                COLUMN20 + " INTEGER, " +
                COLUMN40 + " TEXT ); ";

        String createRelationDatabase = " CREATE TABLE " + TABLE_RELATIONSHIP + " ( " +
                RELATIONID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN21 + " TEXT, " +
                COLUMN22 + " TEXT, " +
                COLUMN23 + " TEXT, " +
                COLUMN24 + " TEXT, " +
                COLUMN25 + " TEXT); ";

        String createClinicHourDatabase = " CREATE TABLE " + TABLE_HOUR + " ( " +
                COLUMN30 + " TEXT, " +
                COLUMN31 + " TEXT, " +
                COLUMN32 + " TEXT, " +
                COLUMN33 + " TEXT, " +
                COLUMN34 + " TEXT, " +
                COLUMN35 + " TEXT, " +
                COLUMN36 + " TEXT, " +
                COLUMN37 + " TEXT); ";

        String createBookingDatabase = " CREATE TABLE " + TABLE_BOOKINGS + " ( " +
                COLUMN38 + " TEXT, " +
                COLUMN39 + " TEXT); ";
        //Booking Table




        //I want to create my database here!
        db.execSQL(createBookingDatabase);
        db.execSQL(createDatabaseQuery);

        db.execSQL(createServiceDatabase);
        db.execSQL(createClinicDatabase);
        db.execSQL(createRelationDatabase);
        db.execSQL(createClinicHourDatabase);
        db.execSQL(createRatingDatabase);

        //Adding admin access
        String pass = hashPass("5T5ptQ");
        db.execSQL("INSERT INTO accounts (username, password, firstName, lastName, phoneNumber, address, email, type) VALUES('admin','" + pass + "','admin','admin','admin','admin','admin','Admin')");

        //adding test patient
        String patientpass = hashPass("patient");
        db.execSQL("INSERT INTO accounts (username, password, firstName, lastName, phoneNumber, address, email, type) VALUES('testpatient','" + patientpass + "','Testpatient','Testpatient','1234567890','12 woodpark way','testp@gmail.com','P')");



        //Adding 8 services that are always there
        db.execSQL("INSERT INTO services (serviceName,serviceRole,serviceRate) VALUES('Vaccination','Nurse','10')");
        db.execSQL("INSERT INTO services (serviceName,serviceRole,serviceRate) VALUES('Prescription Assistance','Staff','10')");
        db.execSQL("INSERT INTO services (serviceName,serviceRole,serviceRate) VALUES('Walkin','Doctor','10')");
        db.execSQL("INSERT INTO services (serviceName,serviceRole,serviceRate) VALUES('Urgent Care','Doctor','10')");
        db.execSQL("INSERT INTO services (serviceName,serviceRole,serviceRate) VALUES('Diagnostic lab and Imaging','Staff','10')");
        db.execSQL("INSERT INTO services (serviceName,serviceRole,serviceRate) VALUES('Care Coordination','Nurse','10')");
        db.execSQL("INSERT INTO services (serviceName,serviceRole,serviceRate) VALUES('Surgery','Doctor','10')");
        db.execSQL("INSERT INTO services (serviceName,serviceRole,serviceRate) VALUES('Speciality Care','Doctor','10')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DELETE OLD DATABASE
        String deleteDatabase = "drop table if exists " + TABLE_NAME;
        String deleteServiceDatabase = "drop table if exists " + TABLE_SERVICE;
        String deleteClinicDatabase = "drop table if exists " + TABLE_CLINIC;
        String deleteRelationDatabase = "drop table if exists " + TABLE_RELATIONSHIP;
        String deleteClinicHourDatabase = "drop table if exists " + TABLE_HOUR;
        String deleteBookings = "drop table if exists " + TABLE_BOOKINGS;
        String deleteRatingDatabase = "drop table if exists " + TABLE_RATING;


        db.execSQL(deleteDatabase);
        db.execSQL(deleteServiceDatabase);
        db.execSQL(deleteClinicDatabase);
        db.execSQL(deleteRelationDatabase);
        db.execSQL(deleteClinicHourDatabase);
        db.execSQL(deleteBookings);
        db.execSQL(deleteRatingDatabase);
        //Create a new one
        onCreate(db);

    }


    //I want to be able to add data and to check if there's any duplicates!
    public boolean insertNewData(String username, String password, String firstName, String lastName, String phoneNumber, String address, String email, String type) {
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

    //Adding a new clinic into the database for the clinic table
    public boolean insertNewClinic(String name, String address, String phone, String description, Boolean license, String employeeID) {
        //This will allow me to read and write into database!
        SQLiteDatabase db = this.getWritableDatabase();
        //Create an instance of ContentValues
        ContentValues data = new ContentValues();
        //data.put(COLUMN, VALUE);
        data.put(COLUMN13, employeeID);
        data.put(COLUMN14, name);
        data.put(COLUMN16, address);
        data.put(COLUMN17, phone);
        data.put(COLUMN18, description);
        data.put(COLUMN19, license);
        data.put(COLUMN20, employeeID);
        data.put(COLUMN40, "0");

        //Now i want to submit this new data into my database
        long insert = db.insert(TABLE_CLINIC, null, data);

        //If the data WAS NOT successfully added, the value of insert will be -1
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    //Adding the hours for a specific clinic
    public boolean insertClinicHours(String clinicID, String mon, String tues, String wed, String thur, String fri, String sat, String sun) {
        //This will allow me to read and write into database!
        SQLiteDatabase db = this.getWritableDatabase();
        //Create an instance of ContentValues
        ContentValues data = new ContentValues();
        //data.put(COLUMN, VALUE);
        data.put(COLUMN30, clinicID);
        data.put(COLUMN31, mon);
        data.put(COLUMN32, tues);
        data.put(COLUMN33, wed);
        data.put(COLUMN34, thur);
        data.put(COLUMN35, fri);
        data.put(COLUMN36, sat);
        data.put(COLUMN37, sun);

        //Now i want to submit this new data into my database
        long insert = db.insert(TABLE_HOUR, null, data);

        //If the data WAS NOT successfully added, the value of insert will be -1
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    //checks whether clinic hours already submitted
    public boolean validateClinicHours(String clinicID) {
        SQLiteDatabase db = getReadableDatabase();
        //I want to execute the following query
        Cursor cursor = db.rawQuery("Select * from clinichours where hourclinicID=?", new String[]{clinicID});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }


    //Updating clinic hours
    public boolean updateClinicHours(String clinicID, String mon, String tues, String wed, String thur, String fri, String sat, String sun) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        Log.d("DATA PRINTED", clinicID + " : " + mon);
        data.put(COLUMN30, clinicID);
        data.put(COLUMN31, mon);
        data.put(COLUMN32, tues);
        data.put(COLUMN33, wed);
        data.put(COLUMN34, thur);
        data.put(COLUMN35, fri);
        data.put(COLUMN36, sat);
        data.put(COLUMN37, sun);

        long update = db.update(TABLE_HOUR, data, COLUMN30 + "=?", new String[]{clinicID});
        if (update == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String findHourMonday(int clinicID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_HOUR + " WHERE " + COLUMN30 + " = \"" + clinicID + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String result;
        if (cursor.moveToFirst()) {
            result = cursor.getString(1);
            cursor.close();
        } else {
            return null;
        }
        db.close();
        return result;
    }

    public String findHourTuesday(Integer clinicID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_HOUR + " WHERE " + COLUMN30 + " = \"" + clinicID + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String result;
        if (cursor.moveToFirst()) {
            result = cursor.getString(2);
            cursor.close();
        } else {
            return null;
        }
        db.close();
        return result;
    }

    public String findHourWednesday(Integer clinicID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_HOUR + " WHERE " + COLUMN30 + " = \"" + clinicID + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String result;
        if (cursor.moveToFirst()) {
            result = cursor.getString(3);
            cursor.close();
        } else {
            return null;
        }
        db.close();
        return result;
    }

    public String findHourThursday(Integer clinicID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_HOUR + " WHERE " + COLUMN30 + " = \"" + clinicID + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String result;
        if (cursor.moveToFirst()) {
            result = cursor.getString(4);
            cursor.close();
        } else {
            return null;
        }
        db.close();
        return result;
    }

    public String findHourFriday(Integer clinicID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_HOUR + " WHERE " + COLUMN30 + " = \"" + clinicID + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String result;
        if (cursor.moveToFirst()) {
            result = cursor.getString(5);
            cursor.close();
        } else {
            return null;
        }
        db.close();
        return result;
    }

    public String findHourSaturday(Integer clinicID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_HOUR + " WHERE " + COLUMN30 + " = \"" + clinicID + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String result;
        if (cursor.moveToFirst()) {
            result = cursor.getString(6);
            cursor.close();
        } else {
            return null;
        }
        db.close();
        return result;
    }

    public String findHourSunday(Integer clinicID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_HOUR + " WHERE " + COLUMN30 + " = \"" + clinicID + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String result;
        if (cursor.moveToFirst()) {
            result = cursor.getString(7);
            cursor.close();
        } else {
            return null;
        }
        db.close();
        return result;
    }


    //Updating clinic information
    public boolean updateClinic(String name, String add, String phone, String descrip, Boolean license, String employeeID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COLUMN14, name);
        data.put(COLUMN16, add);
        data.put(COLUMN17, phone);
        data.put(COLUMN18, descrip);
        data.put(COLUMN19, license);
        data.put(COLUMN20, employeeID);

        long update = db.update(TABLE_CLINIC, data, COLUMN20 + "=?", new String[]{employeeID});
        if (update == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String findClinicID(int employeeID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String empId = Integer.toString(employeeID);
        Cursor cursor = db.rawQuery("Select * from clinics where employeeID=?", new String[]{empId});
        if (cursor.moveToFirst()) {
            db.close();
            return cursor.getString(0);
        } else {
            db.close();
            return null;
        }
    }

    //DONE
    public String findClinicName(int employeeID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String emID = Integer.toString(employeeID);
        Cursor cursor = db.rawQuery("Select * from clinics where employeeID=?", new String[]{emID});
        if (cursor.moveToFirst()) {
            return cursor.getString(1);
        } else {
            return "Clinic Name Here";
        }
    }

    //get clinic name from clinic ID
    public String findClinicNamefromID(String clinicID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from clinics where clinicID=?", new String[]{clinicID});
        if (cursor.moveToFirst()) {
            return cursor.getString(1);
        } else {
            return "Clinic Name Here";
        }
    }

    public String findClinicAddress(Integer employeeID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_CLINIC + " WHERE " + COLUMN20 + " = \"" + employeeID + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String result;
        if (cursor.moveToFirst()) {
            result = cursor.getString(2);
            cursor.close();
        } else {
            return "Clinic Address Here";
        }
        db.close();
        return result;
    }

    public String findClinicPhone(Integer employeeID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_CLINIC + " WHERE " + COLUMN20 + " = \"" + employeeID + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String result;
        if (cursor.moveToFirst()) {
            result = cursor.getString(3);
            cursor.close();
        } else {
            return "Clinic Phone Here";
        }
        db.close();
        return result;
    }


    public String findClinicDescription(Integer employeeID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_CLINIC + " WHERE " + COLUMN20 + " = \"" + employeeID + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String result;
        if (cursor.moveToFirst()) {
            result = cursor.getString(4);
            cursor.close();
        } else {
            return "";
        }
        db.close();
        return result;
    }

    public Boolean findClinicLicensed(Integer employeeID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_CLINIC + " WHERE " + COLUMN20 + " = \"" + employeeID + "\"";
        Cursor cursor = db.rawQuery(query, null);
        Boolean result;
        if (cursor.moveToFirst()) {
            result = Boolean.valueOf(cursor.getString(5));
            cursor.close();
        } else {
            return false;
        }
        db.close();
        return result;
    }

    //Adding relationship between clinic and service
    public boolean insertNewRelation(String clinicID, String serviceID, String servname, String servrole, String servrate) {
        //This will allow me to read and write into database!
        SQLiteDatabase db = this.getWritableDatabase();
        //Create an instance of ContentValues
        ContentValues data = new ContentValues();
        //data.put(COLUMN, VALUE);
        data.put(COLUMN21, clinicID);
        data.put(COLUMN22, serviceID);
        data.put(COLUMN23, servname);
        data.put(COLUMN24, servrole);
        data.put(COLUMN25, servrate);

        //Now i want to submit this new data into my database
        long insert = db.insert(TABLE_RELATIONSHIP, null, data);

        //If the data WAS NOT successfully added, the value of insert will be -1
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String getRelationID(String clinicID, String serviceID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_RELATIONSHIP;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    if ((cursor.getString(1).equals(clinicID)) && (cursor.getString(2).equals(serviceID))) {
                        return cursor.getString(0);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return null;
    }

    //deleting a service in a clinic
    public boolean deleteRelation(String relationID) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_RELATIONSHIP + " WHERE " + RELATIONID + " = \"" + relationID + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            String idStr = cursor.getString(0);
            db.delete(TABLE_RELATIONSHIP, RELATIONID + " =" + idStr, null);
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    //checks whether relationship already exists
    public boolean validateClinicService(String relationID) {
        SQLiteDatabase db = getReadableDatabase();
        //I want to execute the following query
        String query = "Select * FROM " + TABLE_RELATIONSHIP + " WHERE " + RELATIONID + " = \"" + relationID + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public User findUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN1 + " = \"" + username + "\"";
        Cursor cursor = db.rawQuery(query, null);
        User user;
        if (cursor.moveToFirst()) {
            if (cursor.getString(8).equals("P")) {
                user = new Patient();
                user.setUsername(cursor.getString(1));
                user.setFirstName(cursor.getString(3));
                user.setLastName(cursor.getString(4));
                user.setPhoneNumber(cursor.getString(5));
                user.setAddress(cursor.getString(6));
                user.setEmail(cursor.getString(7));
                cursor.close();
            } else {
                user = new Employee();
                user.setUsername(cursor.getString(1));
                user.setFirstName(cursor.getString(3));
                user.setLastName(cursor.getString(4));
                user.setPhoneNumber(cursor.getString(5));
                user.setAddress(cursor.getString(6));
                user.setEmail(cursor.getString(7));
                cursor.close();
            }
        } else {
            user = null;
        }
        db.close();
        return user;
    }

    public boolean deleteUser(String username) {
        //Cannot try to remove admin
        if (username.equals("admin")) {
            return false;
        }

        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN1 + " = \"" + username + "\"";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            if(cursor.getString(8).equals("P")){
                String idStr = cursor.getString(0);
                db.delete(TABLE_NAME, COLUMN0 + " =" + idStr, null);
                db.delete(TABLE_BOOKINGS,COLUMN38 +" ="+ idStr, null);
                result = true;
        }}

        if(cursor.moveToFirst()){

            if(cursor.getString(8 ).equals("E")){
                //I want to delete everything else associated with this employee
                String idStr = cursor.getString(0);
                db.delete(TABLE_NAME,COLUMN0 +" ="+ idStr, null);
                db.delete(TABLE_BOOKINGS,COLUMN39 +" ="+ idStr, null);
                db.delete(TABLE_HOUR,COLUMN30 +" ="+ idStr, null);
                db.delete(TABLE_CLINIC,COLUMN13 +" ="+ idStr, null);
                db.delete(TABLE_RELATIONSHIP,COLUMN21 +" ="+ idStr, null);
                db.delete(TABLE_RELATIONSHIP,COLUMN21 +" ="+ idStr, null);
                db.delete(TABLE_RATING,COLUMN41+" ="+idStr,null);
                return true;
            }
        }
        db.close();
        return result;
    }


    //DONE
    public boolean insertService(String name, String role, String rate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COLUMN10, name);
        data.put(COLUMN11, role);
        data.put(COLUMN12, rate);
        long insert = db.insert(TABLE_SERVICE, null, data);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    //DONE
    public boolean deleteService(String servicename) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_SERVICE + " WHERE " + COLUMN10 + " = \"" + servicename + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            String idStr = cursor.getString(0);
            db.delete(TABLE_SERVICE, COLUMN9 + " =" + idStr, null);
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    //DONE
    public Service findService(String servicename) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * FROM " + TABLE_SERVICE + " WHERE " + COLUMN10 + " = \"" + servicename + "\"";
        Cursor cursor = db.rawQuery(query, null);
        Service service = new Service();
        if (cursor.moveToFirst()) {
            service.setServiceID(cursor.getString(0));
            service.setName(cursor.getString(1));
            service.setRole(cursor.getString(2));
            service.setHourlyRate(cursor.getString(3));
            cursor.close();
        } else {
            service = null;
        }
        db.close();
        return service;
    }

    //DONE
    public boolean updateService(String name, String role, String rate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put(COLUMN10, name);
        data.put(COLUMN11, role);
        data.put(COLUMN12, rate);
        long update = db.update(TABLE_SERVICE, data, COLUMN10 + "=?", new String[]{name});
        if (update == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<String> getAllClinics() {
        ArrayList<String> clinics = new ArrayList<String>();
        String query = "SELECT * FROM " + TABLE_CLINIC;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String clinic = "";
                    clinic += cursor.getString(1);
                    clinic += ":";
                    clinic += cursor.getString(6);
                    clinics.add(clinic);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return clinics;
    }


    //LIST OF SERVICES IN ORDER TO VIEW
    public ArrayList<Service> getAllServices() {
        ArrayList<Service> services = new ArrayList<Service>();
        String query = "SELECT * FROM " + TABLE_SERVICE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
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

    //LIST OF SERVICES IN ORDER TO VIEW FOR SPECIFIC CLINIC
    public ArrayList<Service> getAllServicesforClinic(String clinicID) {
        ArrayList<Service> services = new ArrayList<Service>();
        String query = "SELECT * FROM " + TABLE_RELATIONSHIP;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getString(1).equals(clinicID)) {
                        Service service = new Service();
                        service.setServiceID(cursor.getString(2));
                        service.setName(cursor.getString(3));
                        service.setRole(cursor.getString(4));
                        service.setHourlyRate(cursor.getString(5));
                        services.add(service);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return services;
    }

    //clinic service relation table

    //LIST OF ALL THE USERS TO VIEW, ONLY SHOWS PATIENTS AND EMPLOYEES NOT THE ADMIN
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<User>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    //if admin, dont add to list
                    if (cursor.getString(1).equals("admin")) {
                    }
                    //else add to list of users
                    else {
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
                        else if (cursor.getString(8).equals("E")) {
                            Employee user = new Employee();
                            user.setUsername(cursor.getString(1));
                            user.setFirstName(cursor.getString(3));
                            user.setLastName(cursor.getString(4));
                            user.setPhoneNumber(cursor.getString(5));
                            user.setAddress(cursor.getString(6));
                            user.setEmail(cursor.getString(7));
                            users.add(user);
                        }
                    }

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return users;
    }


    //METHODS THAT WILL CHECK IF  E-MAIL OR USERNAME & PHONE NUMBER ARE AVAILABLE!

    public boolean checkAvbEmail(String email) {
        //We just want to read from database!
        SQLiteDatabase db = getReadableDatabase();
        //The Query I want to execute!
        Cursor cursor = db.rawQuery("Select * from accounts where email=?", new String[]{email});
        //This will check if there was a result, it will return false, meaning it's already in use!
        if (cursor.getCount() > 0) {
            return false;
        }
        //It will return true if that e-mail is available
        else {
            return true;
        }
    }

    public boolean checkAvbPhone(String phoneNum) {
        //We just want to read from database!
        SQLiteDatabase db = getReadableDatabase();
        //The Query I want to execute!
        Cursor cursor = db.rawQuery("Select * from accounts where username=?", new String[]{phoneNum});
        //This will check if there was a result, it will return false, meaning it's already in use!
        if (cursor.getCount() > 0) {
            return false;
        }
        //It will return true if that e-mail is available
        else {
            return true;
        }
    }

    public boolean checkAvbUser(String user) {
        //We just want to read from database!
        SQLiteDatabase db = getReadableDatabase();
        //The Query I want to execute!
        Cursor cursor = db.rawQuery("Select * from accounts where username=?", new String[]{user});
        //This will check if there was a result, it will return false, meaning it's already in use!
        if (cursor.getCount() > 0) {
            return false;
        }
        //It will return true if that e-mail is available
        else {
            return true;
        }
    }


    //Verify If Proper LogIn!
    public boolean validateLogIn(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        //I want to execute the following query
        Cursor cursor = db.rawQuery("Select * from accounts where username=? and password=?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //Useless method, keeping it for now!
    public int fetchUserId(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from accounts where username=?", new String[]{username});
        //I should only have one result!
        //I now want to just return the users ID!
        if (cursor.moveToFirst()) {
            int x = cursor.getInt(0);
            return x;
        } else {
            return -1;
        }

    }

    public String fetchUserName(String userID){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from accounts where ID=?", new String[]{userID});
        //I should only have one result!
        //I now want to just return the users ID!
        if (cursor.moveToFirst()) {
            String x = cursor.getString(1);
            return x;
        } else {
            return "error"; }

    }

    public String hashPass(String unhashedPass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(unhashedPass.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            return String.format("%064x", new BigInteger(1, digest));
        } catch (NoSuchAlgorithmException e) {
            return "error";
        }

    }

    public boolean checkAvbService(String servicename) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from services where serviceName=?", new String[]{servicename});
        if (cursor.getCount() > 0) {
            System.out.println(cursor.getCount());
            return false;
        } else {
            return true;
        }
    }

    public boolean checkAvdClinicEmployee(String empID) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from clinics where employeeID=?", new String[]{empID});
        if (cursor.getCount() > 0) {
            System.out.println(cursor.getCount());
            return false;
        } else {
            return true;
        }
    }


    public boolean checkAvbClinic(String clinicname) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from clinics where clinicName=?", new String[]{clinicname});
        if (cursor.getCount() == 0) {
            System.out.println(cursor.getCount());
            return true;
        } else {
            return false;
        }
    }


    //Retrieve Entire Cursor Data
    public Cursor retrieveData(String username) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from accounts where username=?", new String[]{username});
    }

    public Cursor retrieveServiceData(String name) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from services where name=?", new String[]{name});
    }


    //Retrieve Clinics
    //First Methods Will Be Searching by Addrss
    //Second Method Will Be By Working Hours
    //Third Method Will Be By Types of Services

    public ArrayList<ArrayList<String>> retrieveClinicDataByAddy(String address) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ArrayList<String>> finalReturn = new ArrayList<ArrayList<String>>();
        Cursor dataRetrieved = db.rawQuery("Select * from clinics where clinicAddress=?", new String[]{address});

        //I '
        //Now that I have the cursor, I want to return a list of all clinics
        //Let us first check if any data at all was retrieved
        if (dataRetrieved != null) {
            if (dataRetrieved.moveToFirst()) {
                do {
                    ArrayList<String> returnVal = new ArrayList<String>();
                    returnVal.add(dataRetrieved.getString(1));
                    returnVal.add(dataRetrieved.getString(2));
                    returnVal.add(dataRetrieved.getString(3));
                    String clinicID = dataRetrieved.getString(0);
                    //Get the wait time
                    String waitTime = this.setNewWaitTime(clinicID);
                    String rating = this.getClinicScoreRating(clinicID);
                    //Add the wait time
                    returnVal.add(waitTime);
                    returnVal.add(rating);
                    System.out.println(waitTime);
                    finalReturn.add(returnVal);
                }

                while (dataRetrieved.moveToNext());
            }
            dataRetrieved.close();

        }
        db.close();
        return finalReturn;
    }


    public ArrayList<ArrayList<String>> retrieveClinicDataByService(String service) {
        //I have a service
        //Make an array that will first carry the data of a single clinic
        ArrayList<ArrayList<String>> retrieveData = new ArrayList<ArrayList<String>>();
        //Make an instance of my DataBase
        SQLiteDatabase db = getReadableDatabase();
        //Search in ServiceClinic for all clinics that match that service name
        Cursor dataRetrieved = db.rawQuery("Select * from serviceclinic where servname=?", new String[]{service});
        //Now that I have all of the data retrieved. I now want to save search for all clinic
        System.out.println("total " + dataRetrieved.getCount()+" total");
        //Now that I have this data
        if(dataRetrieved.moveToFirst()){
            for (int i = 0 ; i < dataRetrieved.getCount() ; i++){
                //I want to search for every clinic that has that data
                String currentClidID = dataRetrieved.getString(1);
                Cursor clinicRetrieve = db.rawQuery("Select * from clinics where clinicID=?", new String[]{currentClidID});
                //Now that I have retrieved the data I wanted
                //Store data into arrayList
                if(clinicRetrieve.moveToFirst()){
                    ArrayList<String> currClinic = new ArrayList<String>();
                    currClinic.add(clinicRetrieve.getString(1));
                    currClinic.add(clinicRetrieve.getString(2));
                    currClinic.add(clinicRetrieve.getString(3));
                    //Get the clinics ID
                    String clinicID = clinicRetrieve.getString(0);
                    //Find the ET
                    String estimatedTime = this.setNewWaitTime(clinicID);
                    //Find the rating
                    String rating = this.getClinicScoreRating(clinicID);
                    currClinic.add(estimatedTime);
                    currClinic.add(rating);
                    //Add this new clinic info into the ArrayList
                    retrieveData.add(currClinic);
                    //Move to the next data in data retrieved
                    dataRetrieved.moveToNext();
                    System.out.println(currClinic);}
            }
        }
        return retrieveData;
    }





    //Method only checks which clinics are open on the day that is inputted if no
    public ArrayList<ArrayList<String>> retrieveClinicDataByHours(ArrayList<String> info) {
        ArrayList<ArrayList<String>> toReturn = new ArrayList<ArrayList<String>>();
        String day = info.get(0);
        String startHour = info.get(1); //unused atm
        String startAP = info.get(2); //unused atm
        String endHour = info.get(3); //unused atm
        String endAP = info.get(4); //unused atm

        SQLiteDatabase db = getReadableDatabase();
        //Gets all clinics open on that day
        ArrayList<String> currClinic = new ArrayList<String>();

        if (day.equals("Monday")) {
            Cursor clinicRetrieve = db.rawQuery("Select * from clinichours where mondayhour!=?", new String[]{"Not Open"});
            if (clinicRetrieve != null) {
                if (clinicRetrieve.moveToFirst()) {
                    do {
                        String clinicID = clinicRetrieve.getString(0);
                        currClinic.add(clinicID);
                    }
                    while (clinicRetrieve.moveToNext());
                }
                clinicRetrieve.close();
            }
        } else if (day.equals("Tuesday")) {
            Cursor clinicRetrieve = db.rawQuery("Select * from clinichours where tuesdayhour!=?", new String[]{"Not Open"});
            if (clinicRetrieve != null) {
                if (clinicRetrieve.moveToFirst()) {
                    do {
                        String clinicID = clinicRetrieve.getString(0);
                        currClinic.add(clinicID);
                    }
                    while (clinicRetrieve.moveToNext());
                }
                clinicRetrieve.close();
            }
        } else if (day.equals("Wednesday")) {
            Cursor clinicRetrieve = db.rawQuery("Select * from clinichours where wednesdayhour!=?", new String[]{"Not Open"});
            if (clinicRetrieve != null) {
                if (clinicRetrieve.moveToFirst()) {
                    do {
                        String clinicID = clinicRetrieve.getString(0);
                        currClinic.add(clinicID);
                    }
                    while (clinicRetrieve.moveToNext());
                }
                clinicRetrieve.close();
            }
        } else if (day.equals("Thursday")) {
            Cursor clinicRetrieve = db.rawQuery("Select * from clinichours where thursdayhour!=?", new String[]{"Not Open"});
            if (clinicRetrieve != null) {
                if (clinicRetrieve.moveToFirst()) {
                    do {
                        String clinicID = clinicRetrieve.getString(0);
                        currClinic.add(clinicID);
                    }
                    while (clinicRetrieve.moveToNext());
                }
                clinicRetrieve.close();
            }
        }
        if (day.equals("Friday")) {
            Cursor clinicRetrieve = db.rawQuery("Select * from clinichours where fridayhour!=?", new String[]{"Not Open"});
            if (clinicRetrieve != null) {
                if (clinicRetrieve.moveToFirst()) {
                    do {
                        String clinicID = clinicRetrieve.getString(0);
                        currClinic.add(clinicID);
                    }
                    while (clinicRetrieve.moveToNext());
                }
                clinicRetrieve.close();
            }
        } else if (day.equals("Saturday")) {
            Cursor clinicRetrieve = db.rawQuery("Select * from clinichours where saturdayhour!=?", new String[]{"Not Open"});
            if (clinicRetrieve != null) {
                if (clinicRetrieve.moveToFirst()) {
                    do {
                        String clinicID = clinicRetrieve.getString(0);
                        currClinic.add(clinicID);
                    }
                    while (clinicRetrieve.moveToNext());
                }
                clinicRetrieve.close();
            }
        } else {
            Cursor clinicRetrieve = db.rawQuery("Select * from clinichours where sundayhour!=?", new String[]{"Not Open"});
            if (clinicRetrieve != null) {
                if (clinicRetrieve.moveToFirst()) {
                    do {
                        String clinicID = clinicRetrieve.getString(0);
                        currClinic.add(clinicID);
                    }
                    while (clinicRetrieve.moveToNext());
                }
                clinicRetrieve.close();
            }
        }

        //uses list of clinicIDS to get their full info
        for (int i = 0; i < currClinic.size(); i++) {
            Cursor dataRetrieve = db.rawQuery("Select * from clinics where clinicID=?", new String[]{currClinic.get(i)});
            if (dataRetrieve != null) {
                if (dataRetrieve.moveToFirst()) {
                    do {
                        ArrayList<String> clinic = new ArrayList<String>();
                        clinic.add(dataRetrieve.getString(1));
                        clinic.add(dataRetrieve.getString(2));
                        clinic.add(dataRetrieve.getString(3));
                        //Get the clinics ID
                        String clinicID = dataRetrieve.getString(0);
                        //Find the ET
                        String estimatedTime = this.setNewWaitTime(clinicID);
                        //Find the rating
                        String rating = this.getClinicScoreRating(clinicID);
                        clinic.add(estimatedTime);
                        clinic.add(rating);
                        //Add this new clinic info into the ArrayList
                        toReturn.add(clinic);
                        //Move to the next data in data retrieved
                        dataRetrieve.moveToNext();
                    }
                    while (dataRetrieve.moveToNext());
                }
                dataRetrieve.close();
            }
        }
        return toReturn;
    }











        public Cursor retrieveByService(String serviceName) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM serviceclinic where servname = ?", new String[]{serviceName});
    }


    //Retrieve Data Individually Methods !


    public String getCurrentFirstName(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from accounts where username=?", new String[]{username});
        if (data.moveToFirst()) {
            return data.getString(3);
        }

        return "error";
    }


    public String getCurrentLastName(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from accounts where username=?", new String[]{username});
        if (data.moveToFirst()) {
            return data.getString(4);
        }

        return "error";
    }


    public String getCurrentPhone(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from accounts where username=?", new String[]{username});
        if (data.moveToFirst()) {
            return data.getString(5);
        }
        return "error";
    }

    public String getCurrentAddress(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from accounts where username=?", new String[]{username});
        if (data.moveToFirst()) {
            return data.getString(6);
        }
        return "error";
    }

    public String getCurrentEmail(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from accounts where username=?", new String[]{username});
        if (data.moveToFirst()) {
            return data.getString(7);
        }
        return "error";
    }


    public String getCurrentType(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from accounts where username=?", new String[]{username});
        if (data.moveToFirst()) {
            return data.getString(8);
        }
        return "error";
    }

    public String getCurrentServiceID(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from services where serviceName=?", new String[]{name});
        if (data.moveToFirst()) {
            return data.getString(0);
        }
        return "error";
    }

    public String getCurrentServiceName(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from services where serviceName=?", new String[]{name});
        if (data.moveToFirst()) {
            return data.getString(10);
        }
        return "error";
    }

    public String getCurrentServiceRole(String role) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from services where serviceName=?", new String[]{role});
        if (data.moveToFirst()) {
            return data.getString(11);
        }
        return "error";
    }

    public String getCurrentServiceRate(String rate) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from services where serviceName=?", new String[]{rate});
        if (data.moveToFirst()) {
            return data.getString(12);
        }
        return "error";
    }

    public String getClinicID(String clinicName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from clinics where clinicName = ?", new String[]{clinicName});
        if (data.moveToFirst()) {
            return data.getString(0);
        } else {
            return "error";
        }
    }



    public String getClinicName(String clinicID) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor data = db.rawQuery("Select * from clinics where clinicID = ?", new String[]{clinicID});
        if (data.moveToFirst()) {
            return data.getString(1);
        } else {
            return "error";
        }
    }


    //Add a booking method
    //Remove a booking
    //Calculated ET

    public boolean bookUser(String patientID, String clinicID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newBooking = new ContentValues();
        //I want to place the data for the newBooking
        newBooking.put(COLUMN38, patientID);
        newBooking.put(COLUMN39, clinicID);
        if (TABLE_BOOKINGS == null) {
            System.out.println("Your Table Doesn't exist");
        }
        long insert = db.insert(TABLE_BOOKINGS, null, newBooking);
        System.out.println("Hello " + patientID + " : " + clinicID);
        if (insert == -1) {
            return false;
        }

        return true;
    }


    public void removeBooking(String patientID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_BOOKINGS + " WHERE " + COLUMN38 + "='" + patientID + "'");
    }

    public boolean CheckIfBooked(String patientID) {
        SQLiteDatabase db = this.getReadableDatabase();
        //I want to check if there's enough currently booked
        Cursor data = db.rawQuery("Select * from bookings where userID2 = ?", new String[]{patientID});
        if (data.getCount() == 0) {
            return false;
        }
        return true;
    }

    public String getClinicBooked(String patientID){
        SQLiteDatabase db = this.getReadableDatabase();
        //I want to check if there's enough currently booked
        Cursor data = db.rawQuery("Select * from bookings where userID2 = ?", new String[]{patientID});
        System.out.println(data.getCount());
        if (data.moveToFirst()){
            return data.getString(1);
        }
        return "erorr";
    }




    public String setNewWaitTime(String clinicID) {
        SQLiteDatabase db = this.getReadableDatabase();
        //I want to check if there's enough currently booked
        Cursor data = db.rawQuery("Select * from bookings where clinicID2 = ?", new String[]{clinicID});
        int totalWaitTime = data.getCount() * 15;
        String returnVal = Integer.toString(totalWaitTime);
        System.out.println("HELOOOO = "+clinicID + " : " +  totalWaitTime);
        return returnVal;
    }


    //Rating Database Methods
    public boolean insertNewRating(String clinicID,String rating,String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newRating = new ContentValues();
        newRating.put(COLUMN41,clinicID);
        newRating.put(COLUMN42,rating);
        newRating.put(COLUMN43,description);
        long success  = db.insert(TABLE_RATING,null,newRating);
        if(success == -1 ) {return false;}
        return true;

    }

    //Get the current clinics score
    public String getClinicScoreRating(String clinicID){
        double totalScore = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        //Write my sql query
        Cursor data = db.rawQuery("Select * from ratings where ratedClinID=?", new String[]{clinicID});
        if(data.getCount() == 0 ){return "-1";}
        try{
            while(data.moveToNext()){
                String currRatingRow = data.getString(1);
                //Convert it to a float
                double currRatingVal = Double.parseDouble(currRatingRow);
                totalScore = totalScore + currRatingVal;
            }
        }
        finally {
            data.close();
        }

        double average = totalScore / data.getCount();
        return String.format("%.2f", average);
    }
}