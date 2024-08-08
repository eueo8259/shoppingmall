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
import org.springframework.stereotype.Repository;


@Repository
@Primary
public interface ProductRepository extends JpaRepository<Product, Long>, ProductCustomRepository {
    @Query(value = "SELECT * FROM product", nativeQuery = true)
    Page<Product> getProductsList(Pageable pageable);

    @Query(value = "SELECT * FROM product WHERE status = '대기'", nativeQuery = true)
    Page<Product> getProductsAwaitingApproval(Pageable pageable);
}
