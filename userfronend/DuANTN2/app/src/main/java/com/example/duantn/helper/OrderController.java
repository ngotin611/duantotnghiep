package com.example.duantn.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.duantn.models.OrderDomain;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OrderController {
    private Context context;
    private SharedPreferences preferences;
    private static final String ORDER_LIST_KEY = "OrderList";
    private Gson gson;

    public OrderController(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences("MyOrders", Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    // Lấy danh sách tất cả đơn hàng
    public ArrayList<OrderDomain> getAllOrders() {
        String json = preferences.getString(ORDER_LIST_KEY, null);
        if (json == null || json.isEmpty()) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<OrderDomain>>() {}.getType();
        ArrayList<OrderDomain> orders = gson.fromJson(json, type);
        return orders != null ? orders : new ArrayList<>();
    }

    // Thêm đơn hàng mới
    public void addOrder(OrderDomain order) {
        ArrayList<OrderDomain> orders = getAllOrders();
        orders.add(order);
        saveOrders(orders);
        Toast.makeText(context, "Đã tạo đơn hàng #" + order.getOrderId(), Toast.LENGTH_SHORT).show();
    }

    // Cập nhật trạng thái đơn hàng
    public void updateOrderStatus(String orderId, String newStatus) {
        ArrayList<OrderDomain> orders = getAllOrders();
        for (OrderDomain order : orders) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus(newStatus);
                saveOrders(orders);
                return;
            }
        }
    }

    // Xóa đơn hàng
    public void deleteOrder(String orderId) {
        ArrayList<OrderDomain> orders = getAllOrders();
        orders.removeIf(order -> order.getOrderId().equals(orderId));
        saveOrders(orders);
    }

    // Lưu danh sách đơn hàng
    private void saveOrders(ArrayList<OrderDomain> orders) {
        String json = gson.toJson(orders);
        preferences.edit().putString(ORDER_LIST_KEY, json).apply();
    }

    // Tạo mã đơn hàng tự động
    public String generateOrderId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        return "DH" + sdf.format(new Date());
    }

    // Lọc đơn hàng theo trạng thái
    public ArrayList<OrderDomain> getOrdersByStatus(String status) {
        ArrayList<OrderDomain> allOrders = getAllOrders();
        ArrayList<OrderDomain> filtered = new ArrayList<>();
        for (OrderDomain order : allOrders) {
            if (order.getStatus().equals(status)) {
                filtered.add(order);
            }
        }
        return filtered;
    }

    // Lấy đơn hàng đã thanh toán (Hoàn thành) theo bàn
    public ArrayList<OrderDomain> getPaidOrdersByTable(String tableNumber) {
        ArrayList<OrderDomain> allOrders = getAllOrders();
        ArrayList<OrderDomain> filtered = new ArrayList<>();
        for (OrderDomain order : allOrders) {
            if (order.isCompleted() && order.getTableNumber().equals(tableNumber)) {
                filtered.add(order);
            }
        }
        return filtered;
    }

    // Lấy tất cả đơn hàng đã thanh toán (Hoàn thành)
    public ArrayList<OrderDomain> getAllPaidOrders() {
        return getOrdersByStatus("Hoàn thành");
    }
}