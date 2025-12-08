package com.example.banhmiviet.controller;

import com.example.banhmiviet.model.Product;
import com.example.banhmiviet.model.ProductRepository;

import java.util.List;

public class ProductController {
    private ProductRepository repository;

    public ProductController() {
        repository = new ProductRepository();
    }

    public List<Product> getProducts() {
        return repository.getAllProducts();
    }
}
