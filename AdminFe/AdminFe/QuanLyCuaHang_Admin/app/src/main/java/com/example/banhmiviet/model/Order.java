package com.example.banhmiviet.model;

public class Order {

    public static final String TYPE_AT_TABLE = "AT_TABLE";
    public static final String TYPE_TAKE_AWAY = "TAKE_AWAY";

    public static final String STATUS_NEW = "NEW";          // mới tạo, chưa tất toán
    public static final String STATUS_PAID = "PAID";        // đã tất toán
    public static final String STATUS_CANCELLED = "CANCELLED"; // đã hủy

    private String id;
    private String description;
    private double totalPrice;

    private String type;        // AT_TABLE / TAKE_AWAY
    private Integer tableNumber; // null nếu mang đi
    private String status;      // NEW / PAID / CANCELLED
    private long createdTime;   // millis
    private String createdBy;   // tên nhân viên order

    // Constructor cũ – để code cũ vẫn chạy
    public Order(String id, String description, double totalPrice) {
        this(id, description, totalPrice,
                TYPE_AT_TABLE, null,
                STATUS_NEW, System.currentTimeMillis(),
                "Admin");
    }

    // Constructor đầy đủ
    public Order(String id, String description, double totalPrice,
                 String type, Integer tableNumber,
                 String status, long createdTime,
                 String createdBy) {
        this.id = id;
        this.description = description;
        this.totalPrice = totalPrice;
        this.type = type;
        this.tableNumber = tableNumber;
        this.status = status;
        this.createdTime = createdTime;
        this.createdBy = createdBy;
    }

    // ===== Getter/Setter =====
    public String getId() { return id; }
    public String getDescription() { return description; }
    public double getTotalPrice() { return totalPrice; }
    public String getType() { return type; }
    public Integer getTableNumber() { return tableNumber; }
    public String getStatus() { return status; }
    public long getCreatedTime() { return createdTime; }
    public String getCreatedBy() { return createdBy; }

    public void setId(String id) { this.id = id; }
    public void setDescription(String description) { this.description = description; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setType(String type) { this.type = type; }
    public void setTableNumber(Integer tableNumber) { this.tableNumber = tableNumber; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedTime(long createdTime) { this.createdTime = createdTime; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}
