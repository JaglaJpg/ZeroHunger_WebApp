package com.example.zerohunger.Service;

import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.zerohunger.DTO.LoginRequest;
import com.example.zerohunger.DTO.LoginResponse;
import com.example.zerohunger.Entity.Users;
import com.example.zerohunger.Entity.userStats;
import com.example.zerohunger.Repository.UsersRepo;
import com.example.zerohunger.Utility.PasswordUtil;

//Service layer is for the actual logic
@Service
public class UsersService {
	private final FoodService foodService;
	private final StatsService statsService; // might not be needed decide later
	private final UsersRepo usersRepo;
	
	@Autowired
	public UsersService(FoodService foodService, StatsService statsService, UsersRepo usersRepo) {
		this.foodService = foodService;
		this.statsService = statsService;
		this.usersRepo = usersRepo;
	}
	
	public boolean addUser(Users user) {
		try {
			user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
	        userStats stats = new userStats();
	        stats.setUserID(user);
	        stats.setTotalSaved(0);
	        stats.setTotalWasted(0);
	        stats.setWastedLastMonth(0);
	        stats.setWastedLastWeek(0);
	        
	        user.setStats(stats);
			usersRepo.save(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public LoginResponse VerifyUser(LoginRequest request) {
		String email = request.getEmail();
		String password = request.getPassword();
		Users user = usersRepo.findByEmail(email);
		if(user == null) {
			return new LoginResponse(false);
		}
		
		LoginResponse response = new LoginResponse(PasswordUtil.verifyPassword(password, user.getPassword())
		, user);
		
		return response;
	}
	
	//Scheduled routine job that runs every midnight
	@Scheduled(cron = "0 0 0 * * *")
	public void midnightStatRecount() {
		List<Long> Ids = usersRepo.listUserIDs();//gets a list of all userIDS
		foodService.removeOldFood();//removes all food marked over a month ago from the foodItems table
		
		for(Long x : Ids) {
			foodService.CountFoodSinceDate(x);//goes through all userIDs and recoutns their monthly and weekly stats in userStats
		}
	}
}
