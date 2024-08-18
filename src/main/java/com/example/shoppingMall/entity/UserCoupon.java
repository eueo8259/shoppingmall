package com.example.shoppingMall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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