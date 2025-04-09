package com.example.zerohunger.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "appliances_donations")
public class AppliancesDonation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Appliance name is required")
    private String applianceName;

    @NotBlank(message = "Brand information is required")
    private String make;

    @NotBlank(message = "Model information is required")
    private String model;

    @NotBlank(message = "Specifications are required")
    private String specs;

    @NotNull(message = "Condition rating is required")
    @Min(value = 1, message = "Condition must be between 1 and 5")
    @Max(value = 5, message = "Condition must be between 1 and 5")
    @Column(nullable = false)
    private Integer condition;

    @ManyToOne
    @JoinColumn(name = "foodBank_id")
    private FoodBank foodBank;

    private LocalDateTime donationDate;

    @ManyToOne
    @JoinColumn(name = "donorId")
    private Users donor;

    private boolean isAvailable = true;

    @Column
    private String imageUrl;
    
    @Transient
    double Distance;
    
    @Transient
    private Boolean belongsToUser;
    
    public void setBelongs(Boolean x) {this.belongsToUser = x;}
    public Boolean getBelongs() {return this.belongsToUser;}

    // Constructor
    public AppliancesDonation() {}

    public AppliancesDonation(String applianceName, String make, 
    		String model, String specs, 
    		Integer condition, FoodBank foodBank, 
    		Users donor) {
        this.applianceName = applianceName;
        this.make = make;
        this.model = model;
        this.specs = specs;
        this.condition = condition;
        this.foodBank = foodBank;
        this.donor = donor;
        this.donationDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getApplianceName() {
        return applianceName;
    }

    public void setApplianceName(String applianceName) {
        this.applianceName = applianceName;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public FoodBank getFoodBank() {
        return foodBank;
    }

    public void setFoodBank(FoodBank foodBank) {
        this.foodBank = foodBank;
    }

    public LocalDateTime getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDateTime donationDate) {
        this.donationDate = donationDate;
    }

    public Users getDonor() {
        return donor;
    }

    public void setDonor(Users donor) {
        this.donor = donor;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public double getDistance() {
        return Distance;
    }

    public void setDistance(double Distance) {
        this.Distance = Distance;
    }

}