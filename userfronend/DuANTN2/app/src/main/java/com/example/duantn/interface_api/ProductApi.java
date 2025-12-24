package com.example.duantn.interface_api;

import com.example.duantn.models.page.PageResponse;
import com.example.duantn.models.product.CreateProduct;
import com.example.duantn.models.product.ResponseProduct;
import com.example.duantn.models.product.UpdateProduct;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface ProductApi {

    // Lấy danh sách sản phẩm có phân trang
    @GET("/api/products")
    Call<PageResponse<ResponseProduct>> getProducts(
            @Query("page") int page,   // số trang, bắt đầu từ 0
            @Query("size") int size,   // số phần tử mỗi trang
            @Query("sort") String sort // sắp xếp (ví dụ: "id,asc" hoặc "name,desc")
    );


}
