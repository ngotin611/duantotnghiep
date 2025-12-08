package com.example.banhmiviet.data;

import com.example.banhmiviet.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private static OrderRepository instance;
    private final List<Order> orders = new ArrayList<>();

    private OrderRepository() {
        mockData();
    }

    public static OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    private void mockData() {
        orders.add(new Order("O01", "1x Bánh mì thịt, 1x Trà sữa", 27000));
        orders.add(new Order("O02", "2x Hamburger bò", 70000));
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public int getTotalOrderCount() {
        return orders.size();
    }

    public double getTotalRevenue() {
        double total = 0;
        for (Order o : orders) {
            total += o.getTotalPrice();
        }
        return total;
    }
}
