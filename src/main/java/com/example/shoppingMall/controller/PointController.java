package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.dto.UserPointDto;
import com.example.shoppingMall.service.UserPointService;
import com.example.shoppingMall.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("point")
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

    @GetMapping("/chargePointPop")
    public String chargePointPop(Model model, Principal principal) {
        if(principal != null) {
            UserPointDto userPointDto = new UserPointDto();
            userPointDto.setUserInfo(userService.findByUserId(principal.getName()));
            log.info(userPointDto.toString());
            model.addAttribute("userPointDto", userPointDto);
            return "point/chargePointPop";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/chargePoint")
    public String chargePoint(@RequestParam("chargePoint") int chargePoint,
                              @RequestParam("username") String userId,
                              @RequestParam("popYn") String popYn){
        String remarks = "포인트충전";
        String response = userPointService.chargePoint(chargePoint, userId, remarks, popYn);
        if(response.equals("N")) {
            return "redirect:/point/pointList";
        } else {
            return "point/chargePointPop";
        }
    }

    @PostMapping("/pointReflection")
    @ResponseBody
    public Map<String, Integer> pointReflection(@RequestParam("userInfoCode") Long userInfoCode){
        UserInfoDto userInfoDto = userService.findUserInfo(userInfoCode);
        Map<String, Integer> data = new HashMap<>();
        data.put("currentPoint", userInfoDto.getCurrentPoint());
        log.info(data.toString());
        return data;
    }


    @GetMapping("/list")
    public String pointList(Model model, Principal principal,
                            @PageableDefault(page = 0, size = 6, sort = "pointId",
                            direction = Sort.Direction.ASC) Pageable pageable) {
        if(principal != null) {
            String userId = principal.getName();
            Page<UserPointDto> userPointDto = userPointService.findById(userId, pageable);
            model.addAttribute("pageNumber", userPointDto.getNumber());
            model.addAttribute("pageSize", userPointDto.getSize());
            model.addAttribute("userPointDto", userPointDto);
            return "point/pointList";
        } else {
            return "redirect:/";
        }
    }
}
