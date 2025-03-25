package com.example.zerohunger.DTO;

import com.example.zerohunger.Entity.FoodStatus;

public class MarkFoodDTO {
    private Long foodID;
    private FoodStatus status;

    public Long getFoodID() { return foodID; }
    public void setFoodID(Long foodID) { this.foodID = foodID; }

    public FoodStatus getStatus() { return status; }
    public void setStatus(FoodStatus status) { this.status = status; }
}


