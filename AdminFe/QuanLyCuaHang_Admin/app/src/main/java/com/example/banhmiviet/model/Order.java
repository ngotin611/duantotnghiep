package com.example.banhmiviet.model;

public class Order {
    private String id;
    private String description;
    private double totalPrice;

    public Order(String id, String description) {
        this.id = id;
        this.description = description;
        this.totalPrice = 0;
    }

    // ✅ Constructor mới — để nhận thêm totalPrice
    public Order(String id, String description, double totalPrice) {
        this.id = id;
        this.description = description;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
