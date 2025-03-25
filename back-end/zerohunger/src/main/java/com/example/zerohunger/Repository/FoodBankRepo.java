package com.example.zerohunger.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.zerohunger.Entity.FoodBank;

@Repository
public interface FoodBankRepo extends JpaRepository<FoodBank, Long> {
    Optional<FoodBank> findByName(String name);
    
    boolean existsByName(String name);

	boolean existsByAddress(String address);
}

