package com.example.shoppingMall.controller;

import com.example.shoppingMall.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("wish")
public class WishController {
    @Autowired
    WishService wishService;

    @GetMapping("")
    public String mainView(){
        return "wishList/main";
    }

}
