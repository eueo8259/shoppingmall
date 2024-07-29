package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.CartDto;
import com.example.shoppingMall.entity.Cart;
import com.example.shoppingMall.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("cart")
@Slf4j
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("")
    public String mainView(Principal principal, Model model){
        if(principal != null) {
            String user = principal.getName();
            List<Cart> cartList = cartService.findAll(user);
            Long total = cartService.total(user);
            model.addAttribute("cart", cartList);

            log.info(cartList.toString());
            return "cart/main";
        }
        return "cart/main";
    }




}
