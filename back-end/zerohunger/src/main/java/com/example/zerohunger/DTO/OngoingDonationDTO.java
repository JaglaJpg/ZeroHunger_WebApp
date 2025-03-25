package com.example.zerohunger.DTO;

import java.util.List;

import com.example.zerohunger.Entity.DonationStatus;
import com.example.zerohunger.Entity.FoodBank;
import com.example.zerohunger.Entity.OngoingDonations;

public class OngoingDonationDTO {

	public final Long foodBankID;
	public final DonationStatus status;
	public final String userType;
	public final String address;
	
	public OngoingDonationDTO(FoodBank foodBank, OngoingDonations donation, String userType) {
		this.foodBankID = foodBank.getID();
		this.status = donation.getStatus();
		this.userType = userType;
		this.address = foodBank.getAddress();
	}
	
	public Long getFoodBankID(){
		return this.foodBankID;
	}
	
	public DonationStatus getStatus(){
		return this.status;
	}
	
	public String getUserType() {
		return this.userType;
	}
	
    public String getAddress() {
    	return this.address;
    }
	
}
