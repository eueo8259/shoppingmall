package com.example.shoppingMall.controller;

import com.example.shoppingMall.constant.UserRole;
import com.example.shoppingMall.dto.ReviewDto;
import com.example.shoppingMall.dto.UserDto;
import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.service.ReviewService;
import com.example.shoppingMall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ReviewService reviewService;

    @GetMapping("/signUp")
    public String signUpForm(Model model) {
        model.addAttribute("userInfoDto", new UserInfoDto());
        model.addAttribute("userDto", new UserDto());
        return "user/signUp";
    }

    @PostMapping("/signUp")
    public String userInfoInsert(@ModelAttribute("userInfoDto") UserInfoDto userInfoDto,
                                 @RequestParam("role") String role) {
        log.info(userInfoDto.toString());
        userService.userInsert(userInfoDto, role);
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

    @PostMapping("/user/findId")
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

    @PostMapping("/user/findPw")
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

    @PostMapping("/user/updatePw")
    public Map<String, String> updatePw(@RequestParam("id") String id,
                                        @RequestParam("updatePw") String updatePw) {
        String save = userService.updatePw(id, updatePw);
        Map<String, String> response = new HashMap<>();
        response.put("save", save);
        return response;
    }

    @GetMapping("/user/userInfoModify")
    public String userInfoModifyForm(Model model, Principal principal) {
        if (principal != null) {
            UserInfoDto userInfoDto = userService.findUserInfo(principal.getName());
            model.addAttribute(userInfoDto);
            return "user/userInfoEdit";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/user/checkPassword")
    @ResponseBody
    public String checkPassword(@RequestParam("userId") String userId,
                                @RequestParam("password") String password){
        Boolean passwordCheck = userService.passwordCheck(userId, password);
        if(passwordCheck){
            return "ok";
        } else {
            return "error";
        }
    }
    @PostMapping("/user/userInfoModify")
    public String userInfoModify(@ModelAttribute("userInfoDto") UserInfoDto userInfoDto,
                                 @RequestParam("applySeller") String applySeller,
                                 @RequestParam("password") String password){
        log.info(userInfoDto.toString());
        log.info(applySeller);
        log.info(password);
        userService.userInfoModify(userInfoDto, applySeller, password);

        return "redirect:/";
    }

    @GetMapping("/review")
    public String reviewPage(@RequestParam("productCode") Long productCode,
                             Model model){
        UserInfoDto userInfoDto = userService.loginUserInfoDto();

        ReviewDto reviewDto = reviewService.getReviewBowl(productCode, userInfoDto.getUserInfoCode());
        model.addAttribute("reviewDto", reviewDto);
        return "user/review";
    }

    @PostMapping("review")
    public String productReview(@ModelAttribute("reviewDto") ReviewDto reviewDto){
        reviewService.insertReview(reviewDto);
        return "redirect:/";
    }

    @PostMapping("/user/deleteUser")
    @ResponseBody
    public String deleteUser(@RequestParam("userInfoCode") Long userInfoCode){
        userService.deleteUser(userInfoCode);
        return "ok";
    }
}
