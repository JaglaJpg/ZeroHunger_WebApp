package com.example.zerohunger.Entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
//Database table for Users
//each attribute is a field in the table
//Entity tag makes it a table
@Entity
@Table(name = "users")
public class Users {
	//Id tag marks it as Primary key, generatedValue is a generated incremental Long type number (Between -2^63 to 2^63 -1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userID;
	
	private String firstName;
	private String lastName;
	private LocalDate DOB;
	@Column(nullable = false, unique = true)
	private String email;
	private String password;
	private String address;
	private double longitude;
	private double latitude;
	
	//Relation to userStats table, Primary key of users is also primary AND foreign key for userStats
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private userStats stats;
    
    
    //Getters and Setters
    public userStats getStats() {
        return stats;
    }

    public void setStats(userStats stats) {
        this.stats = stats;
    }

    public Long getUserID() {
    	return userID;
    }
    
    public void setUserID(Long userID) {
    	this.userID = userID;
    }
    
    public String getFirstName() {
    	return firstName;
    }
    
    public void setFirstName(String name) {
    	this.firstName = name;
    }
    
    public String getLastName() {
    	return lastName;
    }
    
    public void setLastName(String name) {
    	this.lastName = name;
    }
    
    
    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public LocalDate getDOB() {
    	return DOB;
    }
    
    public void setDOB(LocalDate dob) {
    	this.DOB = dob;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    public String getAddress() {
    	return this.address;
    }
    
    public void setAddress(String address) {
    	this.address = address;
    }
    
    public double getLat() {
    	return this.latitude;
    }
    
    public void setLat(double lat) {
    	this.latitude = lat;
    }
    
    public double getLong() {
    	return this.longitude;
    }
    
    public void setLong(double lon) {
    	this.longitude = lon;
    }
   
}
