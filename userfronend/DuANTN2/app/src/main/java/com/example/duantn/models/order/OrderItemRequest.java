package com.example.duantn.models.order;

/**
 * Model cho từng món ăn trong đơn hàng
 */
public class OrderItemRequest {
    private Long productId; // ID sản phẩm từ backend
    private String productName; // Tên sản phẩm
    private int quantity; // Số lượng
    private double price; // Giá mỗi sản phẩm

    // Constructor
    public OrderItemRequest() {}

    public OrderItemRequest(Long productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getter và Setter
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

