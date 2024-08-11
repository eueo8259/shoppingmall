package com.example.shoppingMall.controller;

import com.example.shoppingMall.constant.UserRole;
import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.entity.Category;
import com.example.shoppingMall.entity.Users;
import com.example.shoppingMall.service.ProductService;
import com.example.shoppingMall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    @ModelAttribute
    public void addAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String userRole = userService.findUserRole(userDetails.getUsername());
            UserInfoDto userInfoDto = userService.findUserInfo(userDetails.getUsername());
            int userCurrentPoint = userInfoDto.getCurrentPoint();
            model.addAttribute("username", userDetails.getUsername());
            model.addAttribute("userRole", userRole);
            model.addAttribute("userCurrentPoint", userCurrentPoint);
            log.info(userRole.toString());
        }
        List<Category> categoryList = productService.categoryList();
        model.addAttribute("categoryList", categoryList);


    }
}
