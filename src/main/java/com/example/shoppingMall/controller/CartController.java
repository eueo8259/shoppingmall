package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.CartDto;
import com.example.shoppingMall.dto.ProductDto;
import com.example.shoppingMall.entity.Cart;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.service.CartService;
import com.example.shoppingMall.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("cart")
@Slf4j
public class CartController {
    @Autowired
    CartService cartService;
    ProductService productService;

    @GetMapping("")
    public String mainView(Principal principal, Model model){
        if(principal != null) {
            String user = principal.getName();
            List<Cart> cartList = cartService.findAll(user);
            for (Cart c : cartList){
                System.out.println(c.getProduct().getProductPrice());
            }
            model.addAttribute("cart", cartList);

            return "cart/main";
        }
        return "cart/main";
    }

    @PostMapping("/update-quantity")
    public ResponseEntity<String> updateQuantity(@RequestParam("itemId") Long itemId,
                                                 @RequestParam("quantity") int quantity) {
        try {
            cartService.updateQuantity(itemId, quantity);
            return ResponseEntity.ok("수량이 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("수량 업데이트에 실패했습니다.");
        }
    }

    @PostMapping("/remove-item")
    public ResponseEntity<String> removeItem(@RequestParam("itemId") Long itemId) {
        try {
            cartService.removeItem(itemId);
            return ResponseEntity.ok("항목이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("항목 삭제에 실패했습니다.");
        }
    }




}
