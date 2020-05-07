package com.example.walkinclinic;

abstract public class User {
    private String username;
    private String firstName;
    private String phoneNumber;
    private String address;
    private String lastName;
    private String email;

    public User(){ }

    public User(String user, String name, String last, String phone, String addy, String email){
        this.username = user;
        this.firstName = name;
        this.phoneNumber = phone;
        this.address = addy;
        this.lastName = last;
        this.email = email;
    }


    public String getUsername(){ return username; }
    public String getFirstName(){ return firstName; }
    public String getPhoneNumber(){ return phoneNumber; }
    public String getAddress(){ return address; }
    public String getLastName(){ return lastName; }
    public String getEmail(){ return email; }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //Will be overwritten!
    abstract public String getRole();

    abstract public int getID();
}
