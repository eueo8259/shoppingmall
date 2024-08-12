package com.example.shoppingMall.service;

import com.example.shoppingMall.entity.Coupon;
import com.example.shoppingMall.entity.UserCoupon;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.repository.CouponRepository;
import com.example.shoppingMall.repository.UserCouponRepository;
import com.example.shoppingMall.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    // 쿠폰 목록을 조회하는 메소드
    public List<Coupon> findCoupon(Long couponCode) {
        return couponRepository.findCoupon(couponCode);
    }

    // 쿠폰을 수령하는 메소드
    public boolean receiveCoupon(Long couponCode, String user) {
        // 사용자 정보 조회
        UserInfo userInfo = userInfoRepository.findByUserId(user);
        if (userInfo == null) {
            return false; // 사용자 정보를 찾을 수 없음
        }

        // 쿠폰 정보 조회
        Coupon coupon = couponRepository.findByCouponId(couponCode);
        if (coupon == null) {
            return false; // 쿠폰 정보를 찾을 수 없음
        }

        // UserCoupon 객체 생성 및 설정
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setCoupon(coupon);
        userCoupon.setUserInfo(userInfo);

        // UserCoupon 객체 저장
        try {
            userCouponRepository.save(userCoupon);
            return true; // 저장 성공
        } catch (Exception e) {
            // 저장 중 예외가 발생하면 false 반환
            e.printStackTrace(); // 예외 로그 출력
            return false;
        }
    }
}
