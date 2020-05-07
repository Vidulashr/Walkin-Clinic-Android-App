package com.example.walkinclinic;

public class Admin{
    String username;


    //CAN ONLY BE ONE ADMIN!!! --> I've already added the admin to the database!
    public Admin(){}
    public Admin(String username){this.username = username;}
    public String getRole(){return "Admin";}
    public String getUsername(){return username;}

    //FUTURE METHODS TO IMPLEMENT!
    //createService()
    //deleteUser()

}
