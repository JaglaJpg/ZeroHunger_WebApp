package com.example.zerohunger.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.zerohunger.DTO.MarkFoodDTO;
import com.example.zerohunger.DTO.MonthlyFoodStatsDTO;
import com.example.zerohunger.DTO.UserStatsDTO;
import com.example.zerohunger.DTO.WeeklyFoodStatsDTO;
import com.example.zerohunger.Entity.FoodItems;
import com.example.zerohunger.Entity.FoodStatus;
import com.example.zerohunger.Service.FoodService;
import com.example.zerohunger.Service.StatsService;
import com.example.zerohunger.Service.UsersService;
import com.example.zerohunger.Utility.TokenUtil;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/user")
public class UserController {
    private final StatsService statsService;
    private final TokenUtil tokenUtil;
    private final FoodService foodService;
    private final UsersService userService;

    @Autowired
    public UserController(StatsService statsService, TokenUtil tokenUtil,
    		FoodService foodService, UsersService userService) {
        this.statsService = statsService;
        this.tokenUtil = tokenUtil;
        this.foodService = foodService;
        this.userService = userService;
    }
    
    @GetMapping("/stats")
    public ResponseEntity<?> getUserStats(){
    	//Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	long userId = 1111;
    	
        UserStatsDTO stats = statsService.getUserStats(userId);
        
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/monthly")
    public ResponseEntity<?> getMonthlyStats(){
    	//Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	long userId = 1111;
    	
    	List<MonthlyFoodStatsDTO> stats = foodService.getMonthlyStats(userId);
    	
    	return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/weekly")
    public ResponseEntity<?> getWeeklyStats(){
    	//Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	long userId = 1111;
    	
    	List<WeeklyFoodStatsDTO> stats = foodService.getWeeklyStats(userId);
    	
    	return ResponseEntity.ok(stats);
    }
    
    @PostMapping("/addFood")
    public ResponseEntity<?> addFood(@RequestBody FoodItems foodItem) {
        long userID = 1111; // ✅ Temporary User ID for testing
        
        FoodItems savedItem =foodService.AddFood(foodItem, userID);

        return ResponseEntity.ok(savedItem);
    }
    
    @GetMapping("/getFood")
    public ResponseEntity<?> getFood() {
    	long userID = 1111;

        return ResponseEntity.ok(foodService.getUserFood(userID));
    }
    
    @PostMapping("/markFood")
    public ResponseEntity<String> markFood(@RequestBody MarkFoodDTO request) {
        System.out.println("Received request: " + request);

        if (request == null) {
            return ResponseEntity.badRequest().body("Request body is missing");
        }

        Long foodID = request.getFoodID();
        FoodStatus status = request.getStatus();

        if (foodID == null || status == null) {
            return ResponseEntity.badRequest().body("Invalid request format: Missing foodID or status");
        }

        try {
            foodService.MarkFood(foodID, status);
            return ResponseEntity.ok("Food item marked as " + status);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    
    



}
