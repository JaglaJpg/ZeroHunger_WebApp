package com.example.zerohunger.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "FoodBanks")
public class FoodBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID

    private String name; // Food bank name
    private double latitude;
    private double longitude;
    private String address; // Optional: If available from API

    @Transient // This field is calculated dynamically, not stored in DB
    private double distance; // Distance from user (filled when queried)
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public void setLat(double Lat) {
    	this.latitude = Lat;
    }
    
    public void setLong(double longitude) {
    	this.longitude = longitude;
    }
    
    public double getLat() {
    	return this.latitude;
    }
    
    public double getLong() {
    	return this.longitude;
    }
    
    
    public void setAddress(String address) {
    	this.address = address;
    }
    
    public Long getID() {
    	return this.id;
    }
    
    public String getAddress() {
    	return this.address;
    }
    
    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
    	this.distance = distance;
    }
}
