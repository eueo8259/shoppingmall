package com.example.shoppingMall.entity;

import jakarta.persistence.*;

@Entity
public class Review {
    @Id
    private Long reviewId;
    @ManyToOne
    @JoinColumn(name = "userInfoId")
    private UserInfo userInfo;
    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;
    private int rating;
    @Column(length = 1000)
    private String reviewText;

}
