package com.example.walkinclinic;

public class Employee extends User {
    private int employeeID;

    public Employee(){}

    public Employee(String user, String name, String last, String phone, String addy, String email, int employeeID) {
        super(user, name, last, phone, addy, email);
        this.employeeID = employeeID;
    }

    public int getID(){return employeeID;}

    public String getRole(){return "Employee";}

    //FUTURE METHODS TO IMPLEMENT
    //addService
    //removeService
    //setServiceRate
    //setWorkingHours


}