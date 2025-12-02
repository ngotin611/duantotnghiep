package com.example.duantn.models;

public class OrderDomain {
    private String orderId;
    private String foodName;
    private int quantity;
    private int price;
    private String status;
    private String orderDate;
    private String foodImage;
    private String deliveryAddress;
    private String paymentMethod;
    private String estimatedDelivery;

    // Constructor mặc định
    public OrderDomain() {
    }

    // Constructor đầy đủ tham số
    public OrderDomain(String orderId, String foodName, int quantity, int price,
                       String status, String orderDate, String foodImage,
                       String deliveryAddress, String paymentMethod, String estimatedDelivery) {
        this.orderId = orderId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.orderDate = orderDate;
        this.foodImage = foodImage;
        this.deliveryAddress = deliveryAddress;
        this.paymentMethod = paymentMethod;
        this.estimatedDelivery = estimatedDelivery;
    }

    // Constructor đơn giản (chỉ các thông tin cơ bản)
    public OrderDomain(String orderId, String foodName, int quantity, int price,
                       String status, String orderDate) {
        this.orderId = orderId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.orderDate = orderDate;
        this.foodImage = "";
        this.deliveryAddress = "";
        this.paymentMethod = "Tiền mặt";
        this.estimatedDelivery = "";
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getEstimatedDelivery() {
        return estimatedDelivery;
    }

    // Setters
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setEstimatedDelivery(String estimatedDelivery) {
        this.estimatedDelivery = estimatedDelivery;
    }

    // Method tính tổng tiền
    public int getTotalPrice() {
        return quantity * price;
    }

    // Method format giá tiền
    public String getFormattedPrice() {
        return String.format("%,d VNĐ", price);
    }

    public String getFormattedTotalPrice() {
        return String.format("%,d VNĐ", getTotalPrice());
    }

    // Method kiểm tra trạng thái
    public boolean isProcessing() {
        return "Đang xử lý".equals(status);
    }

    public boolean isDelivering() {
        return "Đang giao".equals(status);
    }

    public boolean isDelivered() {
        return "Đã giao".equals(status);
    }

    public boolean isCancelled() {
        return "Đã hủy".equals(status);
    }

    // Method lấy màu trạng thái
    public int getStatusColor() {
        switch (status) {
            case "Đang xử lý":
                return 0xFF2196F3; // Xanh dương
            case "Đang giao":
                return 0xFFFF9800; // Cam
            case "Đã giao":
                return 0xFF4CAF50; // Xanh lá
            case "Đã hủy":
                return 0xFFF44336; // Đỏ
            default:
                return 0xFF757575; // Xám
        }
    }

    // Method lấy icon trạng thái
    public String getStatusIcon() {
        switch (status) {
            case "Đang xử lý":
                return "⏳";
            case "Đang giao":
                return "��";
            case "Đã giao":
                return "✅";
            case "Đã hủy":
                return "❌";
            default:
                return "📋";
        }
    }

    @Override
    public String toString() {
        return "OrderDomain{" +
                "orderId='" + orderId + '\'' +
                ", foodName='" + foodName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}