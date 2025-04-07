package com.example.zerohunger.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.zerohunger.Entity.FoodItems;
import com.example.zerohunger.Entity.FoodStatus;
import com.example.zerohunger.Entity.Users;

import jakarta.transaction.Transactional;

//Repository allows for backend logic to interact with db tables through JpaRepository which uses JPQL( aka SQL for java)
@Repository
public interface FoodRepo extends JpaRepository<FoodItems, Long> {
	
	//having this interface alone allows the service layer to sue basic JPQL queries however i can also define custom more complex ones here
	
	//Finds relevant food through foodID and changes the status and statusChanged to the values passed through
	@Modifying
	@Query("UPDATE FoodItems f SET f.status = :status, f.statusChanged = :date WHERE f.foodID = :id")
	void updateFoodStatus(@Param("id") Long id, @Param("status") FoodStatus status, @Param("date") LocalDate date);
	
	//Removes all food in the foodItems table that has statusChanged set to a date further back than the passed date
	@Transactional
	@Modifying
	@Query("DELETE FROM FoodItems f WHERE f.statusChanged < :date")
	void removeOldFood(@Param("date") LocalDate date);
	
	//returns a count of all food items from a specified user within a date range made with the passed date as the lower bound andd current date as the upper
	@Query("SELECT COUNT(f) FROM FoodItems f WHERE f.statusChanged > :date AND f.user.userID = :Id")
	int getFoodCountSince(@Param("date") LocalDate date, @Param("Id") Long Id);
	
	@Query("SELECT f FROM FoodItems f WHERE f.statusChanged IS NULL AND f.user.userID = :userID")
	List<FoodItems> getFridge(@Param("userID") Long userID);
	
	@Query("SELECT COUNT(f) "
			+ "FROM FoodItems f "
			+ "WHERE f.statusChanged > :old "
			+ "AND f.statusChanged < :newer "
			+ "AND f.user.userID = :Id "
			+ "AND f.status = :status")
	int getFoodCountBetween(@Param("old") LocalDate old, @Param("newer") LocalDate newer, @Param("Id") Long Id, @Param("status") FoodStatus status);



}
