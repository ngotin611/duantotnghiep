package com.example.foodbe.services;

import com.example.foodbe.request.review.CreateReviewDto;
import com.example.foodbe.response.review.ReviewResponseDto;
import com.example.foodbe.request.review.UpdateReviewDto;

import java.util.List;

public interface ReviewService {

    List<ReviewResponseDto> findAll();
    ReviewResponseDto create(CreateReviewDto createReviewDto);
    ReviewResponseDto updateById(Long id,UpdateReviewDto updateReviewDto);
    void deleteById(Long id);

}
