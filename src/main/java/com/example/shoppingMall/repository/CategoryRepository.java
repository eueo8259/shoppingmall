package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query(value = "SELECT * FROM category where category_code = :categoryId", nativeQuery = true)
    Category find(@Param("categoryId") Long categoryId);
}
