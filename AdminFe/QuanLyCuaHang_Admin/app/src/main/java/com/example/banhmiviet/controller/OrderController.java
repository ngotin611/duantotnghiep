package com.example.banhmiviet.controller;

import com.example.banhmiviet.data.DataRepository;
import com.example.banhmiviet.model.Order;

import java.util.List;

public class OrderController {

    private final DataRepository repo;

    public OrderController() {
        repo = DataRepository.getInstance();

        // Kiểm tra nếu chưa có mock orders thì mock
        if (repo.getOrders().isEmpty()) {
            mockData();
        }
    }

    private void mockData() {
        long now = System.currentTimeMillis();

        repo.addOrder(
                new Order(
                        "001",
                        "1x Hamburger bó, 1x Coca",
                        48000,
                        Order.TYPE_AT_TABLE,
                        1,
                        Order.STATUS_NEW,
                        now - 30 * 60 * 1000,
                        "Quản lý"
                )
        );

        repo.addOrder(
                new Order(
                        "002",
                        "2x Pizza phô mai",
                        138000,
                        Order.TYPE_TAKE_AWAY,
                        null,
                        Order.STATUS_NEW,
                        now - 5 * 60 * 1000,
                        "Thu ngân"
                )
        );
    }

    public List<Order> getOrders() {
        return repo.getOrders();
    }

    public void addOrder(Order order) {
        repo.addOrder(order);
    }

    public void updateOrderStatus(String orderId, String newStatus) {
        for (Order o : repo.getOrders()) {
            if (o.getId().equals(orderId)) {
                o.setStatus(newStatus);
                break;
            }
        }
    }
}
