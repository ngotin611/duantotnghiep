package com.example.foodbe.controllers;


import com.example.foodbe.request.product.CreateProductDTO;
import com.example.foodbe.response.product.ProductResponseDTO;
import com.example.foodbe.request.product.UpdateProductDTO;
import com.example.foodbe.payload.ApiResponse;
import com.example.foodbe.services.ProductService;
import com.example.foodbe.utils.ConstantUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAll(){
        return ResponseEntity.ok(ApiResponse.success(productService.findAll()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getById( @PathVariable Long id){
        return ResponseEntity.ok(ApiResponse.success(productService.findById(id)));
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> create(@Valid @RequestBody CreateProductDTO createProductDTO){
        return ResponseEntity.ok(ApiResponse.success(productService.create(createProductDTO)));
    }

    @PreAuthorize("hasRole('Admin')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> updateById(@PathVariable Long id, @Valid @RequestBody UpdateProductDTO updateProductDTO){
        return ResponseEntity.ok(ApiResponse.success(productService.updateById(id, updateProductDTO)));
    }

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteById(@PathVariable Long id){
        productService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success(ConstantUtils.DELETE_SUCCESSFULLY +id));
    }

}
