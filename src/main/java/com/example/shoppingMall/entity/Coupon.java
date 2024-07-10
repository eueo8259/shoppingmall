package com.example.shoppingMall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Coupon {
    @Id
    private Long couponId;
    @ManyToOne
    @JoinColumn(name = "code")
    private Common common;
    private double discountRate;
    private int discountAmount;
}
