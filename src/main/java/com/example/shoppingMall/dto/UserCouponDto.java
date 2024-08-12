package com.example.shoppingMall.dto;

import com.example.shoppingMall.entity.Coupon;
import com.example.shoppingMall.entity.Orders;
import com.example.shoppingMall.entity.UserCoupon;
import com.example.shoppingMall.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCouponDto {
    private Long userCouponCode;
    private UserInfo userInfo;
    private Coupon coupon;

    public static UserCouponDto fromUserCouponEntity(UserCoupon userCoupon) {
        return new UserCouponDto(
                userCoupon.getUserCouponCode(),
                userCoupon.getUserInfo(),
                userCoupon.getCoupon()
        );
    }
    public static UserCoupon toUserCouponEntity(UserCouponDto dto) {
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserCouponCode(dto.getUserCouponCode());
        userCoupon.setUserInfo(dto.getUserInfo());
        userCoupon.setCoupon(dto.getCoupon());
        return userCoupon;
    }

}
