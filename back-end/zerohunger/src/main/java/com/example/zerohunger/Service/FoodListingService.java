package com.example.zerohunger.Service;

import com.example.zerohunger.DTO.AddFoodRequest;
import com.example.zerohunger.Entity.FoodBank;
import com.example.zerohunger.Entity.FoodListings;
import com.example.zerohunger.Entity.Users;
import com.example.zerohunger.Repository.FoodListingsRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodListingService {

    private static final Logger logger = LoggerFactory.getLogger(FoodListingService.class);
    private final FoodListingsRepo foodRepo;
    private final UsersService userService;
    private final DonationService donationService;

    @Autowired
    public FoodListingService(FoodListingsRepo foodRepo, UsersService userService, DonationService donationService) {
        this.foodRepo = foodRepo;
        this.userService = userService;
        this.donationService = donationService;
    }

    // ✅ Save a food listing
    public FoodListings CreateListings(AddFoodRequest request, Long userID) {
        if (request == null) {
            logger.error("Attempted to save a null food listing!");
            throw new IllegalArgumentException("Food listing cannot be null");
        }

        Users user = userService.fetchUser(userID);
        FoodBank bank = donationService.fetchBank(request.getFoodBankID());

        FoodListings listings = new FoodListings();
        listings.setExpirationDate(request.getExpirationDate());
        listings.setFoodName(request.getFoodName());
        listings.setFoodTypes(request.getFoodType());
        listings.setFoodBank(bank);
        listings.setUser(user);

        logger.info("Saving food listing: {}", listings);
        return foodRepo.save(listings);
    }
    
    public FoodListings fetchListing(Long listingID) {
    	FoodListings listing = foodRepo.findById(listingID)
    			.orElseThrow(() -> new RuntimeException("Listing not found for ID: " + listingID));
    	return listing;
    }
    
    public List<FoodListings> getAllListings(Long userID) {
    	List<FoodListings> list =  foodRepo.findAll();
    	list.removeIf(donation -> donation.getUser().getUserID().equals(userID));
        return list;
    }

    // ✅ Claim a food item
    public void claimFoodItem(Long id) {
        FoodListings listing = foodRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Food item with ID {} not found", id);
                    return new RuntimeException("Food item not found");
                });

        if (listing.isClaimed()) {
            logger.warn("Food item already claimed: {}", id);
            return;
        }

        listing.setClaimed(true);
        foodRepo.save(listing);
        
        logger.info("Food item with ID {} has been claimed", id);
    }
}
