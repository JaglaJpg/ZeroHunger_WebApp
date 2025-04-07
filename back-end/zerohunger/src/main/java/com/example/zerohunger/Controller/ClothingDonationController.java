package com.example.zerohunger.Controller;

import com.example.zerohunger.DTO.ClothingDonationRequest;
import com.example.zerohunger.Entity.ClothingDonation;
import com.example.zerohunger.Entity.FoodBank;
import com.example.zerohunger.Entity.Users;
import com.example.zerohunger.Service.ClothingDonationService;
import com.example.zerohunger.Service.LocationService;
import com.example.zerohunger.Service.UsersService;
import com.example.zerohunger.Utility.ListingManager;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cloth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ClothingDonationController {

    private static final Logger logger = LoggerFactory.getLogger(ClothingDonationController.class);

    @Autowired private ClothingDonationService clothingDonationService;
    @Autowired private UsersService userService;
    @Autowired private LocationService locationService;
    @Autowired private ListingManager listingManager;

    @PostMapping("/donate")
    public ResponseEntity<?> donateClothingItem(
            @RequestPart("clothData") @Valid ClothingDonationRequest donationRequest,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        try {
            //Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long userId = 1212;
        	
            ClothingDonation donation = clothingDonationService.createDonation(donationRequest, image, userId);
            return ResponseEntity.ok(donation);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Image upload failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/listings")
    public ResponseEntity<?> getAllAvailableClothing() {
        try {
        	//Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long userId = 1212;
            Users user = userService.fetchUser(userId);
            List<ClothingDonation> donations = clothingDonationService.getAllAvailableDonations();

            for (ClothingDonation donation : donations) {
                FoodBank bank = donation.getFoodBank();
                double distance = locationService.calculateDistance(user.getLat(), user.getLong(), bank.getLat(), bank.getLong());
                bank.setDistance(distance);
            }

            return ResponseEntity.ok(donations);
        } catch (Exception e) {
            logger.error("Error fetching clothing listings: {}", e.getMessage());
            return ResponseEntity.status(500).body("Unable to fetch listings");
        }
    }

    @PutMapping("/claim/{id}")
    public ResponseEntity<?> claimClothing(@PathVariable Long id) {
        try {
        	//Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long userId = 1212;
            ClothingDonation donation = clothingDonationService.fetchClothing(id);
            clothingDonationService.claimClothingItem(id);
            listingManager.extractListingInfo(donation, userId);
            return ResponseEntity.ok("Clothing item claimed successfully.");
        } catch (RuntimeException e) {
            logger.error("Claim error: {}", e.getMessage());
            return ResponseEntity.status(404).body("Clothing item not found or already claimed.");
        } catch (Exception e) {
            logger.error("Internal server error during claim: {}", e.getMessage());
            return ResponseEntity.status(500).body("Server error");
        }
    }

    @GetMapping("/my-donations")
    public ResponseEntity<?> getUserClothingDonations() {
        try {
            Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity.ok(clothingDonationService.getUserDonations(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not fetch user donations: " + e.getMessage());
        }
    }
}
