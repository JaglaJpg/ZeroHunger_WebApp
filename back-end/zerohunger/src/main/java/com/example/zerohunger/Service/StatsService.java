package com.example.zerohunger.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zerohunger.DTO.MonthlyFoodStatsDTO;
import com.example.zerohunger.DTO.UserStatsDTO;
import com.example.zerohunger.Entity.DonationType;
import com.example.zerohunger.Entity.userStats;
import com.example.zerohunger.Repository.StatsRepo;

//Service layer is for the actual logic
@Service
public class StatsService {
	private final StatsRepo statsRepo;
	
	@Autowired
	public StatsService(StatsRepo statsRepo) {
		this.statsRepo = statsRepo;
	}
	
	public UserStatsDTO getUserStats(Long UserID) {
		userStats statsEntry = statsRepo.findById(UserID).orElse(null);
		UserStatsDTO stats = new UserStatsDTO(statsEntry.getTotalWasted(), 
				statsEntry.getTotalSaved(), 
				statsEntry.getWastedLastWeek(), 
				statsEntry.getWastedLastMonth(),
				statsEntry.getFoodDonated(),
				statsEntry.getClothesDonated(),
				statsEntry.getAppliancesDonated());
		
		
		return stats;
	}
	
	public void IncrementDonation(Long userID, DonationType type) {
		userStats user = statsRepo.findById(userID).orElseThrow(() -> new RuntimeException("Stats not found for userId " + userID));
		switch(type) {
		case DonationType.FOOD:
			user.addFoodDonated();
			break;
		case DonationType.CLOTHING:
			user.addClothesDonated();
			break;
		case DonationType.APPLIANCE:
			user.addAppliancesDonated();
			break;
		}
		
		statsRepo.save(user);
	}
	
	
	
	//method for incrementing totalWasted field in userStats by 1
	public void IncrementTotalWasted(Long userID) {
		userStats user = statsRepo.findById(userID).orElseThrow(() -> new RuntimeException("Stats not found for userId " + userID));
		user.addTotalWasted();
		statsRepo.save(user);
	}
	
	//method for incrementing totalSaved field in userStats by 1
	public void IncrementTotalSaved(Long userID) {
		userStats user = statsRepo.findById(userID).orElseThrow(() -> new RuntimeException("Stats not found for userId " + userID));
		user.addTotalSaved();
		statsRepo.save(user);
	}
	
	//Method that calls the JPQL query for updating the stats of the user with the given userID and updating them with the given values week and month
	public void routineUpdateStats(int week, int month, Long userID) {
		statsRepo.routineUpdateStats(week, month, userID);
	}
}
