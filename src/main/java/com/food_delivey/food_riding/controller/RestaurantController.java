package com.food_delivey.food_riding.controller;

import com.food_delivey.food_riding.model.Restaurant;
import com.food_delivey.food_riding.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant,
                                                       @AuthenticationPrincipal UserDetails userDetails) {
        // Extract the owner's email from the JWT token
        String ownerEmail = userDetails.getUsername();
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurant, ownerEmail));
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllApprovedRestaurants());
    }

    @PatchMapping("/{restaurantId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> approveRestaurant(@PathVariable Long restaurantId) {
        restaurantService.approveRestaurant(restaurantId);
        return ResponseEntity.ok().build();
    }
}