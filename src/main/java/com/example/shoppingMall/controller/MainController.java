package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.entity.Category;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.service.ProductService;
import com.example.shoppingMall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @GetMapping("")
    public String mainPage(Model model,
                           Principal principal) {
        if(principal != null) {
            String userId = principal.getName();
            UserInfo userInfo = userService.findByUserId(userId);
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("userId", userId);
            return "main";
        } else {
            return "main";
        }
    }

}
