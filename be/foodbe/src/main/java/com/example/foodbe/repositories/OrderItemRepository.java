package com.example.foodbe.repositories;

import com.example.foodbe.models.OrderItem;
import com.example.foodbe.response.order_item.OrderItemResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long id);
}
