package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manage")
@Slf4j
public class UserMngController {
    @Autowired
    UserService userService;

    @GetMapping("/userMng")
    public String userList(Model model) {
        List<UserInfoDto> userInfoDtos = userService.userListAll();
        model.addAttribute("userInfoDto", userInfoDtos);
        return "manage/userMng";
    }
}
