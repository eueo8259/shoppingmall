package com.example.shoppingMall.dto;

import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    private Long userInfoCode;
    private String userName;
    private String RRN; //주민번호 약자
    private String phoneNumber;
    private LocalDate createdDate;
    private String email;
    private LocalDate birthDate;
//    private List<Delivery> deliveryList = new ArrayList<>(); 양방향 매핑인데 필요한지 모르겠음 나중에 수정하는걸로하고 일단 주석처리
    private String grade;
    private Users user;
    private String isActive;

    public static UserInfoDto fromUserInfoEntity(UserInfo userInfo) {
        return new UserInfoDto(
                userInfo.getUserInfoCode(),
                userInfo.getUserName(),
                userInfo.getRRN(),
                userInfo.getPhoneNumber(),
                userInfo.getCreatedDate(),
                userInfo.getEmail(),
                userInfo.getBirthDate(),
                userInfo.getGrade(),
                userInfo.getUser(),
                userInfo.getIsActive()
        );
    }
    public static UserInfo toUserInfoEntity(UserInfoDto dto) {
        UserInfo info = new UserInfo();
        info.setUserInfoCode(dto.getUserInfoCode());
        info.setUserName(dto.getUserName());
        info.setRRN(dto.getRRN());
        info.setPhoneNumber(dto.getPhoneNumber());
        info.setCreatedDate(dto.getCreatedDate());
        info.setEmail(dto.getEmail());
        info.setBirthDate(dto.getBirthDate());
        info.setGrade(dto.getGrade());
        info.setUser(dto.getUser());
        info.setIsActive(dto.getIsActive());
        return info;
    }

}
