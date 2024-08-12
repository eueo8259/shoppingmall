package com.example.shoppingMall.service;

import com.example.shoppingMall.entity.Cart;
import com.example.shoppingMall.entity.Coupon;
import com.example.shoppingMall.entity.UserCoupon;
import com.example.shoppingMall.repository.CouponRepository;
import com.example.shoppingMall.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCouponService {
    @Autowired
    UserCouponRepository userCouponRepository;
    @Autowired
    CouponRepository couponRepository;

    public List<UserCoupon> findCouponCodes(Long userInfoCode, String category, List<Cart> cartList) {
        List<UserCoupon> userCoupons = new ArrayList<>();

        if (category != null) {
            List<Coupon> coupons = couponRepository.findByCategory_categoryCode(category);
            if (coupons != null && !coupons.isEmpty()) {
                for (Coupon coupon : coupons) {
                    List<UserCoupon> foundCoupons = userCouponRepository.findByUserInfo_userInfoCodeAndCoupon_couponCode(userInfoCode, coupon.getCouponCode());
                    if (foundCoupons != null && !foundCoupons.isEmpty()) {
                        userCoupons.addAll(foundCoupons);
                    }
                }
            }
        } else if (cartList != null) {
            for (Cart cart : cartList) {
                List<Coupon> coupons = couponRepository.findByCategory_categoryCode(cart.getProduct().getCategory().getCategoryCode());
                if (coupons != null && !coupons.isEmpty()) {
                    for (Coupon coupon : coupons) {
                        List<UserCoupon> foundCoupons = userCouponRepository.findByUserInfo_userInfoCodeAndCoupon_couponCode(userInfoCode, coupon.getCouponCode());
                        if (foundCoupons != null && !foundCoupons.isEmpty()) {
                            userCoupons.addAll(foundCoupons);
                        }
                    }
                }
            }
        }

        return userCoupons;
    }
}
