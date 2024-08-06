package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.DeliveryDto;
import com.example.shoppingMall.dto.UserPointDto;
import com.example.shoppingMall.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("delivery")
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @GetMapping("/deliveryList")
    public String deliveryList(Model model, Principal principal) {
        if(principal != null) {
            String userId = principal.getName();
            List<DeliveryDto> deliveryDto = deliveryService.userDeliveryList(userId);
            model.addAttribute("deliveryDto", deliveryDto);
            return "delivery/deliveryList";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/insertDelivery")
    public String insertDelivery(Model model) {
        model.addAttribute("deliveryDto", new DeliveryDto());
        return "delivery/deliveryEdit";
    }

    @PostMapping("/save")
    public String saveDelivery(@ModelAttribute("deliveryDto") DeliveryDto deliveryDto,
                               @RequestParam("username") String userId){
        if(deliveryDto != null){
            deliveryService.saveDelivery(deliveryDto, userId);
            return "redirect:/delivery/deliveryList";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("updateDelivery/{code}")
    public String updateDelivery(@PathVariable("code") Long code, Model model){
        DeliveryDto deliveryDto = deliveryService.findDelivery(code);
        String str = deliveryDto.getAddress();
        int lastSpaceIndex = str.lastIndexOf(" ");
        String address = null;
        String detailAddress = null;
        if (lastSpaceIndex != -1) {
            // 공백을 기준으로 문자열 자르기
            address = str.substring(0, lastSpaceIndex);
            detailAddress = str.substring(lastSpaceIndex + 1);
        }
        model.addAttribute("address", address);
        model.addAttribute("detailAddress", detailAddress);
        model.addAttribute("deliveryDto", deliveryDto);
        return "delivery/deliveryEdit";
    }

    @PostMapping("deleteDelivery/{code}")
    public String deleteDelivery(@PathVariable("code") Long code) {
        deliveryService.deleteDelivery(code);
        return "redirect:/delivery/deliveryList";
    }

}
