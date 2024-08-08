package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.*;
import com.example.shoppingMall.entity.Cart;
import com.example.shoppingMall.entity.UserCoupon;
import com.example.shoppingMall.service.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("order")
@Slf4j
public class OrderController {
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    UserService userService;
    @Autowired
    DeliveryService deliveryService;
    @Autowired
    CartService cartService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserCouponService userCouponService;

    @GetMapping("/cart")
    public String cartOrder(@RequestParam("orderItems") Long[] orderItems,
                            @RequestParam("userInfoCode") Long userInfoCode, Model model) {
        UserInfoDto userInfoDto = userService.findUserInfo(userInfoCode);
        List<DeliveryDto> deliveryDto = deliveryService.deliveryList(userInfoCode);
        DeliveryDto defaultDelivery = deliveryService.defaultDelivery(userInfoCode);
        List<Cart> cartList = cartService.findCartCodes(orderItems);
        List<UserCoupon> userCoupons = userCouponService.findCouponCodes(userInfoCode);
        log.info(userCoupons.toString());
        model.addAttribute("userInfoDto", userInfoDto);
        model.addAttribute("deliveryDto", deliveryDto);
        model.addAttribute("defaultDelivery", defaultDelivery);
        model.addAttribute("cartList", cartList);
        model.addAttribute("userCoupons", userCoupons);
        return "order/insertOrderCart";
    }

    @PostMapping("/insertOrder")
    @ResponseBody
    public Map<String, String> insertOrder(@RequestBody Map<String, Object> requestBody){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 문자열 -> Java 객체 변환
            OrderDto orderDto = objectMapper.convertValue(requestBody.get("orderDto"), OrderDto.class);
            Long[] cartCodeList = objectMapper.convertValue(requestBody.get("cartCodeList"), Long[].class);
            List<Map<String, Object>> orderDetailList = objectMapper.convertValue(
                    requestBody.get("orderDetailList"), new TypeReference<List<Map<String, Object>>>() {}
            );
            Long couponCode = objectMapper.convertValue(requestBody.get("couponCode"), Long.class);
            orderService.insertOrder(orderDto, orderDetailList, cartCodeList, couponCode);
            Map<String, String> response = new HashMap<>();
            response.put("redirectUrl", "/");
            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error processing order", e);
        }
    }

    @GetMapping("/list")
    public String orderList(Model model, Principal principal) {

        if(principal != null) {
            List<OrderDto> orderList = orderService.findOrders(principal);
            List<OrderDetailDto> orderDetailList = new ArrayList<>();
            orderDetailList = orderService.findOrderDetail(orderList);
            log.info(orderList.toString());
            model.addAttribute("orderList", orderList);
            model.addAttribute("orderDetailList", orderDetailList);
            return "order/orderList";
        } else {
            return "redirect:/";
        }
    }
}
