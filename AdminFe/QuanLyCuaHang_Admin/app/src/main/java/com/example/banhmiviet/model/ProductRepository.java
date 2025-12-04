package com.example.banhmiviet.model;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("mặt hàng 1", 15000, "ngon3"));
        products.add(new Product("mặt hàng 2", 12000, "ngon2"));
        products.add(new Product("mặt hàng 3", 10000, "nggon"));
        return products;
    }
}
