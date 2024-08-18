package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.CouponDto;
import com.example.shoppingMall.entity.Category;
import com.example.shoppingMall.entity.Coupon;
import com.example.shoppingMall.entity.UserCoupon;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.repository.CategoryRepository;
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

    @Autowired
    private CategoryRepository categoryRepository;

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

    public boolean checkCoupon(Long couponCode, String user) {
        UserInfo id = userInfoRepository.findByUserId(user);
        List<UserCoupon> coupons = userCouponRepository.checkCoupon(couponCode, id.getUserInfoCode());
        if (coupons.size() == 1) {
            return false;
        }
        return true; // 필요에 따라 이 부분을 수정하세요.
    }

    public boolean remove(Long couponCode) {
        if (couponRepository.existsById(couponCode)) {  // 먼저 해당 쿠폰이 존재하는지 확인
            couponRepository.deleteById(couponCode);     // 쿠폰 삭제
            return !couponRepository.existsById(couponCode); // 삭제 후 해당 쿠폰이 존재하지 않으면 true 반환
        }
        return false; // 삭제하려는 쿠폰이 없으면 false 반환
    }

    public Long lastCouponCode(Long categoryCode) {
        return couponRepository.lastCode(categoryCode);
    }

    public void insert(CouponDto couponDto, Long coupon, Long categoryId) {
        Category category = categoryRepository.find(categoryId);
        Coupon saveCoupon = new Coupon();
        if (couponDto.getDiscountRate() == 0) {
            saveCoupon.setCouponCode(coupon+1);
            saveCoupon.setCategory(category);
            saveCoupon.setDiscountAmount(couponDto.getDiscountAmount());
            saveCoupon.setDiscountRate(0);
        } else {
            saveCoupon.setCouponCode(coupon+1);
            saveCoupon.setCategory(category);
            saveCoupon.setDiscountAmount(0);
            saveCoupon.setDiscountRate(couponDto.getDiscountRate()/100);
        }
        couponRepository.save(saveCoupon);
    }
}
