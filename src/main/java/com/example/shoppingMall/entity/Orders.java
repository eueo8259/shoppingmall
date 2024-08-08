package com.example.shoppingMall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderCode; // 주문 번호
    @ManyToOne
    @JoinColumn(name = "userInfoCode")
    private UserInfo userInfo;
    private LocalDateTime orderDate = LocalDateTime.now();
    private int paymentPrice;   // 결제 총 금액
    private int discountAmount; // 할인 총 금액 (쿠폰+등급할인)
    @ManyToOne
    @JoinColumn(name = "deliveryCode")
    private Delivery delivery;
}