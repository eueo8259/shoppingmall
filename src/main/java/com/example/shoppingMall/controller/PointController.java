package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.UserPointDto;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.service.UserPointService;
import com.example.shoppingMall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/point")
@Slf4j
public class PointController {
    @Autowired
    UserPointService userPointService;
    @Autowired
    UserService userService;

    @GetMapping("/chargePoint")
    public String chargeForm(Model model, Principal principal) {
        if(principal != null) {
            UserPointDto userPointDto = new UserPointDto();
            userPointDto.setUserInfo(userService.findByUserId(principal.getName()));
            log.info(userPointDto.toString());
            model.addAttribute("userPointDto", userPointDto);
            return "point/chargePoint";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/chargePoint")
    public String chargePoint(@RequestParam("chargePoint") int chargePoint,
                              @RequestParam("username") String userId){
        String remarks = "포인트충전";
        userPointService.chargePoint(chargePoint, userId, remarks);
        return "redirect:/point/pointList";
    }

    @GetMapping("/pointList")
    public String pointList(Model model, Principal principal) {
        if(principal != null) {
            String userId = principal.getName();
            List<UserPointDto> userPointDto = userPointService.findById(userId);
            model.addAttribute("userPointDto", userPointDto);
            return "point/pointList";
        } else {
            return "redirect:/";
        }
    }
}
