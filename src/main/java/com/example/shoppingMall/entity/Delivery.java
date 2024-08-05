package com.example.shoppingMall.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryCode;
    @ManyToOne
    @JoinColumn(name = "userInfoCode")
    private UserInfo userInfo;
    private String postalCode;
    private String address;
    private String contactNumber; //받는 사람 번호
    private String contactName; //받는 사람 이름
    private String defaultYn; // 기본 배송지 여부
}
