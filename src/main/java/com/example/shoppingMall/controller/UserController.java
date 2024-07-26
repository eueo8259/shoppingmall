package com.example.shoppingMall.controller;

import com.example.shoppingMall.constant.UserRole;
import com.example.shoppingMall.dto.UserDto;
import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/signUp")
    public String signUpForm(Model model) {
        model.addAttribute("userInfoDto", new UserInfoDto());
        model.addAttribute("userDto", new UserDto());
        return "user/signUp";
    }

    @PostMapping("/signUp")
    public String userInfoInsert(@ModelAttribute("userInfoDto") UserInfoDto userInfoDto) {
        log.info(userInfoDto.toString());
        UserDto userDto = new UserDto();
        userDto.setId(userInfoDto.getUser().getId());
        userDto.setPassword(userInfoDto.getUser().getPassword());
        userDto.setUserRole(UserRole.USER);
        userService.userInsert(userDto);
        userService.userInfoInsert(userInfoDto);
        return "redirect:/";
    }
    @PostMapping("/checkId")
    @ResponseBody
    public Map<String, Boolean> checkId(@RequestParam("id") String userId) {
        boolean isDuplicate = userService.isIdDuplicate(userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        return response;
    }
    @PostMapping("/checkDuplicate")
    @ResponseBody
    public Map<String, Boolean> checkDuplicate(@RequestParam("id") String userId) {
        boolean isDuplicate = userService.isIdDuplicate(userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        return response;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "user/login";
    }

}
