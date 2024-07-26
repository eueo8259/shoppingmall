package com.example.shoppingMall.dto;

import com.example.shoppingMall.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private String categoryCode;
    private String categoryName;

    public static CategoryDto fromCategoryEntity(Category category){
        return new CategoryDto(
                category.getCategoryCode(),
                category.getCategoryName()
        );
    }

}