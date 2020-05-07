package com.example.walkinclinic;

public class Service{
    private String serviceID;
    private String name;
    private String role;
    private String hourlyRate;

    public Service(){}

    public Service(String n,String r,String h,String s){
        name = n;
        role = r;
        hourlyRate = h;
        serviceID = s;
    }

    public String getHourlyRate() {
        return hourlyRate;
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void setHourlyRate(String hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }
}
