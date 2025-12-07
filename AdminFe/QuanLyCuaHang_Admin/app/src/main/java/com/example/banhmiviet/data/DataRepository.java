package com.example.banhmiviet.data;

import com.example.banhmiviet.R;
import com.example.banhmiviet.model.Customer;
import com.example.banhmiviet.model.Employee;
import com.example.banhmiviet.model.InventoryItem;
import com.example.banhmiviet.model.Order;
import com.example.banhmiviet.model.Product;
import com.example.banhmiviet.model.RevenuePoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataRepository {

    private static DataRepository instance;

    private final List<Product> products = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<Employee> employees = new ArrayList<>();
    private final List<InventoryItem> inventoryItems = new ArrayList<>();
    private final List<RevenuePoint> revenuePoints = new ArrayList<>();

    private DataRepository() {
        mockProducts();
        mockOrders();
        mockCustomers();
        mockEmployees();
        mockInventory();
        mockRevenue();
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            instance = new DataRepository();
        }
        return instance;
    }

    // ========= MOCK DATA =========
    private void mockProducts() {
        products.clear();
        products.add(new Product("P01", "Bánh mì thịt", 15000,
                "Bánh mì thịt truyền thống", "Bánh mì", R.drawable.ic_launcher_foreground));
        products.add(new Product("P02", "Hamburger bò", 35000,
                "Hamburger bò phô mai", "Hamburger", R.drawable.ic_launcher_foreground));
        products.add(new Product("P03", "Pizza phô mai", 69000,
                "Pizza phô mai 4 loại", "Pizza", R.drawable.ic_launcher_foreground));
    }

    private void mockOrders() {
        orders.clear();
        long now = System.currentTimeMillis();

        orders.add(new Order(
                "O01",
                "1x Bánh mì thịt, 1x Trà sữa",
                27000,
                Order.TYPE_AT_TABLE,
                1,
                Order.STATUS_NEW,
                now - 60 * 60 * 1000,
                "Nguyễn Văn A"
        ));

        orders.add(new Order(
                "O02",
                "2x Hamburger bò",
                70000,
                Order.TYPE_TAKE_AWAY,
                null,
                Order.STATUS_NEW,
                now - 30 * 60 * 1000,
                "Trần Thị B"
        ));
    }

    private void mockCustomers() {
        customers.clear();
        customers.add(new Customer("C01", "Hoàng", "0909 999 999", 5, 350000));
        customers.add(new Customer("C02", "Lan", "0904 444 444", 2, 120000));
    }

    private void mockEmployees() {
        employees.clear();
        employees.add(new Employee("E01", "Nguyễn Văn A", "Quản lý",
                "0901 111 111", "a@sapo.com", 28, "Nam",
                "manager01", "123456"));
        employees.add(new Employee("E02", "Trần Thị B", "Thu ngân",
                "0902 222 222", "b@sapo.com", 23, "Nữ",
                "cashier01", "123456"));
        employees.add(new Employee("E03", "Hoàng Văn C", "Bán hàng",
                "0934 333 333", "c@sapo.com", 25, "Nam",
                "seller01", "123456"));
    }

    private void mockInventory() {
        inventoryItems.clear();
        inventoryItems.add(new InventoryItem("P01", "Bánh mì thịt", 100));
        inventoryItems.add(new InventoryItem("P02", "Hamburger bò", 30));
        inventoryItems.add(new InventoryItem("P03", "Pizza phô mai", 10));
    }

    private void mockRevenue() {
        revenuePoints.clear();
        revenuePoints.add(new RevenuePoint("D1", 28000));
        revenuePoints.add(new RevenuePoint("D2", 41000));
        revenuePoints.add(new RevenuePoint("D3", 0));
        revenuePoints.add(new RevenuePoint("D4", 0));
        revenuePoints.add(new RevenuePoint("D5", 0));
        revenuePoints.add(new RevenuePoint("D6", 0));
    }

    // ========= GETTERS =========

    public List<Product> getProducts()     { return new ArrayList<>(products); }
    public List<Order> getOrders()         { return new ArrayList<>(orders); }
    public List<Customer> getCustomers()   { return new ArrayList<>(customers); }
    public List<Employee> getEmployees()   { return new ArrayList<>(employees); }
    public List<RevenuePoint> getRevenuePoints() { return new ArrayList<>(revenuePoints); }

    public List<InventoryItem> getInventoryItems() {
        return new ArrayList<>(inventoryItems);
    }

    // ========= ORDER ACTIONS =========

    public void addOrder(Order order) {
        orders.add(order);

        revenuePoints.add(new RevenuePoint(
                "D" + (revenuePoints.size() + 1),
                order.getTotalPrice()
        ));
    }

    public void addOrderWithDetails(Order order, Map<Product, Integer> selectedProducts) {
        addOrder(order);

        if (selectedProducts != null) {
            for (Product product : selectedProducts.keySet()) {
                int qty = selectedProducts.get(product);
                decreaseStock(product.getId(), qty);
            }
        }
    }

    public void removeOrder(int index) {
        if (index >= 0 && index < orders.size()) {
            orders.remove(index);
        }
    }

    // ========= INVENTORY =========

    private InventoryItem findInventoryByProductId(String id) {
        for (InventoryItem i : inventoryItems) {
            if (i.getProductId().equals(id)) return i;
        }
        return null;
    }

    public void decreaseStock(String productId, int quantity) {
        InventoryItem item = findInventoryByProductId(productId);
        if (item != null) {
            int newQty = item.getQuantity() - quantity;
            item.setQuantity(Math.max(newQty, 0));
        }
    }

    public void increaseStock(String productId, int quantity) {
        InventoryItem item = findInventoryByProductId(productId);
        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        }
    }

    // ========= EMPLOYEE =========

    public void addEmployee(Employee e) { employees.add(e); }

    public void removeEmployee(int index) {
        if (index >= 0 && index < employees.size()) {
            employees.remove(index);
        }
    }

    public void updateEmployee(int index, Employee e) {
        if (index >= 0 && index < employees.size()) {
            employees.set(index, e);
        }
    }

    // ========= PRODUCT ACTION =========
    public void addProduct(Product p) { products.add(p); }

    public void updateProduct(int index, Product p) {
        if (index >= 0 && index < products.size()) {
            products.set(index, p);
        }
    }

    // ========= SUMMARY =========

    public double getTotalRevenue() {
        double sum = 0;
        for (Order o : orders) sum += o.getTotalPrice();
        return sum;
    }

    public int getTotalOrderCount() { return orders.size(); }
    public int getTotalProductCount() { return products.size(); }
    public int getTotalCustomerCount() { return customers.size(); }
    public int getTotalEmployeeCount() { return employees.size(); }
}
