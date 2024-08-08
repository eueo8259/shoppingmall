package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.*;
import com.example.shoppingMall.entity.Cart;
import com.example.shoppingMall.service.CartService;
import com.example.shoppingMall.service.DeliveryService;
import com.example.shoppingMall.service.OrderService;
import com.example.shoppingMall.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
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

    @GetMapping("/cart")
    public String cartOrder(@RequestParam("orderItems") Long[] orderItems,
                            @RequestParam("userInfoCode") Long userInfoCode, Model model) {
        OrdersDto orderDto = new OrdersDto();
        UserInfoDto userInfoDto = userService.findUserInfo(userInfoCode);
        List<DeliveryDto> deliveryDto = deliveryService.deliveryList(userInfoCode);
        DeliveryDto defaultDelivery = deliveryService.defaultDelivery(userInfoCode);
        List<Cart> cartList = cartService.findCartCodes(orderItems);
        model.addAttribute("userInfoDto", userInfoDto);
        model.addAttribute("deliveryDto", deliveryDto);
        model.addAttribute("defaultDelivery", defaultDelivery);
        model.addAttribute("cartList", cartList);
        model.addAttribute("orderDto", orderDto);
        return "order/insertOrderCart";
    }

    @PostMapping("/insertOrder")
    @ResponseBody
    public Map<String, String> insertOrder(@RequestBody Map<String, Object> requestBody){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON 문자열 -> Java 객체 변환
            OrdersDto orderDto = objectMapper.convertValue(requestBody.get("orderDto"), OrdersDto.class);
            List<Map<String, Object>> orderDetailList = objectMapper.convertValue(
                    requestBody.get("orderDetailList"), new TypeReference<List<Map<String, Object>>>() {}
            );
            log.info(orderDto.toString());
            log.info(orderDetailList.toString());
            orderService.insertOrder(orderDto, orderDetailList);
            Map<String, String> response = new HashMap<>();
            response.put("redirectUrl", "/");
            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error processing order", e);
        }
    }

    @GetMapping("/orderList")
    public String orderList(Model model, Principal principal) {

        if(principal != null) {
            OrdersDto orderList = orderService.findOrders(principal);
            Long orderCode = orderList.getOrderCode();
            OrderDetailDto orderDetailList = new OrderDetailDto();
            if(orderList != null) {
                OrderDetailDto OrderDetailDto = orderService.findOrderDetail(orderCode);
            }
            model.addAttribute("orderList", orderList);
            model.addAttribute("orderDetailList", orderDetailList);
            return "point/chargePointPop";
        } else {
            return "redirect:/";
        }
    }
}
