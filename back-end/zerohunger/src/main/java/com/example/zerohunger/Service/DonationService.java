package com.example.zerohunger.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zerohunger.DTO.BankOptionsDTO;
import com.example.zerohunger.DTO.OngoingDonationDTO;
import com.example.zerohunger.Entity.DonationStatus;
import com.example.zerohunger.Entity.FoodBank;
import com.example.zerohunger.Entity.OngoingDonations;
import com.example.zerohunger.Entity.Users;
import com.example.zerohunger.Repository.DonationRepo;
import com.example.zerohunger.Repository.FoodBankRepo;

import jakarta.transaction.Transactional;

@Service
public class DonationService {

    private static final Logger logger = LoggerFactory.getLogger(DonationService.class);

    private final DonationRepo donationRepo;
    private final FoodBankRepo foodBankRepo;
    private final UsersService userService;
    private final LocationService location;

    @Autowired
    public DonationService(
        DonationRepo donationRepo,
        UsersService userService,
        FoodBankRepo foodBankRepo,
        LocationService location
    ) {
        this.donationRepo = donationRepo;
        this.userService = userService;
        this.foodBankRepo = foodBankRepo;
        this.location = location;
    }

    // Create a new donation record
    public OngoingDonations StartDonation(Long recipientID, Users donor, FoodBank bank, String name) {
        Users recipient = userService.fetchUser(recipientID);
        LocalDateTime date = LocalDateTime.now();

        OngoingDonations donation = new OngoingDonations();
        donation.setRecipient(recipient);
        donation.setDonor(donor);
        donation.setBank(bank);
        donation.setTimestamp(date);
        donation.setName(name);
        donation.setStatus(DonationStatus.PENDING);
        donationRepo.save(donation);
        logger.info("New donation created between donor {} and recipient {}", donor.getUserID(), recipientID);

        return donation;
    }

    @Transactional
    public Boolean updateStatus(Long donationID, DonationStatus status) {
        OngoingDonations donation = donationRepo.findById(donationID)
            .orElseThrow(() -> new RuntimeException("Donation not found"));

        donationRepo.updateStatus(status, donationID);

        return true;
    }

    // Fetch list of donation DTOs
    public List<OngoingDonationDTO> fetchDonations(Long ID) {
        List<OngoingDonationDTO> donations = new ArrayList<>();
        List<OngoingDonations> list = donationRepo.listDonations(ID);

        for (OngoingDonations x : list) {
            String type = ID.equals(x.getDonor().getUserID()) ? "donor" : "recipient";
            OngoingDonationDTO dto = new OngoingDonationDTO(x.getBank(), x, type);
            donations.add(dto);
        }

        return donations;
    }

    // Return sorted list of nearest food banks
    public List<BankOptionsDTO> fetchBanks(Long userID) {
        List<BankOptionsDTO> options = new ArrayList<>();
        try {
            Users user = userService.fetchUser(userID);
            List<FoodBank> banks = foodBankRepo.findAll();

            for (FoodBank x : banks) {
                double distance = location.calculateDistance(user.getLat(), user.getLong(), x.getLat(), x.getLong());
                BankOptionsDTO option = new BankOptionsDTO(x.getID(), x.getName(), distance);
                options.add(option);
            }

            options.sort(Comparator.comparingDouble(BankOptionsDTO::getDistance));
            if (options.size() > 10) {
                options.subList(10, options.size()).clear();
            }

        } catch (Exception e) {
            logger.error("Failed to fetch bank options", e);
            throw e;
        }

        return options;
    }
	
	public FoodBank fetchBank(Long bankID) {
	
		return foodBankRepo.findById(bankID)
				.orElseThrow(() -> new RuntimeException("FoodBank not found"));
	}
}
