package com.uottawa.clinicapp;

public class Employee {

	private String username; //each employee has a username upon account creation
	private String password; //each employee has a password upon account creation
	
	//empty constructor
	public Employee() {
	
	}
	
	//constructor
	public Employee(String user, String pass) {
		this.username = user;
		this.password = pass;
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