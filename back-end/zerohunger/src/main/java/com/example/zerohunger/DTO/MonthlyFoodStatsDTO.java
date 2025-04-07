package com.example.zerohunger.DTO;

public class MonthlyFoodStatsDTO {
    private String name; // e.g. "Jan"
    private int Saved;
    private int Wasted;

    public MonthlyFoodStatsDTO(String name, int saved, int wasted) {
        this.name = name;
        this.Saved = saved;
        this.Wasted = wasted;
    }

    public String getName() {
        return name;
    }

    public int getSaved() {
        return Saved;
    }

    public int getWasted() {
        return Wasted;
    }
}
