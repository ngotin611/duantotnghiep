package com.example.duantn.interface_api;

import com.example.duantn.models.order.CreateOrderRequest;
import com.example.duantn.models.order.ResponseOrder;
import com.example.duantn.models.page.PageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderApi {

    // Tạo đơn hàng mới
    @POST("/api/orders")
    Call<ResponseOrder> createOrder(@Body CreateOrderRequest orderRequest);

    // Lấy đơn hàng theo ID
    @GET("/api/orders/{id}")
    Call<ResponseOrder> getOrderById(@Path("id") Long id);

    // Lấy đơn hàng theo mã đơn hàng
    @GET("/api/orders/orderId/{orderId}")
    Call<ResponseOrder> getOrderByOrderId(@Path("orderId") String orderId);

    // Lấy tất cả đơn hàng (có phân trang)
    @GET("/api/orders")
    Call<PageResponse<ResponseOrder>> getAllOrders(
            @Query("page") int page,
            @Query("size") int size,
            @Query("sort") String sort
    );

    // Lấy đơn hàng theo bàn
    @GET("/api/orders/table/{tableNumber}")
    Call<List<ResponseOrder>> getOrdersByTable(@Path("tableNumber") String tableNumber);

    // Lấy đơn hàng theo trạng thái
    @GET("/api/orders/status/{status}")
    Call<List<ResponseOrder>> getOrdersByStatus(@Path("status") String status);

    // Lấy đơn hàng của người dùng hiện tại (nếu có authentication)
    @GET("/api/orders/my-orders")
    Call<List<ResponseOrder>> getMyOrders();
}

