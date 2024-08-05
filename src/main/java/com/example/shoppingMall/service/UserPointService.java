package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.UserPointDto;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.entity.UserPoint;
import com.example.shoppingMall.repository.UserInfoRepository;
import com.example.shoppingMall.repository.UserPointRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserPointService {
    @Autowired
    UserPointRepository userPointRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    public List<UserPointDto> findById(String userId) {
        UserInfo findUser = userInfoRepository.findByUserId(userId);
        List<UserPointDto> userPointDto = userPointRepository.findByUserInfo_userInfoCode(findUser.getUserInfoCode())
                .stream().map(x -> UserPointDto.fromUserPointEntity(x)).toList();
        return userPointDto;
    }
    @Transactional
    public void chargePoint(int chargePoint, String userId, String remarks) {
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        UserPoint userPoint = new UserPoint();
        userPoint.setOccurDate(LocalDateTime.now());
        userPoint.setChargePoint(chargePoint);
        userPoint.setUserInfo(userInfo);
        userPoint.setRemarks(remarks);
        userPointRepository.save(userPoint);

        userInfo.setCurrentPoint(userInfo.getCurrentPoint()+userPoint.getChargePoint());
        userInfoRepository.save(userInfo);
    }
}
