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
    // 아이디 중복체크
    @PostMapping("/checkId")
    @ResponseBody
    public Map<String, Boolean> checkId(@RequestParam("id") String userId) {
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

    @PostMapping("/findId")
    @ResponseBody
    public Map<String, String> findId(@RequestParam("userName") String userName,
                                       @RequestParam("RRN") String rrn,
                                       @RequestParam("phoneNumber") String phoneNumber) {
        String userId = userService.findUserId(userName, rrn, phoneNumber);
        String response = "found";
        if(userId == null) {
            response = "NotFound";
        }
        Map<String, String> data = new HashMap<>();
        data.put("userId", userId);
        data.put("isEmpty", response);
        return data;
    }

    @PostMapping("/findPw")
    @ResponseBody
    public Map<String, String> findPw(@RequestParam("id") String id,
                                      @RequestParam("phoneNumber") String phoneNumber,
                                      @RequestParam("email") String email) {
        String userId = userService.findUser(id, phoneNumber, email);
        String response = "found";
        if(userId == null) {
            response = "NotFound";
        }
        Map<String, String> data = new HashMap<>();
        data.put("userId", userId);
        data.put("response", response);
        return data;
    }

    @PostMapping("/updatePw")
    @ResponseBody
    public Map<String, String> updatePw(@RequestParam("id") String id,
                                        @RequestParam("updatePw") String updatePw) {
        String save = userService.updatePw(id, updatePw);
        Map<String, String> response = new HashMap<>();
        response.put("save", save);
        return response;
    }

}
