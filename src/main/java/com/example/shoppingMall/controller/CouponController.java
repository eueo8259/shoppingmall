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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            // 관리자 확인
            String admin = principal.getName();
            model.addAttribute("admin", admin);

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
        boolean check = couponService.checkCoupon(couponCode, user);
        if (check){
            boolean success = couponService.receiveCoupon(couponCode, user); // 쿠폰 수령 처리 로직
            if (success) {
                return ResponseEntity.ok("쿠폰을 성공적으로 받았습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("쿠폰 수령에 실패했습니다.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("쿠폰을 이미 받으셨습니다.");
        }
    }

    @PostMapping("/coupon/remove")
    public ResponseEntity<String> remove(@RequestParam("couponCode") Long couponCode) {
        boolean success = couponService.remove(couponCode);
            if (success) {
                return ResponseEntity.ok("쿠폰을 성공적으로 삭제하였습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("쿠폰 삭제에 실패했습니다.");
            }
    }


    @PostMapping("coupon/insert")
    public String insertCoupon(CouponDto couponDto,
                               @RequestParam("categoryId") Long categoryId,
                               RedirectAttributes redirectAttributes) {
        log.info(categoryId.toString());
        try {
            couponService.insert(couponDto, couponService.lastCouponCode(categoryId),categoryId);
            redirectAttributes.addFlashAttribute("message", "쿠폰 등록이 완료되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "쿠폰 등록에 실패하였습니다.");
        }
        return "redirect:/event/category/coupon/" + categoryId; // 등록 후 원래 페이지로 리다이렉트
    }

}
