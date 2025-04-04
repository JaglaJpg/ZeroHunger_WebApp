package com.example.zerohunger.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.zerohunger.Entity.AppliancesDonation;

public interface ApplianceDonationRepository extends JpaRepository<AppliancesDonation, Long> {
	List <AppliancesDonation> findByIsAvailableTrue();
	List <AppliancesDonation> findByDonor_UserID(Long userId);

}