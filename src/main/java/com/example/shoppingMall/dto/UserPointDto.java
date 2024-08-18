package com.example.shoppingMall.dto;

import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.entity.UserPoint;
import com.example.shoppingMall.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPointDto {

    private Long pointId;
    private UserInfo userInfo;
    private LocalDateTime occurDate;
    private int chargePoint;
    private int usePoint;
    private String remarks;

    public static UserPointDto fromUserPointEntity(UserPoint userPoint) {
        return new UserPointDto(
                userPoint.getPointId(),
                userPoint.getUserInfo(),
                userPoint.getOccurDate(),
                userPoint.getChargePoint(),
                userPoint.getUsePoint(),
                userPoint.getRemarks()
        );
    }

    public static UserPoint toUserPointEntity(UserPointDto dto) {
        UserPoint userPoint = new UserPoint();
        userPoint.setPointId(dto.getPointId());
        userPoint.setUserInfo(dto.getUserInfo());
        userPoint.setOccurDate(dto.getOccurDate());
        userPoint.setChargePoint(dto.getChargePoint());
        userPoint.setUsePoint(dto.getUsePoint());
        userPoint.setRemarks(dto.getRemarks());
        return userPoint;
    }
}