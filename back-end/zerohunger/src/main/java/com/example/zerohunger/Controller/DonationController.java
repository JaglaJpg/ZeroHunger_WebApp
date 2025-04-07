package com.example.zerohunger.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.zerohunger.DTO.BankOptionsDTO;
import com.example.zerohunger.DTO.DonationStatusUpdateDTO;
import com.example.zerohunger.Entity.DonationStatus;
import com.example.zerohunger.Service.DonationService;

//end points for Tracker page and for fetching foodbanks to select as pickup location

@RestController
@RequestMapping("/donations")
public class DonationController {
	private DonationService donationService;
	
	@Autowired
	public DonationController(DonationService donationService) {
		this.donationService = donationService;
	}
	
	@GetMapping("/bankOptions")
	public ResponseEntity<?> fetchBankOptions() {
	    //Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //fetch userID from security filter
		long userId = 1111;
	    try {
	        List<BankOptionsDTO> options = donationService.fetchBanks(userId);//gathers 10 closest foodbanks to the user
	        return ResponseEntity.ok(options);
	    } catch (Exception e) {
	        return ResponseEntity.status(404).body("No food banks found");
	    }
	}
	
	@GetMapping("/fetchDonations")
	public ResponseEntity<?> fetchUserDonations() {
	    // For testing — replace with real userId if needed
	    long userId = 1212;

	    try {
	        return ResponseEntity.ok(donationService.fetchDonations(userId)); //returns ongoing donations linked to the user
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Failed to fetch donations: " + e.getMessage());
	    }
	}
	
	@PutMapping("/claim/{ID}")
	public ResponseEntity<?> finishDonation(@PathVariable Long ID){
		donationService.finishDonation(ID);
		
		return ResponseEntity.ok(null);
	}
	
	@PatchMapping("/updateStatus")
	public ResponseEntity<?> updateStatus(@RequestBody DonationStatusUpdateDTO request){ //recieves id of donation to update and the new status
	    donationService.updateStatus(request.getDonationId(), request.getStatus()); //applies the update

	    Map<String, Object> response = new HashMap<>();
	    response.put("donationId", request.getDonationId());
	    response.put("newStatus", request.getStatus());
	    response.put("message", "Status updated successfully");

	    return ResponseEntity.ok(response);
	}
}
