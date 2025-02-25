package com.example.zerohunger.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

//Database table for each users stats for the dashboard
//each attribute is a field in the table
//Entity tag makes it a table
@Entity
@Table(name = "stats")
public class userStats {
	
	//Id tag marks it as Primary key
	@Id
	private Long userID;
	
	//Relation to Users, userID is primary and foreign key
	@OneToOne
	@JoinColumn(name = "userID", referencedColumnName = "userID")
	private Users user;
	
	private int totalWasted;
	private int totalSaved;
	private int wastedLastWeek;
	private int wastedLastMonth;
	//Add dates and time of wasted food
	
	//Getters and Setters
	public Long getUserID() {
		return userID;
	}
	
	public void setUserID(Users userID) {
		this.user = userID;
	}
	
	public int getTotalWasted() {
		return totalWasted;
	}
	
	public void addTotalWasted() {
		this.totalWasted++;
	}
	
	public int getTotalSaved() {
		return totalSaved;
	}
	
	public void addTotalSaved() {
		this.totalSaved++;
	}
	
	public int getWastedLastWeek() {
		return wastedLastWeek;
	}
	
	public void setWastedLastWeek(int waste) {
		this.wastedLastWeek = waste;
	}
	
	public int getWastedLastMonth() {
		return wastedLastMonth;
	}
	
	public void setWastedLastMonth(int waste) {
		this.wastedLastMonth = waste;
	}

	public void setTotalSaved(int i) {
		totalSaved = i;
		
	}
	
	public void setTotalWasted(int i) {
		totalWasted = i;
	}
	
}
