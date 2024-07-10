package com.example.shoppingMall.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponDto {
    private Long couponId;
    private String code;
    private double discountRate;
    private int discountAmount;

}
