package com.example.shoppingMall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Coupon {
    @Id
    private Long couponCode;
    @ManyToOne
    @JoinColumn(name = "categoryCode")
    private Category category;
    private double discountRate;
    private int discountAmount;
}