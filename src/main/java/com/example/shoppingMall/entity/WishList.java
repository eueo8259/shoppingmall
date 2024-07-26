package com.example.shoppingMall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class WishList {
    @Id
    private Long wishListCode;
    @ManyToOne
    @JoinColumn(name = "userInfoCode")
    private UserInfo userInfo;
    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;
}