package com.example.banhmiviet.data;

import com.example.banhmiviet.R;
import com.example.banhmiviet.model.Product;

import java.util.ArrayList;
import java.util.List;

public class    ProductRepository {

    private static ProductRepository instance;
    private final List<Product> products = new ArrayList<>();

    private ProductRepository() {
        mockData();
    }

    public static ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }
        return instance;
    }

    private void mockData() {
        products.add(new Product("P01", "Bánh mì thịt", 15000, "Bánh mì thịt truyền thống", "Bánh mì", R.drawable.ic_launcher_foreground));
        products.add(new Product("P02", "Hamburger bò", 35000, "Hamburger bò phô mai", "Hamburger", R.drawable.ic_launcher_foreground));
        products.add(new Product("P03", "Pizza phô mai", 69000, "Pizza phô mai 4 loại", "Pizza", R.drawable.ic_launcher_foreground));
        products.add(new Product("P04", "Trà sữa trân châu", 25000, "Trà sữa trân châu đường đen", "Đồ uống", R.drawable.ic_launcher_foreground));
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public int getTotalProductCount() {
        return products.size();
    }
}
