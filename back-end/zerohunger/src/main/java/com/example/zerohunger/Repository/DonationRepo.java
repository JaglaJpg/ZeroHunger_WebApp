package com.example.zerohunger.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.zerohunger.Entity.DonationStatus;
import com.example.zerohunger.Entity.OngoingDonations;

@Repository
public interface DonationRepo extends JpaRepository<OngoingDonations, Long> {
	
	@Modifying
	@Query("UPDATE OngoingDonations o SET o.status = :status WHERE o.donationID = :ID")
	void updateStatus(@Param("status") DonationStatus status, @Param("ID") Long ID);
	
	@Query("SELECT o FROM OngoingDonations o WHERE o.donor.id = :id OR o.recipient.id = :id")
	List<OngoingDonations> listDonations(@Param("id") Long id);

}
