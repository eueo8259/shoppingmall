package com.example.shoppingMall.controller;

import com.example.shoppingMall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("cart")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("")
    public String mainView(){
        return "cart/main";
    }




}
