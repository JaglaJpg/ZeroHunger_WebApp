package com.example.zerohunger.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zerohunger.DTO.OngoingDonationDTO;
import com.example.zerohunger.Entity.DonationStatus;
import com.example.zerohunger.Entity.FoodBank;
import com.example.zerohunger.Entity.OngoingDonations;
import com.example.zerohunger.Entity.Users;
import com.example.zerohunger.Repository.DonationRepo;
import com.example.zerohunger.Repository.UsersRepo;

@Service
public class DonationService {
	private DonationRepo donationRepo;
	private UsersRepo userRepo;
	
	@Autowired
	public DonationService(DonationRepo donationRepo, UsersRepo userRepo) {
		this.donationRepo = donationRepo;
		this.userRepo = userRepo;
	}
	
	// need to pass in a listing entry here to get the donor Id !!!!!
	public OngoingDonations StartDonation(Long recipientID , FoodBank bank) {
		Users recipient = userRepo.findById(recipientID)
				.orElseThrow(() -> new RuntimeException("Recipient not found"));
		
		LocalDateTime date = LocalDateTime.now();
		OngoingDonations donation = new OngoingDonations();
		donation.setRecipient(recipient);
		donation.setBank(bank);
		donation.setTimestamp(date);
		
		
		return donation;
	}
	
	public Boolean updateStatus(Long donationID) {
		OngoingDonations donation = donationRepo.findById(donationID)
				.orElseThrow(() -> new RuntimeException("Donation not found"));
		
		if(donation.getStatus() == DonationStatus.PENDING) {
			donationRepo.updateStatus(DonationStatus.ONGOING, donationID);
		} else if (donation.getStatus() == DonationStatus.ONGOING) {
			donationRepo.updateStatus(DonationStatus.DELIVERED, donationID);
		}
		 return true;
	}
	
	public List<OngoingDonationDTO> fetchDonations(Long ID) {
		List<OngoingDonationDTO> donations = new ArrayList<OngoingDonationDTO>();
		List<OngoingDonations> list = donationRepo.listDonations(ID);
		
		for(OngoingDonations x : list) {
			String type;
			if(ID == x.getDonor().getUserID()) {
				type = "donor";
			} else {
				type = "recipient";
			}
			
			OngoingDonationDTO dto = new OngoingDonationDTO(x.getBank(), x, type);
			donations.add(dto);
		}
		
		return donations;
	}
}
