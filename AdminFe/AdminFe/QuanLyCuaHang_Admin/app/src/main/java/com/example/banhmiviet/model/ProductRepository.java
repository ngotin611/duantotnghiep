package com.example.banhmiviet.model;

import java.util.ArrayList;
import com.example.banhmiviet.R;
import java.util.List;

public class ProductRepository {
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(
                "P01",
                "Mặt hàng 1",
                15000,
                "Ngon đặc biệt",
                "Loại A",
                R.drawable.ic_launcher_foreground

        ));
        products.add(new Product(
                "P02",
                "Mặt hàng 2",
                20000,
                "SP đặt biệt",
                "Loại B",
                R.drawable.ic_launcher_foreground

        ));

        products.add(new Product(
                "P03",
                "Mặt hàng 3",
                45000,
                "Sản phẩm hot",
                "Loại C",
                R.drawable.ic_launcher_foreground

        ));

        return products;
    }
}
