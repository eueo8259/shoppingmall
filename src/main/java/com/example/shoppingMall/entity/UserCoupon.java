package com.example.shoppingMall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserCoupon {
    @Id
    private Long userCouponId;
    @ManyToOne
    @JoinColumn(name = "couponId")
    private Coupon coupon;
    @ManyToOne
    @JoinColumn(name = "userInfoId")
    private UserInfo userInfo;
}
