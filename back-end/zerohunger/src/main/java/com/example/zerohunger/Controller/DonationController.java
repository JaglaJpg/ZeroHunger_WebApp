package com.example.zerohunger.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.zerohunger.DTO.BankOptionsDTO;
import com.example.zerohunger.Service.DonationService;

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
	    Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    try {
	        List<BankOptionsDTO> options = donationService.fetchBanks(userId);
	        return ResponseEntity.ok(options);
	    } catch (Exception e) {
	        return ResponseEntity.status(404).body("No food banks found");
	    }
	}
	
	@GetMapping("/fetchDonations")
	public ResponseEntity<?> fetchUserDonations() {
	    // For testing — replace with real userId if needed
	    long userId = 3432;

	    try {
	        return ResponseEntity.ok(donationService.fetchDonations(userId));
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Failed to fetch donations: " + e.getMessage());
	    }
	}
}
