package com.example.zerohunger.DTO;

import java.time.LocalDate;
import java.util.Set;

import com.example.zerohunger.Entity.Foodtype;

public class AddFoodRequest {
    private final String foodName;
    private final LocalDate expirationDate;
    private final Set<Foodtype> foodType;
    private final Long foodBankID;

    public AddFoodRequest(String foodName, LocalDate expirationDate, Set<Foodtype> foodType, Long foodBankID) {
        this.foodName = foodName;
        this.expirationDate = expirationDate;
        this.foodType = foodType;
        this.foodBankID = foodBankID;
    }

    public String getFoodName() {
        return this.foodName;
    }

    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    public Set<Foodtype> getFoodType() {
        return this.foodType;
    }

    public Long getFoodBankID() {
        return this.foodBankID;
    }

    public AddFoodRequest() {
        this.foodName = null;
        this.expirationDate = null;
        this.foodType = null;
        this.foodBankID = null;
    }
}
