package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.CategoryDto;
import com.example.shoppingMall.entity.Category;
import com.example.shoppingMall.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    EntityManager em;
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> findAll() {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        return categoryRepository.findAll()
                .stream()
                .map(x-> CategoryDto.fromCategoryEntity(x))
                .toList();
    }


}
