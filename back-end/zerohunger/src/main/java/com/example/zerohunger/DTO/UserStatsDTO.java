package com.example.zerohunger.DTO;

public class UserStatsDTO {
    private final int totalWasted;
    private final int totalSaved;
    private final int wastedLastWeek;
    private final int wastedLastMonth;

    // ✅ New donation stats
    private final int foodDonated;
    private final int clothesDonated;
    private final int appliancesDonated;

    public UserStatsDTO(int totalWasted, int totalSaved, int wastedLastWeek, int wastedLastMonth,
                        int foodDonated, int clothesDonated, int appliancesDonated) {
        this.totalWasted = totalWasted;
        this.totalSaved = totalSaved;
        this.wastedLastWeek = wastedLastWeek;
        this.wastedLastMonth = wastedLastMonth;
        this.foodDonated = foodDonated;
        this.clothesDonated = clothesDonated;
        this.appliancesDonated = appliancesDonated;
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

    public int getFoodDonated() {
        return foodDonated;
    }

    public int getClothesDonated() {
        return clothesDonated;
    }

    public int getAppliancesDonated() {
        return appliancesDonated;
    }
}
