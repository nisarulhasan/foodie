package com.food_delivey.food_riding.service;

import com.food_delivey.food_riding.dto.OrderItemRequest;
import com.food_delivey.food_riding.dto.OrderRequest;
import com.food_delivey.food_riding.model.*;
import com.food_delivey.food_riding.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantService restaurantService;
    private final MenuService menuService;

    @Transactional
    public Order createOrder(String customerEmail, OrderRequest request) {
        // Fetch the restaurant
        Restaurant restaurant = restaurantService.getRestaurant(request.getRestaurantId());

        // Create the order
        Order order = new Order();
        order.setCustomer(new User(customerEmail)); // Set customer using email
        order.setRestaurant(restaurant);

        // Calculate total amount and add items
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItemRequest itemRequest : request.getItems()) {
            // Fetch the menu item
            MenuItem menuItem = menuService.getMenuItem(itemRequest.getMenuItemId());

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setName(menuItem.getName());
            orderItem.setPrice(menuItem.getPrice());
            orderItem.setQuantity(itemRequest.getQuantity());

            // Add order item to the order
            order.getItems().add(orderItem);

            // Update total amount
            totalAmount = totalAmount.add(menuItem.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        }

        // Set total amount and save the order
        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersForUser(String email) {
        return orderRepository.findByCustomerEmail(email);
    }
}