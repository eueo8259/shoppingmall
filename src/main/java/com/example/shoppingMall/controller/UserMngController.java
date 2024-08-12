package com.example.shoppingMall.controller;

import com.example.shoppingMall.constant.UserRole;
import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manage")
@Slf4j
public class UserMngController {
    @Autowired
    UserService userService;

    @GetMapping("/userMng")
    public String userList(Model model,
                           @PageableDefault(page = 0, size = 6, sort = "userInfoCode",
                           direction = Sort.Direction.ASC) Pageable pageable) {
        Page<UserInfoDto> userInfoDtos = userService.userListAll(pageable);
        model.addAttribute("userInfoDto", userInfoDtos);
        return "manage/userMng";
    }

    @PostMapping("/updateIsActive")
    @ResponseBody
    public Page<UserInfoDto> updateIsActive(@RequestBody List<Map<String, String>> changes,
                                            @PageableDefault(page = 0, size = 6, sort = "userInfoCode",
                                            direction = Sort.Direction.ASC) Pageable pageable){
        try {
            for (Map<String, String> change : changes) {
                String userId = change.get("userId");
                String isActive = change.get("isActive");
                String userRole = change.get("userRole");
                userService.updateIsActive(userId, isActive);
                userService.updateUserRole(userId, userRole);
            }
            return userService.userListAll(pageable);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생했습니다.");
        }
    }

    @GetMapping("/approvalPop")
    public String approvalPop(Model model) {
        List<UserInfoDto> userInfoDtos = userService.applyUserList();
        model.addAttribute("userInfoDto", userInfoDtos);
        return "manage/approvalPop";
    }

    @PostMapping("/updateUserRoles")
    @ResponseBody
    public Page<UserInfoDto> updateUserRoles(@RequestBody List<String> data,
                                             @PageableDefault(page = 0, size = 6, sort = "userInfoCode",
                                             direction = Sort.Direction.ASC) Pageable pageable){
        try {
            for (String userId : data) {
                userService.updateUserRole(userId, "SELLER");
            }
            return userService.userListAll(pageable);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 오류가 발생했습니다.");
        }
    }

    @PostMapping("/filterUsers")
    @ResponseBody
    public List<UserInfoDto> filterMembers(@RequestBody Map<String, String> data) {
        log.info(data.get("grade").toString());
        log.info(data.get("userRole").toString());
        log.info(data.get("isActive").toString());
        List<UserInfoDto> userInfoDtos = userService.filterUserInfo(data.get("grade"), data.get("userRole"), data.get("isActive"), data.get("keyword"));
        log.info(userInfoDtos.toString());
        return userInfoDtos;
    }

    @PostMapping("/updateGrade")
    public String updateGrade(){
        userService.updateGrade();
        return "redirect:/manage/userMng";
    }
}
