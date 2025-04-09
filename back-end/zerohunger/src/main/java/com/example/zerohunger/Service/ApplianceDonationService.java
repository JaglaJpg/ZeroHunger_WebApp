package com.example.zerohunger.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.zerohunger.DTO.AppliancesDonationRequest;
import com.example.zerohunger.Entity.AppliancesDonation;
import com.example.zerohunger.Entity.FoodBank;
import com.example.zerohunger.Entity.Users;
import com.example.zerohunger.Repository.ApplianceDonationRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ApplianceDonationService {

    @Autowired
    private ApplianceDonationRepository applianceRepository;

    @Autowired
    private UsersService userService;

    @Value("${app.upload.dir:${user.home}/uploads/appliances}")
    private String uploadDir;
    
    @Autowired
    private DonationService donationService;

    @Transactional
    public AppliancesDonation createDonation(@Valid AppliancesDonationRequest donationRequest, MultipartFile imageFile, Long userID) throws IOException {
        Users donor = userService.fetchUser(userID);
        FoodBank foodBank = donationService.fetchBank(donationRequest.getFoodBankID());

        AppliancesDonation donation = new AppliancesDonation();
        donation.setApplianceName(donationRequest.getApplianceName());
        donation.setModel(donationRequest.getModel());
        donation.setMake(donationRequest.getMake());
        donation.setSpecs(donationRequest.getSpecs());
        donation.setCondition(donationRequest.getCondition());
        donation.setFoodBank(foodBank);
        donation.setDonor(donor);
        donation.setDonationDate(LocalDateTime.now());

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath);

            donation.setImageUrl("/uploads/appliances/" + fileName);
        }

        return applianceRepository.save(donation);
    }

    public List<AppliancesDonation> getAllAvailableDonations(Long userID) {
    	List<AppliancesDonation> list = applianceRepository.findByIsAvailableTrue();
    	for (AppliancesDonation x : list) {
    		x.setBelongs(userID == x.getDonor().getUserID()? true:false);
    	}
    	
        return list;
    }

    public List<AppliancesDonation> getUserDonations(Long userID) {
        return applianceRepository.findByDonor_UserID(userID);
    }

    @Transactional
    public boolean claimDonation(Long donationId) {
    	AppliancesDonation donation = applianceRepository.findById(donationId)
    			.orElseThrow(() -> new RuntimeException("Clothing donation not found"));
    			
        if (!donation.isAvailable()) {
            return false;
        }

        donation.setAvailable(false);
        applianceRepository.save(donation);
        return true;
    }
    
    public AppliancesDonation fetchAppliance(Long Id) {
    	return applianceRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Appliance donation not found with ID: " + Id));
    }
}
