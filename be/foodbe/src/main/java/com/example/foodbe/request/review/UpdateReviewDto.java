package com.example.foodbe.request.review;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UpdateReviewDto {

    @Min(value = 1, message = "{entity.size.min}")
    @Max(value = 5, message = "{entity.size.max}")
    private int rating;       // Điểm đánh giá mới

    @NotBlank(message = "{entity.name.required}")
    @Size(max = 500, message = "{entity.size.max}")
    private String comment;   // Nội dung đánh giá mới

}
