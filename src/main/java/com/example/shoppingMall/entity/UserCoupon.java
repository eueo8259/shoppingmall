package com.example.shoppingMall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCouponCode;
    @ManyToOne
    @JoinColumn(name = "couponCode")
    private Coupon coupon;
    @ManyToOne
    @JoinColumn(name = "userInfoCode")
    private UserInfo userInfo;
}