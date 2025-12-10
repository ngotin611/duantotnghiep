package com.example.banhmiviet.model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    public static final String TYPE_AT_TABLE   = "AT_TABLE";
    public static final String TYPE_TAKE_AWAY  = "TAKE_AWAY";

    public static final String STATUS_NEW       = "NEW";        // mới tạo, chưa tất toán
    public static final String STATUS_PAID      = "PAID";       // đã tất toán
    public static final String STATUS_CANCELLED = "CANCELLED";  // đã hủy

    private String id;
    private String description;
    private double totalPrice;

    private String type;        // AT_TABLE / TAKE_AWAY
    private Integer tableNumber; // null nếu mang đi
    private String status;      // NEW / PAID / CANCELLED
    private long createdTime;   // millis
    private String createdBy;   // tên nhân viên order

    // 🆕 lưu danh sách món trong đơn (để trừ kho chính xác)
    private List<CartItem> items;

    // ================= CONSTRUCTOR CŨ 3 THAM SỐ =================
    // Dùng cho code cũ / mock đơn giản
    public Order(String id, String description, double totalPrice) {
        this(
                id,
                description,
                totalPrice,
                TYPE_AT_TABLE,
                null,
                STATUS_NEW,
                System.currentTimeMillis(),
                "Admin",
                null
        );
    }

    // ================= CONSTRUCTOR 8 THAM SỐ (GIỮ TƯƠNG THÍCH) =================
    // ⚠️ cái này chính là constructor mà OrderController, DataRepository đang gọi
    public Order(String id,
                 String description,
                 double totalPrice,
                 String type,
                 Integer tableNumber,
                 String status,
                 long createdTime,
                 String createdBy) {
        this(id, description, totalPrice,
                type, tableNumber,
                status, createdTime,
                createdBy, null);
    }

    // ================= CONSTRUCTOR ĐẦY ĐỦ 9 THAM SỐ =================
    public Order(String id,
                 String description,
                 double totalPrice,
                 String type,
                 Integer tableNumber,
                 String status,
                 long createdTime,
                 String createdBy,
                 List<CartItem> items) {

        this.id          = id;
        this.description = description;
        this.totalPrice  = totalPrice;
        this.type        = type;
        this.tableNumber = tableNumber;
        this.status      = status;
        this.createdTime = createdTime;
        this.createdBy   = createdBy;
        this.items       = items != null ? new ArrayList<>(items) : new ArrayList<>();
    }

    // ================= GETTER / SETTER =================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
    }
}
