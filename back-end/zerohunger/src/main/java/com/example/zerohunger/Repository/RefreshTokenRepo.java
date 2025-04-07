package com.example.zerohunger.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.zerohunger.Entity.RefreshToken;
import com.example.zerohunger.Entity.Users;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

	@Modifying
	@Query("DELETE FROM RefreshToken r WHERE r.userID = :userID")
	void deleteByUserID(@Param("userID")Users userID);
	
	RefreshToken findByUserID_UserID(Long userID);
	
	void deleteByUserID_UserID(Long userID);

}
