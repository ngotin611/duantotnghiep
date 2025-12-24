package com.example.duantn.models.product;

import java.math.BigDecimal;

public class CreateProduct {

    private String name;
    private int quantity;
    private BigDecimal price;
    private String imgProduct;
    private String description;
    private Long categoryId;

    // Constructor không tham số
    public CreateProduct() {}

    // Constructor đầy đủ
    public CreateProduct(String name, int quantity, BigDecimal price, String imgProduct, String description, Long categoryId) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.imgProduct = imgProduct;
        this.description = description;
        this.categoryId = categoryId;
    }

    // Getter và Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getImgProduct() { return imgProduct; }
    public void setImgProduct(String imgProduct) { this.imgProduct = imgProduct; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
}
