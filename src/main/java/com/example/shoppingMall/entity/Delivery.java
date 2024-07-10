package com.example.shoppingMall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Delivery {
    @Id
    private Long deliveryCode;
    @ManyToOne
    @JoinColumn(name = "userInfoId")
    private UserInfo userInfo;
    private int postalCode;
    private String address;
    private String contactNumber;
}
