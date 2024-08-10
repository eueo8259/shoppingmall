package com.example.shoppingMall.service;

import com.example.shoppingMall.constant.UserRole;
import com.example.shoppingMall.dto.DeliveryDto;
import com.example.shoppingMall.entity.Delivery;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.repository.DeliveryRepository;
import com.example.shoppingMall.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeliveryService {
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    DeliveryRepository deliveryRepository;

    public List<DeliveryDto> userDeliveryList(String userId) {
        UserInfo findUser = userInfoRepository.findByUserId(userId);
        List<DeliveryDto> deliveryDto = deliveryRepository.findByUserInfo_userInfoCode(findUser.getUserInfoCode())
                .stream().map(DeliveryDto::fromDeliveryEntity).toList();
        return deliveryDto;
    }

    public List<DeliveryDto> deliveryList(Long userInfoCode) {
        UserInfo findUser = userInfoRepository.findById(userInfoCode).orElse(null);
        List<DeliveryDto> deliveryDto = deliveryRepository.findByUserInfo_userInfoCode(findUser.getUserInfoCode())
                .stream().map(DeliveryDto::fromDeliveryEntity).toList();
        return deliveryDto;
    }


    public Long saveDelivery(DeliveryDto deliveryDto, String userId) {
        UserInfo findUser = userInfoRepository.findByUserId(userId);
//        log.info(findUser.toString());
        Delivery delivery = DeliveryDto.toDeliveryEntity(deliveryDto);
        delivery.setUserInfo(findUser);
        deliveryRepository.save(delivery);
        Long deliveryCode = deliveryRepository.findDeliveryCode(findUser.getUserInfoCode());
        return deliveryCode;
    }

    public DeliveryDto findDelivery(Long deliveryCode) {
        return deliveryRepository.findById(deliveryCode).map(DeliveryDto::fromDeliveryEntity).orElse(null);
    }

    public void deleteDelivery(Long deliveryCode) {
        deliveryRepository.deleteById(deliveryCode);
    }

    public DeliveryDto defaultDelivery(Long userInfoCode) {
        UserInfo findUser = userInfoRepository.findById(userInfoCode).orElse(null);
        log.info(findUser.toString());
        DeliveryDto defaultDelivery = DeliveryDto.fromDeliveryEntity(
                deliveryRepository.findByUserInfo_userInfoCodeAndDefaultYn(findUser.getUserInfoCode(), "Y"));
        log.info(defaultDelivery.toString());
        return defaultDelivery;
    }
}
