package com.example.zerohunger.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zerohunger.Entity.FoodItems;
import com.example.zerohunger.Entity.FoodStatus;
import com.example.zerohunger.Entity.Users;
import com.example.zerohunger.Entity.userStats;
import com.example.zerohunger.Repository.FoodRepo;

import jakarta.transaction.Transactional;

//Service layer is for the actual logic
@Service
public class FoodService {
	private final FoodRepo foodRepo;
	private final StatsService statsService;
	
	//Autowiring means that these references dont need to be passed into any method, springboot handles that
	@Autowired 
	public FoodService(FoodRepo foodRepo, StatsService statsService) {
		this.foodRepo = foodRepo;
		this.statsService = statsService;
	}
	
	//Method for adding food to the foodItems table, an Instance of the class for the table is made, 
	//my setters are used to fill values and the JPQL save command is used to commit it to the table as a record
	@Transactional
	public FoodItems AddFood(FoodItems foodItem, Long userID) {
		Users user = new Users();
		
		user.setUserID(userID);
		foodItem.setStatus(FoodStatus.UNCONFIRMED);//Status initially unconfirmed
		foodItem.setDateChanged(null);//dateChanged changes the statusChanged field, currently status is unconfirmed so date is null
		foodItem.setUser(user); // when u make userRepo fix this wiktor!!!
		return foodRepo.save(foodItem);
	}
	
	public List<FoodItems> getUserFood(Long userId){
		Users user = new Users();
		user.setUserID(userId);
		return foodRepo.getFridge(user);
	}
	
	//Method for marking food as used or wasted in the foodItems table.
	@Transactional
	public void MarkFood(Long foodID, FoodStatus status) {
		FoodItems foodItem = foodRepo.findById(foodID).orElseThrow(() -> new RuntimeException("Food not found for foodId " + foodID)); //Relevant Record is fetched
		Long userID = foodItem.getUser().getUserID(); //UserId is taken from the record for stats to be adjsuted
		LocalDate date = LocalDate.now();
		foodRepo.updateFoodStatus(foodID, status, date); //Food status is changed and statusChanged is set to current date

		switch(status) { //Either totalWasted or totalSaved is incremented by one
		case USED:
			statsService.IncrementTotalSaved(userID);
			break;
		case WASTED:
			statsService.IncrementTotalWasted(userID);
			break;
		default:
			break;
		}
	}
	
	//Method that calls query that removes all food from foodItems that was marked over a month ago
	public void removeOldFood() {
		LocalDate date = LocalDate.now();
		LocalDate old = date.minusMonths(1);
		
		foodRepo.removeOldFood(old);
	}
	
	//Method that counts all food marked within last week and all food marked within last month and updates the relevant field in userStats
	public void CountFoodSinceDate(Long userID) {
		LocalDate date = LocalDate.now();
		LocalDate week = date.minusWeeks(1);
		LocalDate month = date.minusMonths(1);
		
		int weekCount = foodRepo.getFoodCountSince(week, userID);
		int monthCount = foodRepo.getFoodCountSince(month, userID);
		
		statsService.routineUpdateStats(weekCount, monthCount, userID);
	}
}
