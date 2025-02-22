package com.food_delivey.food_riding.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User customer; // User who placed the order

    @ManyToOne
    private Restaurant restaurant; // Restaurant for the order

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>(); // List of items in the order

    private BigDecimal totalAmount;
    private OrderStatus status = OrderStatus.PLACED;

    public enum OrderStatus {
        PLACED, CONFIRMED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    }
}