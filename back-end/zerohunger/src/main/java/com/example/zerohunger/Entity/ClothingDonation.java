package com.example.zerohunger.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ClothingDonations")
public class ClothingDonation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String clothName;

    @NotBlank
    private String clothSize;

    @NotBlank
    private String clothGender;

    @NotBlank
    private String category;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer condition;

    private String brand;

    @ManyToOne
    @JoinColumn(name = "foodBank_id")
    private FoodBank foodBank;

    private String imageUrl;

    private LocalDateTime donationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id")
    private Users donor;

    private boolean isAvailable = true;

    @Transient
    private double distance;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClothName() {
        return clothName;
    }

    public void setClothName(String clothName) {
        this.clothName = clothName;
    }

    public String getClothSize() {
        return clothSize;
    }

    public void setClothSize(String clothSize) {
        this.clothSize = clothSize;
    }

    public String getClothGender() {
        return clothGender;
    }

    public void setClothGender(String clothGender) {
        this.clothGender = clothGender;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public FoodBank getFoodBank() {
        return foodBank;
    }

    public void setFoodBank(FoodBank foodBank) {
        this.foodBank = foodBank;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
