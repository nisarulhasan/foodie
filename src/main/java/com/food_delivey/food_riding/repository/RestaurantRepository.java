package com.food_delivey.food_riding.repository;

import com.food_delivey.food_riding.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByApprovedTrue(); // Only fetch approved restaurants

    List<Restaurant> findByApprovedFalse();
}