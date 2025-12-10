package com.example.banhmiviet.data;

import com.example.banhmiviet.R;
import com.example.banhmiviet.model.CartItem;
import com.example.banhmiviet.model.Customer;
import com.example.banhmiviet.model.Employee;
import com.example.banhmiviet.model.InventoryItem;
import com.example.banhmiviet.model.Order;
import com.example.banhmiviet.model.Product;
import com.example.banhmiviet.model.RevenuePoint;

import java.util.ArrayList;
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
        mockInventory();
        mockOrders();
        mockCustomers();
        mockEmployees();
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
                "Bánh mì thịt truyền thống", "Bánh mì", R.drawable.image));
        products.add(new Product("P02", "Hamburger bò", 35000,
                "Hamburger bò phô mai", "Hamburger", R.drawable.image));
        products.add(new Product("P03", "Pizza phô mai", 69000,
                "Pizza phô mai 4 loại", "Pizza", R.drawable.image));
    }

    private void mockInventory() {
        inventoryItems.clear();
        inventoryItems.add(new InventoryItem("P01", "Bánh mì thịt", 100));
        inventoryItems.add(new InventoryItem("P02", "Hamburger bò", 30));
        inventoryItems.add(new InventoryItem("P03", "Pizza phô mai", 10));
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
                "Nguyễn Văn A",
                null   // demo chưa có items chi tiết
        ));

        orders.add(new Order(
                "O02",
                "2x Hamburger bò",
                70000,
                Order.TYPE_TAKE_AWAY,
                null,
                Order.STATUS_NEW,
                now - 30 * 60 * 1000,
                "Trần Thị B",
                null
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

    // Tìm product theo id để dùng ở Kho
    public Product findProductById(String id) {
        if (id == null) return null;
        for (Product p : products) {
            if (id.equals(p.getId())) return p;
        }
        return null;
    }

    // ========= ORDER ACTIONS =========

    public void addOrder(Order order) {
        orders.add(order);

        // mock revenue theo đơn
        revenuePoints.add(new RevenuePoint(
                "D" + (revenuePoints.size() + 1),
                order.getTotalPrice()
        ));
    }

    // Giữ để dùng chỗ cũ nếu muốn, nhưng hiện tại bạn chọn trừ kho LÚC THANH TOÁN
    public void addOrderWithDetails(Order order, Map<Product, Integer> selectedProducts) {
        addOrder(order);
        // KHÔNG trừ kho tại đây nữa
    }

    public void removeOrder(int index) {
        if (index >= 0 && index < orders.size()) {
            orders.remove(index);
        }
    }

    // 🆕 Trừ kho khi thanh toán đơn
    // ========= APPLY STOCK WHEN ORDER PAID =========
    public void applyStockForPaidOrder(Order order) {
        if (order == null) return;

        // Chỉ trừ kho nếu đơn đang ở trạng thái NEW
        if (!Order.STATUS_NEW.equals(order.getStatus())) {
            return;
        }

        String desc = order.getDescription();
        if (desc == null || desc.trim().isEmpty()) return;

        // Lấy danh sách sản phẩm để map tên -> id
        List<Product> productList = getProducts();

        // Chuỗi mô tả dạng: "2x Bánh mì thịt, 1x Coca"
        String[] parts = desc.split(",");
        for (String part : parts) {
            String itemStr = part.trim();
            if (itemStr.isEmpty()) continue;

            int qty = 1;
            String nameText = itemStr;

            int xIndex = itemStr.indexOf("x");
            if (xIndex > 0) {
                // bên trái "x" là số lượng
                String qtyStr = itemStr.substring(0, xIndex).trim();
                try {
                    qty = Integer.parseInt(qtyStr);
                } catch (NumberFormatException e) {
                    qty = 1;
                }
                // bên phải "x" là tên món
                nameText = itemStr.substring(xIndex + 1).trim();
            }

            String normalizedName = nameText.toLowerCase();

            // Tìm product có tên khớp để trừ kho theo id
            for (Product p : productList) {
                if (p.getName() == null) continue;
                if (p.getName().trim().toLowerCase().equals(normalizedName)) {
                    // Trừ kho theo productId
                    decreaseStock(p.getId(), qty);
                    break;
                }
            }
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
    public void addProduct(Product p) {
        products.add(p);
        // 🆕 sản phẩm mới -> kho cũng có 1 dòng với tồn = 0
        inventoryItems.add(new InventoryItem(
                p.getId(),
                p.getName(),
                0
        ));
    }

    public void updateProduct(int index, Product p) {
        if (index >= 0 && index < products.size()) {
            products.set(index, p);
        }
        // Cập nhật tên trong Inventory nếu trùng id
        InventoryItem item = findInventoryByProductId(p.getId());
        if (item != null) {
            item.setProductName(p.getName());
        }
    }

    // ========= SUMMARY =========

    public double getTotalRevenue() {
        double sum = 0;
        for (Order o : orders) sum += o.getTotalPrice();
        return sum;
    }

    public int getTotalOrderCount()    { return orders.size(); }
    public int getTotalProductCount()  { return products.size(); }
    public int getTotalCustomerCount() { return customers.size(); }
    public int getTotalEmployeeCount() { return employees.size(); }
}
