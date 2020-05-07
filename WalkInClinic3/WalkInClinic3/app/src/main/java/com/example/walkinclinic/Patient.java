package com.example.walkinclinic;


public class Patient extends User {
    private int patientID;



    public Patient() {
    }

    public Patient(String user, String name, String last, String phone, String addy, String email, int patientID){
        super(user,name,last,phone,addy,email);
        this.patientID = patientID;



    }

    public int getID(){return patientID;}
    public String getRole(){return "Patient";}


    //FUTURE METHODS TO IMPLEMENT!!
    //public void bookAppointment()s
    //checkIn

    //searchClinicType
    //searchClinicAddress
    //searchClinicHours


    public void searchByClinicType(){
        System.out.println("Do");
    }

    public void searchByClinicAddress(){
        System.out.println("Do");
    }


    public void searchByClinicHours(){
        System.out.println("Do");
    }




    //rateExperience

}
