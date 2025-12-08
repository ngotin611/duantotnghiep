package com.example.foodbe.request.review;

import jakarta.validation.constraints.*;
import lombok.*;

//import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateReviewDto {

    @Min(value = 1, message = "{entity.size.min}")
    @Max(value = 5, message = "{entity.size.max}")
    private Integer rating;           // Điểm đánh giá 1-5

    @NotBlank(message = "{entity.name.required}")
    @Size(max = 500, message = "{entity.size.max}")
    private String comment;       // Nội dung đánh giá

    @NotNull(message = "{entity.number.not.null}")
    private Long productId;       // ID sản phẩm được đánh giá
    // private Long userId;       // Có thể lấy từ token, không cần client gửi
}
