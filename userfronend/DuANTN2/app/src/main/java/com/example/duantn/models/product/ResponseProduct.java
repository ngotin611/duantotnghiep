package com.example.duantn.models.product;

import java.math.BigDecimal;

public class ResponseProduct {

    private Long id;
    private String name;
    private int quantity;
    private BigDecimal price;
    private String imgProduct;
    private String description;
    private Long idCategory;

    // Constructor không tham số
    public ResponseProduct() {}

    // Constructor đầy đủ
    public ResponseProduct(Long id, String name, int quantity, BigDecimal price, String imgProduct, String description, Long idCategory) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.imgProduct = imgProduct;
        this.description = description;
        this.idCategory = idCategory;
    }

    // Getter và Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Long getIdCategory() { return idCategory; }
    public void setIdCategory(Long idCategory) { this.idCategory = idCategory; }
}
