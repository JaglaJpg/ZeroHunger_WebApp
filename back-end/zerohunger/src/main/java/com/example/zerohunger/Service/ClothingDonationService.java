package com.example.zerohunger.Service;

import com.example.zerohunger.DTO.ClothingDonationRequest;
import com.example.zerohunger.Entity.ClothingDonation;
import com.example.zerohunger.Entity.FoodBank;
import com.example.zerohunger.Entity.Users;
import com.example.zerohunger.Repository.ClothingDonationRepository;
import com.example.zerohunger.Repository.UsersRepo;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClothingDonationService {

    @Autowired
    private ClothingDonationRepository clothingRepository;

    @Autowired
    private UsersService userService;

    @Autowired
    private DonationService donationService;

    @Value("${app.upload.dir:${user.home}/uploads/clothes}")
    private String uploadDir;

    // ✅ Create a new clothing donation
    @Transactional
    public ClothingDonation createDonation(ClothingDonationRequest request, MultipartFile imageFile, Long userID) throws IOException {
        Users donor = userService.fetchUser(userID);
        FoodBank bank = donationService.fetchBank(request.getFoodBankID());

        ClothingDonation donation = new ClothingDonation();
        donation.setClothName(request.getClothName());
        donation.setClothSize(request.getClothSize());
        donation.setClothGender(request.getClothGender());
        donation.setCategory(request.getCategory());
        donation.setBrand(request.getBrandName());
        donation.setCondition(request.getCondition());
        donation.setDonor(donor);
        donation.setFoodBank(bank);
        donation.setDonationDate(LocalDateTime.now());

        // ✅ Handle image
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath);
            donation.setImageUrl("/uploads/clothes/" + fileName);
        }

        return clothingRepository.save(donation);
    }

    public List<ClothingDonation> getAllAvailableDonations() {
        return clothingRepository.findByIsAvailableTrue();
    }

    public List<ClothingDonation> getUserDonations(Long userID) {
        return clothingRepository.findByDonor_UserID(userID);
    }

    public ClothingDonation fetchClothing(Long id) {
        return clothingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clothing donation not found with ID: " + id));
    }

    @Transactional
    public void claimClothingItem(Long id) {
        ClothingDonation donation = clothingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clothing donation not found"));

        if (!donation.isAvailable()) {
            throw new RuntimeException("Clothing donation is already claimed");
        }

        donation.setAvailable(false);
        clothingRepository.save(donation);
    }
}
