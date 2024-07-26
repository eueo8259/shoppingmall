package com.example.shoppingMall.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Orders {
    @Id
    private Long orderId;
    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "userInfoId")
    private UserInfo userInfo;
    private int orderQuantity;
    @ManyToOne
    @JoinColumn(name = "code")
    private Common common;
    private LocalDate orderDate;
    @ManyToOne
    @JoinColumn(name = "deliveryCode")
    private Delivery delivery;
}
