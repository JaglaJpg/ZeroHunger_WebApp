package com.example.zerohunger.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "sessions")
public class Sessions {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String sessionID;
	
	@OneToOne
	@JoinColumn(name = "userID",referencedColumnName = "userID")
	private Users userID;
	
	private LocalDateTime expiresAt;
	
	
	public String getSeshID() {
		return sessionID;
	}
	
	public void setSeshID(String ID) {
		sessionID = ID;
	}
	
	public Users getUserID() {
		return userID;
	}
	
	public void setUserID(Users ID) {
		userID = ID;
	}
	public LocalDateTime getExpAt() {
		return expiresAt;
	}
	
	public void setExpAt(LocalDateTime exp) {
		expiresAt = exp;
	}

}
