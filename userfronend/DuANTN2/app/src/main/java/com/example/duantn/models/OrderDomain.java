package com.example.duantn.models;

public class OrderDomain {

    private String orderId;
    private String foodName;
    private int quantity;
    private int price;
    private String status;
    private String orderDate;

    // ✅ Thêm cho nhân viên:
    private String tableNumber = "";     // VD: "Bàn 1"
    private String orderType = "Tại quán"; // "Tại quán" / "Mang đi"
    private String note = "";            // Ghi chú của khách/nhân viên

    private String foodImage;
    private String deliveryAddress;
    private String paymentMethod;
    private String estimatedDelivery;

    public OrderDomain() {
    }

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
    public String getOrderId() { return orderId; }
    public String getFoodName() { return foodName; }
    public int getQuantity() { return quantity; }
    public int getPrice() { return price; }
    public String getStatus() { return status; }
    public String getOrderDate() { return orderDate; }
    public String getFoodImage() { return foodImage; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getEstimatedDelivery() { return estimatedDelivery; }

    public String getTableNumber() { return tableNumber; }
    public String getOrderType() { return orderType; }
    public String getNote() { return note; }

    // Setters
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setFoodName(String foodName) { this.foodName = foodName; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(int price) { this.price = price; }
    public void setStatus(String status) { this.status = status; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
    public void setFoodImage(String foodImage) { this.foodImage = foodImage; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setEstimatedDelivery(String estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }

    public void setTableNumber(String tableNumber) { this.tableNumber = tableNumber; }
    public void setOrderType(String orderType) { this.orderType = orderType; }
    public void setNote(String note) { this.note = note; }

    // Tổng tiền
    public int getTotalPrice() {
        return quantity * price;
    }

    public String getFormattedPrice() {
        return String.format("%,d VNĐ", price);
    }

    public String getFormattedTotalPrice() {
        return String.format("%,d VNĐ", getTotalPrice());
    }

    // ✅ Trạng thái mới cho nhân viên
    // "Đang chuẩn bị" - "Đang phục vụ" - "Hoàn thành" - "Đã hủy"
    public boolean isPreparing() {
        return "Đang chuẩn bị".equals(status);
    }

    public boolean isServing() {
        return "Đang phục vụ".equals(status);
    }

    public boolean isCompleted() {
        return "Hoàn thành".equals(status);
    }

    public boolean isCancelled() {
        return "Đã hủy".equals(status);
    }

    public int getStatusColor() {
        switch (status) {
            case "Đang chuẩn bị":
                return 0xFFFF9800; // cam
            case "Đang phục vụ":
                return 0xFF2196F3; // xanh dương
            case "Hoàn thành":
                return 0xFF4CAF50; // xanh lá
            case "Đã hủy":
                return 0xFFF44336; // đỏ
            default:
                return 0xFF757575; // xám
        }
    }

    public String getStatusIcon() {
        switch (status) {
            case "Đang chuẩn bị":
                return "⏳";
            case "Đang phục vụ":
                return "🍽️";
            case "Hoàn thành":
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
                ", tableNumber='" + tableNumber + '\'' +
                ", orderType='" + orderType + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}