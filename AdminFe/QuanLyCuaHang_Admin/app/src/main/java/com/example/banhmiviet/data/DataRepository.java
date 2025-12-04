package com.example.banhmiviet.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo repository: thay bằng API / Room / SQLite thực tế sau này.
 */
public class DataRepository {

    private static DataRepository instance;

    // Demo lists
    private final List<Order> orders = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();

    private DataRepository() {
        // populate demo data
        products.add(new Product("mặt hàng 1", 15000));
        products.add(new Product("mặt hàng 2", 12000));
        products.add(new Product("mặt hàng 3", 10000));

        customers.add(new Customer("Người A"));
        customers.add(new Customer("Người B"));

        // demo orders
        Order o1 = new Order("Đơn #001");
        o1.addItem(products.get(0), 1);
        o1.addItem(products.get(1), 1);
        orders.add(o1);

        Order o2 = new Order("Đơn #002");
        o2.addItem(products.get(0), 2);
        o2.addItem(products.get(2), 1);
        orders.add(o2);
    }

    public static synchronized DataRepository getInstance() {
        if (instance == null) instance = new DataRepository();
        return instance;
    }

    // summary getters
    public double getTotalRevenue() {
        double sum = 0;
        for (Order o : orders) sum += o.getTotalAmount();
        return sum;
    }

    public int getOrdersCount() { return orders.size(); }
    public int getCustomersCount() { return customers.size(); }
    public int getProductsCount() { return products.size(); }

    // getters for lists (for detail screens)
    public List<Order> getOrders() { return orders; }
    public List<Product> getProducts() { return products; }
    public List<Customer> getCustomers() { return customers; }

    // --- Simple inner demo classes (move to separate files if muốn)
    public static class Product {
        public final String name;
        public final double price;
        public Product(String name, double price) { this.name = name; this.price = price; }
    }
    public static class Customer {
        public final String name;
        public Customer(String name) { this.name = name; }
    }
    public static class Order {
        public final String id;
        private final List<Item> items = new ArrayList<>();
        public Order(String id) { this.id = id; }
        public void addItem(Product p, int qty) { items.add(new Item(p, qty)); }
        public double getTotalAmount() {
            double s=0; for (Item it: items) s += it.product.price * it.qty; return s;
        }
        public List<Item> getItems(){ return items; }
        public static class Item { public final Product product; public final int qty; Item(Product p,int q){product=p;qty=q;} }
    }
}
