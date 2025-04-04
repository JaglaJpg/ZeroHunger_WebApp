package com.example.zerohunger.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.zerohunger.Entity.AppliancesDonation;
import com.example.zerohunger.Entity.ClothingDonation;
import com.example.zerohunger.Entity.FoodBank;
import com.example.zerohunger.Entity.FoodListings;
import com.example.zerohunger.Entity.Users;
import com.example.zerohunger.Service.DonationService;

@Component
public class ListingManager {
	private final DonationService donationService;
	
	@Autowired
	public ListingManager(DonationService donationService) {
		this.donationService = donationService;
	}
	
	//Add other listings when i have them
	public void extractListingInfo(Object listing, Long recipientID) {
		String name = "";
		FoodBank bank = new FoodBank();
		Users donor = new Users();
		if( listing instanceof FoodListings) {
			name = ((FoodListings) listing).getFoodName();
			bank = ((FoodListings) listing).getFoodBank();
			donor = ((FoodListings) listing).getUser();
		} else if (listing instanceof ClothingDonation) {
		    name = ((ClothingDonation) listing).getClothName();
		    bank = ((ClothingDonation) listing).getFoodBank();
		    donor = ((ClothingDonation) listing).getDonor();
		} else if (listing instanceof AppliancesDonation) {
			name = ((AppliancesDonation) listing).getApplianceName();
			bank = ((AppliancesDonation) listing).getFoodBank();
			donor = ((AppliancesDonation) listing).getDonor();
		}
		
		donationService.StartDonation(recipientID, donor, bank, name);
	}
}
