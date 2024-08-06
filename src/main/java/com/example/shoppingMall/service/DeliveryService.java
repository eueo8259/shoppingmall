package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.DeliveryDto;
import com.example.shoppingMall.entity.Delivery;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.repository.DeliveryRepository;
import com.example.shoppingMall.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public void saveDelivery(DeliveryDto deliveryDto, String userId) {
        UserInfo findUser = userInfoRepository.findByUserId(userId);
        Delivery delivery = DeliveryDto.toDeliveryEntity(deliveryDto);
        delivery.setUserInfo(findUser);
        deliveryRepository.save(delivery);
    }

    public DeliveryDto findDelivery(Long code) {
        return deliveryRepository.findById(code).map(DeliveryDto::fromDeliveryEntity).orElse(null);
    }

    public void deleteDelivery(Long code) {
        deliveryRepository.deleteById(code);
    }
}
