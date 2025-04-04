package com.example.zerohunger.DTO;

import com.example.zerohunger.Entity.FoodBank;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public class AppliancesDonationRequest {
	@NotBlank(message = "Appliance name is required")
	private String applianceName;
	
	@NotBlank(message = "Brand information is required")
	private String make;

	@NotBlank(message = "Model information is required")
	private String model;
	
	@NotBlank(message = "Specifications are required")
	private String specs;
	
	@NotNull(message = "Rate the condition from 1 - 5")
    private Integer condition;
	
	@NotNull
    private Long foodBankID;

	// Getters and Setters
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

    public Long getFoodBankID() {
        return foodBankID;
    }

    public void setFoodBankID(Long foodBankID) {
        this.foodBankID = foodBankID;
    }
	
	

}