package com.example.shoppingMall.controller;

import com.example.shoppingMall.entity.Cart;
import com.example.shoppingMall.entity.WishList;
import com.example.shoppingMall.service.WishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

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

}
