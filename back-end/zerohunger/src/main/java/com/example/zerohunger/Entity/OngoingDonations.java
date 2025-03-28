package com.example.zerohunger.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "OngoingDonations")
public class OngoingDonations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationID;
    
    @ManyToOne
    @JoinColumn(name = "donorID", nullable = false)
    private Users donor;
    
    @ManyToOne
    @JoinColumn(name = "recipientID", nullable = true)
    private Users recipient;
    
    @Enumerated(EnumType.STRING)
    private DonationStatus status;
    
    @ManyToOne
    @JoinColumn(name = "foodBankID", nullable = false)
    private FoodBank bank;
    
    private LocalDateTime timestamp;
    private String name;

    public Long getDonationID() {
        return donationID;
    }

    public void setDonationID(Long donationID) {
        this.donationID = donationID;
    }

    public Users getDonor() {
        return donor;
    }

    public void setDonor(Users donor) {
        this.donor = donor;
    }

    public Users getRecipient() {
        return recipient;
    }

    public void setRecipient(Users recipient) {
        this.recipient = recipient;
    }

    public DonationStatus getStatus() {
        return status;
    }

    public void setStatus(DonationStatus status) {
        this.status = status;
    }

    public FoodBank getBank() {
        return bank;
    }

    public void setBank(FoodBank bank) {
        this.bank = bank;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
