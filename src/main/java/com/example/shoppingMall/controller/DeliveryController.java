package com.example.shoppingMall.controller;

import com.example.shoppingMall.dto.DeliveryDto;
import com.example.shoppingMall.dto.UserPointDto;
import com.example.shoppingMall.service.DeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("delivery")
@Slf4j
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

    @GetMapping("/deliveryPop")
    public String deliveryPop(Model model, Principal principal) {
        if(principal != null) {
            String userId = principal.getName();
            List<DeliveryDto> deliveryDto = deliveryService.userDeliveryList(userId);
            model.addAttribute("deliveryDto", deliveryDto);
            return "delivery/deliveryPop";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/insertDelivery")
    public String insertDelivery(Model model) {
        model.addAttribute("deliveryDto", new DeliveryDto());
        return "delivery/deliveryEdit";
    }

    @GetMapping("/newDeliveryPop")
    public String newDeliveryPop(Model model) {
        model.addAttribute("deliveryDto", new DeliveryDto());
        return "delivery/newDeliveryPop";
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

    @PostMapping("/popSave")
    @ResponseBody
    public Map<String, Long> popSaveDelivery(@RequestParam Map<String, String> data) {
        DeliveryDto deliveryDto = new DeliveryDto();
        deliveryDto.setContactName(data.get("contactName"));
        deliveryDto.setContactNumber(data.get("contactNumber"));
        deliveryDto.setAddress(data.get("address"));
        deliveryDto.setPostalCode(data.get("postalCode"));
        deliveryDto.setDefaultYn(data.get("defaultYn"));
        String userId = data.get("userId");
//        log.info(data.toString());
        Map<String, Long> response = new HashMap<>();
        Long deliveryCode = deliveryService.saveDelivery(deliveryDto, userId);
        response.put("deliveryCode", deliveryCode);
        return response;
    }

    @GetMapping("updateDelivery/{code}")
    public String updateDelivery(@PathVariable("code") Long code, Model model){
        DeliveryDto deliveryDto = deliveryService.findDelivery(code);
        String str = deliveryDto.getAddress();
        int lastSpaceIndex = str.lastIndexOf(",");
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
