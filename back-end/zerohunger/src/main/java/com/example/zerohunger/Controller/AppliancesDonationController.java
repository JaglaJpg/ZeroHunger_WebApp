package com.example.zerohunger.Controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.zerohunger.DTO.AppliancesDonationRequest;
import com.example.zerohunger.Entity.AppliancesDonation;
import com.example.zerohunger.Entity.FoodBank;
import com.example.zerohunger.Entity.Users;
import com.example.zerohunger.Service.ApplianceDonationService;
import com.example.zerohunger.Service.LocationService;
import com.example.zerohunger.Service.UsersService;
import com.example.zerohunger.Utility.ListingManager;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appliances")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AppliancesDonationController {

	@Autowired private ApplianceDonationService applianceDonationService;
	@Autowired private LocationService locationService;
	@Autowired private UsersService userService;
	@Autowired private ListingManager listingManager;
	private static final Logger logger = LoggerFactory.getLogger(AppliancesDonationController.class);

	@GetMapping("/listings")
	public ResponseEntity<?> getAllDonations() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long userId = principal instanceof Long ? (Long) principal : Long.parseLong(principal.toString());
	        Users user = userService.fetchUser(userId);
			List<AppliancesDonation> donations = applianceDonationService.getAllAvailableDonations(userId);
			
			for(AppliancesDonation x: donations) {
				FoodBank bank = x.getFoodBank();
				double Distance = locationService.calculateDistance(user.getLat(), user.getLong(), bank.getLat(), bank.getLong());
				bank.setDistance(Distance);
			}
			return ResponseEntity.ok(donations);
		} catch (Exception e) {
            logger.error("Error fetching clothing listings: {}", e.getMessage());
            return ResponseEntity.status(500).body("Unable to fetch listings");
        }
	}

	@PostMapping("/donate")
	public ResponseEntity<?> donateApplianceItem(
	@RequestPart ("applianceData") @Valid AppliancesDonationRequest donationRequest, 
	@RequestPart(value="image", required = false) MultipartFile image) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long userId = principal instanceof Long ? (Long) principal : Long.parseLong(principal.toString());
			
			AppliancesDonation donation = applianceDonationService.createDonation(donationRequest, image, userId);
			return ResponseEntity.ok(donation);
		} catch (IOException e) {
			return ResponseEntity.badRequest().body("Failed to upload image: " + e.getMessage());
		} catch(RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@GetMapping("/my-donations")
	public ResponseEntity<?> getUserDonations() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long userId = principal instanceof Long ? (Long) principal : Long.parseLong(principal.toString());
			
			List<AppliancesDonation> donations = applianceDonationService.getUserDonations(userId);
			return ResponseEntity.ok(donations);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/claim/{donationId}")
	public ResponseEntity<?> claimDonation(@PathVariable Long donationId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userId = principal instanceof Long ? (Long) principal : Long.parseLong(principal.toString());
		
		boolean success = applianceDonationService.claimDonation(donationId);
		if (success) {
			AppliancesDonation donation = applianceDonationService.fetchAppliance(donationId);
			listingManager.extractListingInfo(donation, userId);
			return ResponseEntity.ok("Donation claimed successfully");
		} else {
			return ResponseEntity.badRequest().body("Failed to claim donation");
		}
	}

}