package com.example.shoppingMall.repository;

import com.example.shoppingMall.dto.ProductDto;
import com.example.shoppingMall.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query(value = "SELECT * FROM product where product_code = :productCode", nativeQuery = true)
    Product findByProductCode(@Param("productCode") Long productCode);

    @Query(value = "SELECT product_name, product_code FROM product", nativeQuery = true)
    List<Map<String, String>> findGetName();
}