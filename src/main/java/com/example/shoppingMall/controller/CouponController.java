package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.CategoryDto;
import com.example.shoppingMall.dto.CouponDto;
import com.example.shoppingMall.entity.Coupon;
import com.example.shoppingMall.service.CategoryService;
import com.example.shoppingMall.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("event")
@Slf4j
public class CouponController {

    @Autowired
    CouponService couponService;

    @Autowired
    CategoryService categoryService;
    @GetMapping("")
    public String eventMainView(Model model){
        List<CategoryDto> categoryDtoList = categoryService.findAll();
        model.addAttribute("category", categoryDtoList);
        log.info(categoryDtoList.toString());
        return "event/main";
    }

    @GetMapping("category/coupon/{id}")
    public String couponView(Model model, Principal principal,
                             @PathVariable("id") Long couponCode){
        if (principal != null){
            List<Coupon> couponDtoList = couponService.findCoupon(couponCode);
            model.addAttribute("coupon",couponDtoList);
            log.info(couponDtoList.toString());
            return "event/coupon";
        }
        return "user/login";
    }

    @PostMapping("/receive-coupon")
    public ResponseEntity<String> receiveCoupon(@RequestParam("couponCode") Long couponCode, Principal principal) {
        String user = principal.getName();
        boolean success = couponService.receiveCoupon(couponCode, user); // 쿠폰 수령 처리 로직
        if (success) {
            return ResponseEntity.ok("쿠폰을 성공적으로 받았습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("쿠폰 수령에 실패했습니다.");
        }
    }
}
