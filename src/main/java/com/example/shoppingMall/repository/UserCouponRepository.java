package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.Coupon;
import com.example.shoppingMall.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    List<UserCoupon> findByUserInfo_userInfoCodeAndCoupon_couponCode(@Param("userInfoCode") Long userInfoCode,
                                                                     @Param("couponCode") Long couponCode);

    @Query(value = "SELECT * FROM user_coupon WHERE coupon_code = :couponCode AND user_info_code = :user", nativeQuery = true)
    List<UserCoupon> checkCoupon(@Param("couponCode") Long couponCode,
                                 @Param("user") Long user);
}