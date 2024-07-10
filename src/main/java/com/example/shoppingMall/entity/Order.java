package com.example.shoppingMall.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Order {
    @Id
    private Long orderCode;
    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "userInfoCode")
    private UserInfo userInfo;
    private int orderQuantity;
    private String orderStatus; //주문 상태값 (배송중, 배송완료, 배송준비)
    private LocalDate orderDate;
    @ManyToOne
    @JoinColumn(name = "deliveryCode")
    private Delivery delivery;
}
