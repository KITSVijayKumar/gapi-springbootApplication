package com.pim.migration.configuration;

public class Customer {
	
	public Customer(String firstName,String lastName,String position,String location){
		this.firstName=firstName;
		this.lastName=lastName;
		this.position=position;
		this.location=location;
	}
	
	String firstName;
	String lastName;
	String position;
	String location;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}
