package com.example.zerohunger.Controller;

import com.example.zerohunger.DTO.AddFoodRequest;
import com.example.zerohunger.Entity.FoodBank;
import com.example.zerohunger.Entity.FoodListings;
import com.example.zerohunger.Entity.Users;
import com.example.zerohunger.Service.FoodListingService;
import com.example.zerohunger.Service.LocationService;
import com.example.zerohunger.Service.UsersService;
import com.example.zerohunger.Utility.ListingManager;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") // ✅ Allow React frontend
@RestController
@RequestMapping("/listings")
public class ListingController {

    private static final Logger logger = LoggerFactory.getLogger(ListingController.class);
    private final FoodListingService foodService;
    private final LocationService location;
    private final UsersService userService;
    private final ListingManager listManager;

    @Autowired
    public ListingController(FoodListingService foodService, LocationService location, 
    		UsersService userService, ListingManager listManager) {
        this.foodService = foodService;
        this.location = location;
        this.userService = userService;
        this.listManager = listManager;
    }

    // ✅ Add food
    @PostMapping("/AddFood")
    public ResponseEntity<?> addFoodListing(@RequestBody AddFoodRequest request) {
        logger.info("Received request: {}", request);

        if (request == null || request.getFoodName() == null || request.getExpirationDate() == null) {
            logger.error("Invalid request data: {}", request);
            return ResponseEntity.badRequest().body("Invalid food listing data.");
        }

        try {
        	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		Long userId = principal instanceof Long ? (Long) principal : Long.parseLong(principal.toString());
            return ResponseEntity.ok(foodService.CreateListings(request, userId));
        } catch (Exception e) {
            logger.error("Error adding food listing", e);
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    // ✅ Get all food listings
    @GetMapping
    public ResponseEntity<?> getAllListings() {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userId = principal instanceof Long ? (Long) principal : Long.parseLong(principal.toString());
    	Users user = userService.fetchUser(userId);
    	List<FoodListings> listings = foodService.getAllListings(userId);
    	
    	for(FoodListings x : listings) {
    		FoodBank foodBank = x.getFoodBank();
    		double distance = location.calculateDistance(user.getLat(), user.getLong(), foodBank.getLat(), foodBank.getLong());
    		foodBank.setDistance(distance);
    	}
        return ResponseEntity.ok(listings);
    }

    // ✅ Claim donation
    @PutMapping("/claim/{id}")
    public ResponseEntity<?> claimFoodItem(@PathVariable Long id) {
        logger.info("Claim request received for ID: {}", id);

        try {
        	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		Long userId = principal instanceof Long ? (Long) principal : Long.parseLong(principal.toString());
        	FoodListings listing = foodService.fetchListing(id);
            foodService.claimFoodItem(id);
            listManager.extractListingInfo(listing, userId);
            return ResponseEntity.ok("Food item claimed successfully.");
        } catch (RuntimeException e) {
            logger.error("Error claiming food item: {}", e.getMessage());
            return ResponseEntity.status(404).body("Food item not found or already claimed.");
        } catch (Exception e) {
            logger.error("Internal server error while claiming: {}", e.getMessage());
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
