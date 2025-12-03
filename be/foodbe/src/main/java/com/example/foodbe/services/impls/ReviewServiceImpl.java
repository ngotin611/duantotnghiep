package com.example.foodbe.services.impls;

import com.example.foodbe.request.review.CreateReviewDto;
import com.example.foodbe.response.review.ReviewResponseDto;
import com.example.foodbe.request.review.UpdateReviewDto;
import com.example.foodbe.exception_handler.NotFoundException;
import com.example.foodbe.mapper.ReviewMapper;
import com.example.foodbe.models.AppUser;
import com.example.foodbe.models.Product;
import com.example.foodbe.models.Review;
import com.example.foodbe.repositories.ProductRepository;
import com.example.foodbe.repositories.ReviewRepository;
import com.example.foodbe.repositories.UserRepository;
import com.example.foodbe.services.ReviewService;
import com.example.foodbe.utils.ConstantUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    @Override
    public List<ReviewResponseDto> findAll() {
        List<Review> reviews = reviewRepository.findAll();
        if(reviews.isEmpty()){
            return List.of();
        }
        return reviews.stream()
                .map(review->reviewMapper.toDto(review))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponseDto create(CreateReviewDto createReviewDto) {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email= authentication.getName();
        AppUser appUser= userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException(ConstantUtils.ExceptionMessage.NOT_FOUND +email));
        Product product= productRepository.findById(createReviewDto.getProductId())
                .orElseThrow(()-> new NotFoundException(ConstantUtils.ExceptionMessage.NOT_FOUND +createReviewDto.getProductId()));

        Review review= reviewMapper.toEntity(createReviewDto, appUser, product);
        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    public ReviewResponseDto updateById(Long id, UpdateReviewDto updateReviewDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(ConstantUtils.ExceptionMessage.NOT_FOUND+id));
        reviewMapper.updateDtoFromEntity(review, updateReviewDto);

        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    public void deleteById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(ConstantUtils.ExceptionMessage.NOT_FOUND +id));
        reviewRepository.deleteById(id);
    }
}
