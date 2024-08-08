package com.example.shoppingMall.repositoryImpl;

import com.example.shoppingMall.dto.ProductDto;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.entity.QProduct;
import com.example.shoppingMall.repositoryCustom.ProductCustomRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductCustomRepository {

    @Autowired
    JPAQueryFactory jpaQuery;
    QProduct product = QProduct.product;


    @Override
    public Page<Product> getSearchProductList(String type, String keyword, Pageable pageable) {

        JPAQuery<Product> query = jpaQuery
                .selectFrom(product)
                .where(getPredicate(type, keyword, product))
                .orderBy(product.productCode.desc()); // 예시로 정렬 기준 설정

        List<Product> resultList = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = jpaQuery
                .selectFrom(product)
                .where(getPredicate(type, keyword, product))
                .fetchCount();

        return new PageImpl<>(resultList, pageable, count);
    }

    @Override
    public Page<Product> getSearchApproveProductList(String type, String keyword, Pageable pageable) {
        JPAQuery<Product> query = jpaQuery
                .selectFrom(product)
                .where(product.status.eq("대기"))
                .where(getPredicate(type, keyword, product))
                .orderBy(product.productCode.desc()); // 예시로 정렬 기준 설정

        List<Product> resultList = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = jpaQuery
                .selectFrom(product)
                .where(product.status.eq("대기"))
                .where(getPredicate(type, keyword, product))
                .fetchCount();

        return new PageImpl<>(resultList, pageable, count);
    }

    @Override
    public Page<Product> findProductListByCategory(String categoryCode, Pageable pageable) {
        JPAQuery<Product> query = jpaQuery
                .selectFrom(product)
                .where(product.status.eq("판매"))
                .where(product.category.categoryCode.eq(categoryCode))
                .orderBy(product.productCode.desc()); // 예시로 정렬 기준 설정

        List<Product> resultList = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = jpaQuery
                .selectFrom(product)
                .where(product.status.eq("판매"))
                .where(product.category.categoryCode.eq(categoryCode))
                .fetchCount();

        return new PageImpl<>(resultList, pageable, count);

    }


    private BooleanExpression getPredicate(String type, String keyword, QProduct product) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        }
        switch (type) {
            case "userInfoCode":
                Long code = Long.valueOf(keyword);
                return product.userInfo.userInfoCode.eq(code);
            case "categoryName":
                return product.category.categoryName.containsIgnoreCase(keyword);
            case "productName":
                return product.productName.containsIgnoreCase(keyword);
            default:
                return null;
        }
    }

}
