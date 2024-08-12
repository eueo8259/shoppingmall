package com.example.shoppingMall.repository;

import com.example.shoppingMall.dto.ProductDto;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.repositoryCustom.ProductCustomRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Primary
public interface ProductRepository extends JpaRepository<Product, Long>, ProductCustomRepository {
    @Query(value = "SELECT * FROM product", nativeQuery = true)
    Page<Product> getProductsList(Pageable pageable);

    @Query(value = "SELECT * FROM product WHERE status = '대기'", nativeQuery = true)
    Page<Product> getProductsAwaitingApproval(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update product pq set pq.product_quantity = pq.product_quantity - :orderQuantity where product_code = :productCode", nativeQuery = true)
    void updateQuantity(@Param("productCode")Long productCode,
                        @Param("orderQuantity") int orderQuantity);


    @Query(value = "SELECT * FROM product where product_code = :productCode", nativeQuery = true)
    Product findByProductCode(@Param("productCode") Long productCode);

    @Query(value = "SELECT product_name, product_code FROM product", nativeQuery = true)
    List<Map<String, String>> findGetName();
}
