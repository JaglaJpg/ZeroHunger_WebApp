package com.example.zerohunger.DTO;

import com.example.zerohunger.Entity.DonationStatus;

//In DTO package
public class DonationStatusUpdateDTO {
 private Long donationId;
 private DonationStatus status;

 public DonationStatusUpdateDTO(Long donationId, DonationStatus status) {
	 this.donationId = donationId;
	 this.status = status;
 }
 
 public DonationStatusUpdateDTO() {}
 
 // Getters and setters
 public Long getDonationId() {
     return donationId;
 }

 public void setDonationId(Long donationId) {
     this.donationId = donationId;
 }

 public DonationStatus getStatus() {
     return status;
 }

 public void setStatus(DonationStatus status) {
     this.status = status;
 }
}
