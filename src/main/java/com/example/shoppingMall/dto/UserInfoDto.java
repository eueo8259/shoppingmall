package com.example.shoppingMall.dto;

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
    private Date birthDate;
//    private List<Delivery> deliveryList = new ArrayList<>(); 양방향 매핑인데 필요한지 모르겠음 나중에 수정하는걸로하고 일단 주석처리
    private String grade;
    private String id;
    private String isActive;

}
