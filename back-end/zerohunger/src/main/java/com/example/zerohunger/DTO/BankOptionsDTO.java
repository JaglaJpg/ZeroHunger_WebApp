package com.example.zerohunger.DTO;

public class BankOptionsDTO {
	private final Long bankID;
	private final String name;
	private final double distance;
	
	public BankOptionsDTO(Long bankID, String name, double distance) {
		this.bankID = bankID;
		this.name = name;
		this.distance = distance;
	}
	
	public Long getBankID() {
		return this.bankID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getDistance() {
		return this.distance;
	}
}
