package com.example.foodbe.services;

import com.example.foodbe.request.product.CreateProductDTO;
import com.example.foodbe.response.product.ProductResponseDTO;
import com.example.foodbe.request.product.UpdateProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> findAll();
    ProductResponseDTO findById(Long id);
    ProductResponseDTO create(CreateProductDTO createProductDTO);
    ProductResponseDTO updateById(Long id, UpdateProductDTO updateProductDTO);
    void deleteById(Long id);
}
