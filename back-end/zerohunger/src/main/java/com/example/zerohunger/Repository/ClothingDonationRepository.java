package com.example.zerohunger.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.zerohunger.Entity.ClothingDonation;



public interface ClothingDonationRepository extends JpaRepository<ClothingDonation, Long> {
	List <ClothingDonation> findByIsAvailableTrue();
	List<ClothingDonation> findByDonor_UserID(Long userId);

}