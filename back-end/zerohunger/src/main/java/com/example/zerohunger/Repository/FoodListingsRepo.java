package com.example.zerohunger.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.zerohunger.Entity.FoodListings;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodListingsRepo extends JpaRepository<FoodListings, Long> {

}
