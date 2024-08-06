package com.example.shoppingMall.controller;

import com.example.shoppingMall.constant.UserRole;
import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/updateUserRoles")
    @ResponseBody
    public String updateCustomer(@RequestBody List<Map<String, String>> changes){
        try {
            for (int i=0; i<changes.size(); i++) {
                userService.updateIsActive(changes.get(i).get("userId"), changes.get(i).get("isActive"));
            }
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }
}
