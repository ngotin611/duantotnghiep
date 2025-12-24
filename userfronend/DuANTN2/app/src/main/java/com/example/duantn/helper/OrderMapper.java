package com.example.duantn.helper;

import com.example.duantn.models.FoodDomain;
import com.example.duantn.models.OrderDomain;
import com.example.duantn.models.order.CreateOrderRequest;
import com.example.duantn.models.order.OrderItemRequest;
import com.example.duantn.models.order.ResponseOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class để chuyển đổi giữa OrderDomain (local) và CreateOrderRequest/ResponseOrder (API)
 */
public class OrderMapper {

    /**
     * Chuyển đổi từ OrderDomain và danh sách FoodDomain sang CreateOrderRequest
     */
    public static CreateOrderRequest toCreateOrderRequest(OrderDomain order, List<FoodDomain> cartItems) {
        CreateOrderRequest request = new CreateOrderRequest();
        
        request.setTableNumber(order.getTableNumber());
        request.setOrderType(order.getOrderType());
        request.setPaymentMethod(order.getPaymentMethod());
        request.setNote(order.getNote());
        
        // Chuyển đổi danh sách món ăn
        List<OrderItemRequest> items = new ArrayList<>();
        for (FoodDomain food : cartItems) {
            OrderItemRequest item = new OrderItemRequest(
                    food.getProductId(),
                    food.getTitle(),
                    food.getNumberInCart(),
                    food.getFee()
            );
            items.add(item);
        }
        request.setItems(items);
        
        return request;
    }

    /**
     * Chuyển đổi từ ResponseOrder sang OrderDomain
     */
    public static OrderDomain toOrderDomain(ResponseOrder responseOrder) {
        OrderDomain order = new OrderDomain();
        
        order.setOrderId(responseOrder.getOrderId());
        order.setTableNumber(responseOrder.getTableNumber());
        order.setOrderType(responseOrder.getOrderType());
        order.setPaymentMethod(responseOrder.getPaymentMethod());
        order.setNote(responseOrder.getNote());
        order.setStatus(responseOrder.getStatus());
        order.setOrderDate(responseOrder.getOrderDate());
        order.setPrice((int) responseOrder.getTotalAmount());
        
        // Tạo foodName từ danh sách items
        if (responseOrder.getItems() != null && !responseOrder.getItems().isEmpty()) {
            StringBuilder foodNames = new StringBuilder();
            int totalQuantity = 0;
            for (com.example.duantn.models.order.OrderItemResponse item : responseOrder.getItems()) {
                if (foodNames.length() > 0) foodNames.append(", ");
                foodNames.append(item.getProductName()).append(" x").append(item.getQuantity());
                totalQuantity += item.getQuantity();
            }
            order.setFoodName(foodNames.toString());
            order.setQuantity(totalQuantity);
        }
        
        return order;
    }
}

