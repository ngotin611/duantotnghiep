package com.example.foodbe.request.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateReviewDto {

    @Min(value = 1, message = "{entity.size.min}")
    @Max(value = 5, message = "{entity.size.max}")
    private Integer rating;       // Điểm đánh giá mới

    @NotBlank(message = "{entity.name.required}")
    @Size(max = 500, message = "{entity.size.max}")
    private String comment;   // Nội dung đánh giá mới

}
