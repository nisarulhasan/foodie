package com.food_delivey.food_riding.repository;

import com.food_delivey.food_riding.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerEmail(String email); // Fetch orders by customer email
}