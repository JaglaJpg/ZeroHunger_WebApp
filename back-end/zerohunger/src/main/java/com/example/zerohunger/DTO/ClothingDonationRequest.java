package com.example.zerohunger.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClothingDonationRequest {

    @NotBlank(message = "Cloth name is required")
    private String clothName;

    @NotBlank(message = "Cloth size is required")
    private String clothSize;

    @NotBlank(message = "Gender category is required")
    private String clothGender;

    @NotBlank(message = "Clothing category is required")
    private String category;

    @NotBlank(message = "Brand name is required")
    private String brandName;

    @NotNull(message = "Rate the condition from 1 - 5")
    private Integer condition;

    @NotNull(message = "A Food Bank must be selected")
    private Long foodBankID;  // ✅ Replaces pickupAddress and links to actual FoodBank

    // === Getters and Setters ===

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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public Long getFoodBankID() {
        return foodBankID;
    }

    public void setFoodBankID(Long foodBankID) {
        this.foodBankID = foodBankID;
    }
}
