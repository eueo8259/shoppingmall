package com.example.shoppingMall.repository;

import com.example.shoppingMall.dto.ProductDto;
import com.example.shoppingMall.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Transactional
    @Query(value = "update product pq set pq.product_quantity = pq.product_quantity - :orderQuantity where product_code = :productCode", nativeQuery = true)
    void updateQuantity(@Param("productCode")Long productCode,
                        @Param("orderQuantity") int orderQuantity);
}