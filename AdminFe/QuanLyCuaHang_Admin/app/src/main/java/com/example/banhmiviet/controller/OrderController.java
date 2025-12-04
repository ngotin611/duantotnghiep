package com.example.banhmiviet.controller;

import com.example.banhmiviet.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderController {
    private final List<Order> orders;

    public OrderController() {
        orders = new ArrayList<>();
        orders.add(new Order("001", "1x mặt hàng 1, 1x mặt hàng 2"));
        orders.add(new Order("002", "2x mặt hàng 1, 1x mặt hàng 3"));
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
}
