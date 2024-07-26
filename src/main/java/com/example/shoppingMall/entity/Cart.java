package com.example.shoppingMall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Cart {
    @Id
    private Long cartCode;
    @ManyToOne
    @JoinColumn(name = "userInfoCode")
    private UserInfo userInfo;

    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;

    private int quantity;
}