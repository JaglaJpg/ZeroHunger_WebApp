package com.example.zerohunger.Entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//Database table for food logged for personal reasons
//each attribute is a field in the table
//Entity tag makes it a table
@Entity
@Table(name = "food")
public class FoodItems {
	//Id tag marks it as Primary key, generatedValue is a generated incremental Long type number (Between -2^63 to 2^63 -1)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long foodID;
	
	//Foreign key to link each food to a user, the foreign key refers to the user table column "userID" (aka users primary key).
	@ManyToOne
	@JoinColumn(name = "userID")
	private Users user;
	//Foreign key is passed in as an object of type Users containing said userID
	
	private String foodName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate expiration;
	
	@Enumerated(EnumType.STRING)
	private FoodStatus status;
	
	@Column(nullable = true)
	private LocalDate statusChanged;
	
	
	//Getters and setters for each field
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
	
    public Long getFoodID() {
    	return foodID;
    }
    
    public void setFoodID(Long foodID) {
    	this.foodID = foodID;
    }
    
    public String getFoodName() {
    	return foodName;
    }
    
    public void setFoodName(String foodName) {
    	this.foodName = foodName;
    }
    
    public LocalDate getExp() {
    	return expiration;
    }
    
    public void setExp(LocalDate expiration) {
    	this.expiration = expiration;
    }
    
    public FoodStatus getStatus() {
    	return status;
    }
    
    public void setStatus(FoodStatus status) {
    	this.status = status;
    }
    
    public LocalDate getDateChanged() {
    	return statusChanged;
    }
    
    public void setDateChanged(LocalDate statusChanged) {
    	this.statusChanged = statusChanged;
    }

}
