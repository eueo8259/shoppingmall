package com.example.shoppingMall.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

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