package com.example.zerohunger.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.example.zerohunger.Entity.userStats;

//Repository allows for backend logic to interact with db tables through JpaRepository which uses JPQL( aka SQL for java)
@Repository
public interface StatsRepo extends JpaRepository<userStats, Long> {
	
	//having this interface alone allows the service layer to sue basic JPQL queries however i can also define custom more complex ones here
	
	//Updates the wastedLastWeek and wastedLastMonth stats of a user indicated by the passed userID with the given values, in the userStats table
	@Modifying
	@Query("UPDATE userStats u SET u.wastedLastWeek = :week, u.wastedLastMonth = :month WHERE userID = :Id")
	void routineUpdateStats(@Param("week") int week, @Param("month") int month, @Param("Id") Long Id);
	
	
}
