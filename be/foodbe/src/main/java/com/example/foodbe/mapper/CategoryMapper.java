package com.example.foodbe.mapper;

import com.example.foodbe.response.category.CategoryResponseDTO;
import com.example.foodbe.request.category.CreateCategoryDTO;
import com.example.foodbe.request.category.UpdateCategoryDTO;
import com.example.foodbe.models.Category;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CategoryMapper {

    public Category toEntity(CreateCategoryDTO requestDTO){
        return Category.builder()
                .name(requestDTO.getName())
                .slug(requestDTO.getSlug())
                .imgCategory(requestDTO.getImgCategory())
                .build();
    }

    public CategoryResponseDTO toDTO(Category category){
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .imgCategory(category.getImgCategory())
                .createAt(category.getCreatedAt())
                .build();
    }

    public void updateEntityFromDto(UpdateCategoryDTO dto, Category entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.getName());
        entity.setSlug(dto.getSlug());
        entity.setImgCategory(dto.getImgCategory());
        // createdAt thường không được update thủ công
    }



//    // mapper between Category and category reference
//    public CategoryReferenceDTO toReference(Category category){
//        return CategoryReferenceDTO.builder()
//                .id(category.getId())
//                .name(category.getName())
//                .slug(category.getSlug())
//                .build();
//    }


}
