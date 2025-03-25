package com.example.zerohunger.DTO;

public class UserStatsDTO {
	private final int totalWasted;
	private final int totalSaved;
	private final int wastedLastWeek;
	private final int wastedLastMonth;
	
	public UserStatsDTO(int totalWasted, int totalSaved, int wastedLastWeek, int wastedLastMonth) {
		this.totalWasted = totalWasted;
		this.totalSaved = totalSaved;
		this.wastedLastWeek = wastedLastWeek;
		this.wastedLastMonth = wastedLastMonth;
	}
	
	public int getTotalWasted() {
		return totalWasted;
	}
	public int getTotalSaved() {
		return totalSaved;
	}
	public int getWastedLastWeek() {
		return wastedLastWeek;
	}
	public int getWastedLastMonth() {
		return wastedLastMonth;
	}
}
