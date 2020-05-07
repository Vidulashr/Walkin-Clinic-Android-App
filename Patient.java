package com.uottawa.clinicapp;

public class Patient {

    private String username; //each patient has a username after account creation
    private String password; //each patient has a password after account creation

    //empty constructor
    public Patient() {

    }
    //constructor
    public Patient(String user, String pass) {
        this.username = user;
        this.password = password;
    }

    //set username
    public void setUsername(String user) {
        this.username = user;
    }

    //set password
    public void setPassword(String pass) {
        this.password = pass;
    }

    //get username
    public String getUsername() {
        return this.username;
    }

    //get password
    public String getPassword() {
        return this.password;
    }
}
