package com.example.banhmiviet.data;

import com.example.banhmiviet.model.InventoryItem;

import java.util.ArrayList;
import java.util.List;

public class InventoryRepository {

    private static InventoryRepository instance;
    private final List<InventoryItem> items = new ArrayList<>();

    private InventoryRepository() {
        mockData();
    }

    public static InventoryRepository getInstance() {
        if (instance == null) {
            instance = new InventoryRepository();
        }
        return instance;
    }

    private void mockData() {
        items.add(new InventoryItem("P01", "Bánh mì thịt", 100));
        items.add(new InventoryItem("P02", "Hamburger bò", 30));
        items.add(new InventoryItem("P03", "Pizza phô mai", 10));  // sắp hết
    }

    public List<InventoryItem> getItems() {
        return new ArrayList<>(items);
    }
}
