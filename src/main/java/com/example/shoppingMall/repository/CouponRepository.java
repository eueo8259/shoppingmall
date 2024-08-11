package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByCategory_categoryCode(String category);
}
