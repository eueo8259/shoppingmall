package com.example.shoppingMall.service;

import com.example.shoppingMall.entity.UserCoupon;
import com.example.shoppingMall.repository.UserCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCouponService {
    @Autowired
    UserCouponRepository userCouponRepository;

    public List<UserCoupon> findCouponCodes(Long userInfoCode) {
        return userCouponRepository.findByUserInfo_userInfoCode(userInfoCode);
    }
}
