package com.example.shoppingMall.repositoryCustom;

import com.example.shoppingMall.dto.ProductDto;
import com.example.shoppingMall.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCustomRepository {
    Page<Product> getSearchProductList(String type, String keyword, Pageable pageable);

    Page<Product> getSearchApproveProductList(String type, String keyword, Pageable pageable);

    Page<Product> findProductListByCategory(String categoryCode, Pageable pageable);
}
