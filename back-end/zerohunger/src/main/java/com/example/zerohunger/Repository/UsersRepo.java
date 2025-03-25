package com.example.zerohunger.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.zerohunger.Entity.Users;

//Repository allows for backend logic to interact with db tables through JpaRepository which uses JPQL( aka SQL for java)
@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
	
	//having this interface alone allows the service layer to sue basic JPQL queries however i can also define custom more complex ones here
	
	//Returns a list of all userIDs from the Users table
	@Query("SELECT x.userID FROM Users x")
	List<Long> listUserIDs();
	
	Users findByEmail(String email);

	
}
