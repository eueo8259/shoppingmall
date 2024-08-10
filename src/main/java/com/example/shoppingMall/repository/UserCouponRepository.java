package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    List<UserCoupon> findByUserInfo_userInfoCode(Long userInfoCode);
}
