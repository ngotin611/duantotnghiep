package com.example.banhmiviet.model;

public class SelectableProduct {
    private Product product;
    private boolean selected;
    private int quantity;

    public SelectableProduct(Product product) {
        this.product = product;
        this.selected = false;
        this.quantity = 1;
    }

    public Product getProduct() { return product; }
    public boolean isSelected() { return selected; }
    public int getQuantity() { return quantity; }

    public void setSelected(boolean selected) { this.selected = selected; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
