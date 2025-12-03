package com.example.foodbe.request.product;

import com.example.foodbe.annotation.FormatWhitespace;
import com.example.foodbe.annotation.Trim;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateProductDTO {

    @FormatWhitespace
    @NotBlank(message = "{entity.name.required}")
    private String name;

    @FormatWhitespace
    @NotBlank(message = "{entity.name.required}")
    private String slug;

    @NotNull(message = "{entity.number.not.null}")
    @Positive(message = "{entity.number.positive}")
    private BigDecimal price;

    @Trim
    @NotBlank(message = "{entity.name.required}")
    private String imgProduct;

    @Size(max = 1000, message = "{entity.size.max}")
    private String description;

    @NotNull(message = "{entity.number.not.null}")
    private Long categoryId;
}
