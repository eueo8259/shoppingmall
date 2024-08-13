package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.UserPointDto;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.entity.UserPoint;
import com.example.shoppingMall.repository.UserInfoRepository;
import com.example.shoppingMall.repository.UserPointRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserPointService {
    @Autowired
    UserPointRepository userPointRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    public Page<UserPointDto> findById(String userId, Pageable pageable) {
        UserInfo findUser = userInfoRepository.findByUserId(userId);
        Page<UserPointDto> userPointDto = userPointRepository.findByUserInfo_userInfoCode(findUser.getUserInfoCode(), pageable)
                .map(UserPointDto::fromUserPointEntity);
        return userPointDto;
    }
    @Transactional
    public String chargePoint(int chargePoint, String userId, String remarks, String popYn) {
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        UserPoint userPoint = new UserPoint();
        userPoint.setOccurDate(LocalDateTime.now());
        userPoint.setChargePoint(chargePoint);
        userPoint.setUserInfo(userInfo);
        userPoint.setRemarks(remarks);
        userPointRepository.save(userPoint);

        userInfo.setCurrentPoint(userInfo.getCurrentPoint()+userPoint.getChargePoint());
        userInfoRepository.save(userInfo);
        return popYn;
    }
}
