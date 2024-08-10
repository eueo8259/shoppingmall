package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT * FROM product", nativeQuery = true)
    Page<Product> getProductsList(Pageable pageable);
}