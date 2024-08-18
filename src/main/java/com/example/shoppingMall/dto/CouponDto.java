package com.example.shoppingMall.dto;
import com.example.shoppingMall.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CouponDto {
    private Long couponCode;
    private Category categoryCode;
    private double discountRate;
    private int discountAmount;
}
