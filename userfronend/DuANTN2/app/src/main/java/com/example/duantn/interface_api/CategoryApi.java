package com.example.duantn.interface_api;

import com.example.duantn.models.CategoryDomain;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryApi {

    // Lấy tất cả danh mục
    @GET("/api/categories")
    Call<List<CategoryDomain>> getAllCategories();

    // Lấy danh mục theo ID
    @GET("/api/categories/{id}")
    Call<CategoryDomain> getCategoryById(@Path("id") Long id);
}

