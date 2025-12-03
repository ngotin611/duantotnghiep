package com.example.foodbe.mapper;

import com.example.foodbe.request.review.CreateReviewDto;
import com.example.foodbe.response.review.ReviewResponseDto;
import com.example.foodbe.request.review.UpdateReviewDto;
import com.example.foodbe.models.AppUser;
import com.example.foodbe.models.Product;
import com.example.foodbe.models.Review;
import org.springframework.stereotype.Component;


@Component
public class ReviewMapper {
    public Review toEntity(CreateReviewDto createReviewDto, AppUser appUser, Product product){
        return Review.builder()
                .rating(createReviewDto.getRating())
                .comment(createReviewDto.getComment())
                .appUser(appUser)
                .product(product)
                .build();
    }

    public ReviewResponseDto toDto(Review review){
        return ReviewResponseDto.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .userId(review.getAppUser().getId())
                .productId(review.getProduct().getId())
                .userName(review.getAppUser().getName())      // chỉ lấy tên
                .productName(review.getProduct().getName())   // chỉ lấy tên sản phẩm
                .created_at(review.getCreated_at())
                .updated_at(review.getUpdate_at())
                .build();
    }

    public void updateDtoFromEntity(Review review, UpdateReviewDto updateReviewDto){
        if(review != null && updateReviewDto != null){
        review.setRating(updateReviewDto.getRating());
        review.setComment(updateReviewDto.getComment());}

    }
}
