package com.food_delivey.food_riding.controller;

import com.food_delivey.food_riding.model.MenuItem;
import com.food_delivey.food_riding.model.Restaurant;
import com.food_delivey.food_riding.service.MenuService;
import com.food_delivey.food_riding.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/restaurants/{restaurantId}/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final RestaurantService restaurantService; // Inject RestaurantService

    @PostMapping
    @PreAuthorize("hasRole('RESTAURANT_OWNER')")
    public ResponseEntity<MenuItem> addMenuItem(@PathVariable Long restaurantId,
                                                @RequestBody MenuItem menuItem) {
        // Fetch the restaurant from the database
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        // Set the restaurant in the menu item
        menuItem.setRestaurant(restaurant);

        // Save the menu item
        return ResponseEntity.ok(menuService.addMenuItem(menuItem));
    }

    @GetMapping
    public ResponseEntity<List<MenuItem>> getMenu(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(menuService.getMenuByRestaurant(restaurantId));
    }
}