package com.example.shoppingMall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCouponDto {
    private Long userCouponId;
    private Long couponId;
    private Long userInfoId;

}
