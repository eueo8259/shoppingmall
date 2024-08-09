package com.example.shoppingMall.controller;

import com.example.shoppingMall.entity.Cart;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.entity.WishList;
import com.example.shoppingMall.service.WishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("wish")
@Slf4j
public class WishController {
    @Autowired
    WishService wishService;

    @GetMapping("")
    public String mainView(Model model, Principal principal){
        if(principal != null) {
            String user = principal.getName();
            List<WishList> wishListList = wishService.findAll(user);
            model.addAttribute("wish", wishListList);
            log.info(wishListList.toString());
            return "wishList/main";
        }
        return "wishList/main";
    }
    @PostMapping("/remove-item")
    public ResponseEntity<String> removeItem(@RequestParam("itemId") Long itemId) {
        try {
            wishService.removeItem(itemId);
            return ResponseEntity.ok("항목이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("항목 삭제에 실패했습니다.");
        }
    }

    @PostMapping("/add-wish/{wish}")
    public String AddWish(@PathVariable("wish") Long productCode,
                          Principal principal) {
        if(principal != null) {
            String user = principal.getName();
            WishList wishList  = wishService.add(user, productCode);
            log.info(wishList.toString());
            return "redirect:/wish";
        }
        return "redirect:/user/login";
    }

}
