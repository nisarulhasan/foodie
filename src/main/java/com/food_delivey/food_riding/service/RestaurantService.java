package com.food_delivey.food_riding.service;

import com.food_delivey.food_riding.model.Restaurant;
import com.food_delivey.food_riding.model.Role;
import com.food_delivey.food_riding.model.User;
import com.food_delivey.food_riding.repository.RestaurantRepository;
import com.food_delivey.food_riding.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    @Transactional
    public Restaurant createRestaurant(Restaurant restaurant, String ownerEmail) {
        // Fetch the owner from the database
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        // Ensure the user has the RESTAURANT_OWNER role
        if (owner.getRole() != Role.RESTAURANT_OWNER) {
            throw new RuntimeException("User is not a restaurant owner");
        }

        // Set the owner and save the restaurant
        restaurant.setOwner(owner);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllApprovedRestaurants() {
        return (List<Restaurant>) restaurantRepository.findByApprovedTrue();
    }

    public Restaurant getRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    @Transactional
    public void approveRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        restaurant.setApproved(true);
        restaurantRepository.save(restaurant);
    }

    public Restaurant getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }
}