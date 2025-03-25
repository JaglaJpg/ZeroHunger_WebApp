package com.example.zerohunger.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zerohunger.DTO.UserStatsDTO;
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
				statsEntry.getWastedLastMonth());
		
		return stats;
	}
	
	
	//method for incrementing totalWasted field in userStats by 1
	public void IncrementTotalWasted(Long userID) {
		userStats user = statsRepo.findById(userID).orElseThrow(() -> new RuntimeException("Stats not found for userId " + userID));
		user.addTotalWasted();
	}
	
	//method for incrementing totalSaved field in userStats by 1
	public void IncrementTotalSaved(Long userID) {
		userStats user = statsRepo.findById(userID).orElseThrow(() -> new RuntimeException("Stats not found for userId " + userID));
		user.addTotalSaved();
	}
	
	//Method that calls the JPQL query for updating the stats of the user with the given userID and updating them with the given values week and month
	public void routineUpdateStats(int week, int month, Long userID) {
		statsRepo.routineUpdateStats(week, month, userID);
	}
}
