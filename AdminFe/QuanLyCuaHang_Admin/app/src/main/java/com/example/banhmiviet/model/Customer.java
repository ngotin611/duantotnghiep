package com.example.banhmiviet.model;

public class Customer {

    private String id;
    private String name;
    private String phone;
    private int orderCount;
    private double totalSpent;

    public Customer(String id, String name, String phone, int orderCount, double totalSpent) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.orderCount = orderCount;
        this.totalSpent = totalSpent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }
}
