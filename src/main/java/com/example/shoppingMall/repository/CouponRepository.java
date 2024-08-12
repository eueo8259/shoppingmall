package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query(value = "SELECT * FROM coupon WHERE category_code = :couponCode", nativeQuery = true)
    List<Coupon> findCoupon(@Param("couponCode") Long couponCode);


    @Query(value = "SELECT * FROM coupon where coupon_code = :couponCode", nativeQuery = true)
    Coupon findByCouponId(@Param("couponCode") Long couponCode);

    List<Coupon> findByCategory_categoryCode(@RequestParam("category") String category);

}
