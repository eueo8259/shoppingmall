package com.example.shoppingMall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCouponDto {
    private Long userCouponCode;
    private Long couponCode;
    private Long userInfoCode;

}
