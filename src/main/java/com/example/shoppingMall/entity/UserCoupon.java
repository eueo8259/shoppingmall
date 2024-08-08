package com.example.shoppingMall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserCoupon {
    @Id
    private Long userCouponCode;
    @ManyToOne
    @JoinColumn(name = "couponCode")
    private Coupon coupon;
    @ManyToOne
    @JoinColumn(name = "userInfoCode")
    private UserInfo userInfo;
}
