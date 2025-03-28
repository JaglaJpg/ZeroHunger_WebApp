package com.example.zerohunger.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "FoodListings")
public class FoodListings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foodName;

    private LocalDate expirationDate;

    private boolean claimed = false; // ✅ NEW FIELD for claim status

    @ElementCollection(targetClass = Foodtype.class)
    @CollectionTable(name = "food_listing_types", joinColumns = @JoinColumn(name = "food_listing_id"))
    @Enumerated(EnumType.STRING)
    private Set<Foodtype> foodTypes;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = true)
    private Users user;
    
    @ManyToOne
    @JoinColumn(name = "foodBankID")
    private FoodBank foodBank;
    @Transient
    private double distance;

    // ✅ Default Constructor
    public FoodListings() {}

    // ✅ Constructor with fields
    public FoodListings(String foodName, LocalDate expirationDate, Set<Foodtype> foodTypes, Users user) {
        this.foodName = foodName;
        this.expirationDate = expirationDate;
        this.foodTypes = foodTypes;
        this.user = user;
    }

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Set<Foodtype> getFoodTypes() {
        return foodTypes;
    }

    public void setFoodTypes(Set<Foodtype> foodTypes) {
        this.foodTypes = foodTypes;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    // ✅ Claim-related methods
    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }
    
    public FoodBank getFoodBank() {
        return foodBank;
    }

    public void setFoodBank(FoodBank foodBank) {
        this.foodBank = foodBank;
    }
    
    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
    	this.distance = distance;
    }
    
}
