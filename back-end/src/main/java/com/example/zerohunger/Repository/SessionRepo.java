package com.example.zerohunger.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.zerohunger.Entity.RefreshToken;
import com.example.zerohunger.Entity.Sessions;
import com.example.zerohunger.Entity.Users;

@Repository
public interface SessionRepo extends JpaRepository<Sessions, Long> {

	@Modifying
	@Query("DELETE FROM Sessions s WHERE s.userID = :userID")
	void deleteByUserID(@Param("userID")Users userID);
}
