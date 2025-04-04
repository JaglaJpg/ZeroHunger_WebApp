package com.example.zerohunger.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.zerohunger.DTO.Coordinates;
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
	private final LocationService locationService;
	
	@Autowired
	public UsersService(FoodService foodService, StatsService statsService, UsersRepo usersRepo
			, LocationService locationService) {
		this.foodService = foodService;
		this.statsService = statsService;
		this.usersRepo = usersRepo;
		this.locationService = locationService;
	}
	
	public boolean addUser(Users user) {
		try {
			user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
			Coordinates coords = locationService.getCoordinates(user.getAddress());
			user.setLat(coords.getLatitude());
			user.setLong(coords.getLongitude());
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
	
	public boolean verifyAge(LocalDate dob) {
		LocalDate today = LocalDate.now();
		int age = Period.between(today,  dob).getYears();
		
		if(age < 16) {
			return false;
		}
		
		return true;
	}
	
	public Users fetchUser(Long ID) {
		Users user = usersRepo.findById(ID)
				.orElseThrow(() -> new RuntimeException("cant find"));
		return user;
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
