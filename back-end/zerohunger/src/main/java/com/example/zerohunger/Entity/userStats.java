package com.example.zerohunger.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "stats")
public class userStats {

    @Id
    private Long userID;

    @OneToOne
    @MapsId // Tells Hibernate this is the same ID as the associated user
    @JoinColumn(name = "userID")
    private Users user;

    @Column(nullable = false)
    private int totalWasted = 0;

    @Column(nullable = false)
    private int totalSaved = 0;

    @Column(nullable = false)
    private int wastedLastWeek = 0;

    @Column(nullable = false)
    private int wastedLastMonth = 0;

    @Column(nullable = false)
    private int foodDonated = 0;

    @Column(nullable = false)
    private int clothesDonated = 0;

    @Column(nullable = false)
    private int appliancesDonated = 0;

    // Getters and Setters
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getTotalWasted() {
        return totalWasted;
    }

    public void addTotalWasted() {
        this.totalWasted++;
    }

    public void setTotalWasted(int i) {
        totalWasted = i;
    }

    public int getTotalSaved() {
        return totalSaved;
    }

    public void addTotalSaved() {
        this.totalSaved++;
    }

    public void setTotalSaved(int i) {
        totalSaved = i;
    }

    public int getWastedLastWeek() {
        return wastedLastWeek;
    }

    public void setWastedLastWeek(int waste) {
        this.wastedLastWeek = waste;
    }

    public int getWastedLastMonth() {
        return wastedLastMonth;
    }

    public void setWastedLastMonth(int waste) {
        this.wastedLastMonth = waste;
    }

    public int getFoodDonated() {
        return foodDonated;
    }

    public void addFoodDonated() {
        this.foodDonated++;
    }

    public void setFoodDonated(int i) {
        this.foodDonated = i;
    }

    public int getClothesDonated() {
        return clothesDonated;
    }

    public void addClothesDonated() {
        this.clothesDonated++;
    }

    public void setClothesDonated(int i) {
        this.clothesDonated = i;
    }

    public int getAppliancesDonated() {
        return appliancesDonated;
    }

    public void addAppliancesDonated() {
        this.appliancesDonated++;
    }

    public void setAppliancesDonated(int i) {
        this.appliancesDonated = i;
    }
}
