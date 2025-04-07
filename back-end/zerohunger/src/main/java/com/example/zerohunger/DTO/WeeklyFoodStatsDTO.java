package com.example.zerohunger.DTO;

public class WeeklyFoodStatsDTO {
    private String name; // e.g. "Week 1"
    private int foodSaved;
    private int foodWasted;

    public WeeklyFoodStatsDTO(String name, int foodSaved, int foodWasted) {
        this.name = name;
        this.foodSaved = foodSaved;
        this.foodWasted = foodWasted;
    }

    public String getName() {
        return name;
    }

    public int getFoodSaved() {
        return foodSaved;
    }

    public int getFoodWasted() {
        return foodWasted;
    }
}
