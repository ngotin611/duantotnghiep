package com.example.banhmiviet.model;

public class Product {

    private String id;
    private String name;
    private double price;
    private String description;
    private String category;
    private int imageResId;    // icon mặc định (mock)
    private String imageUri;   // ảnh chọn từ thiết bị (content://...)

    // Constructor đang dùng trong DataRepository (mock)
    public Product(String id, String name, double price,
                   String description, String category, int imageResId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.imageResId = imageResId;
    }

    // Constructor mới có thêm imageUri
    public Product(String id, String name, double price,
                   String description, String category, int imageResId,
                   String imageUri) {
        this(id, name, price, description, category, imageResId);
        this.imageUri = imageUri;
    }

    // Constructor tiện cho tạo mới từ UI (chưa cần id, không dùng imageRes)
    public Product(String name, double price, String description,
                   String category, String imageUri) {
        this(null, name, price, description, category, 0, imageUri);
    }

    // ===== Getter/Setter =====
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getImageResId() { return imageResId; }
    public void setImageResId(int imageResId) { this.imageResId = imageResId; }

    public String getImageUri() { return imageUri; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }
}
