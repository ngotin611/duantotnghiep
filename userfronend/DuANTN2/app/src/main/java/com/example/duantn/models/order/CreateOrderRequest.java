package com.example.duantn.models.order;

import java.util.List;

/**
 * Model để gửi request tạo đơn hàng lên backend
 */
public class CreateOrderRequest {
    private String tableNumber;
    private String orderType; // "Tại quán" hoặc "Mang đi"
    private String paymentMethod; // "Tiền mặt", "Thẻ tín dụng/ghi nợ", "Ví MoMo"
    private String note;
    private List<OrderItemRequest> items; // Danh sách món ăn trong đơn

    // Constructor
    public CreateOrderRequest() {}

    public CreateOrderRequest(String tableNumber, String orderType, String paymentMethod, String note, List<OrderItemRequest> items) {
        this.tableNumber = tableNumber;
        this.orderType = orderType;
        this.paymentMethod = paymentMethod;
        this.note = note;
        this.items = items;
    }

    // Getter và Setter
    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}

