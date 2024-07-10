package com.example.shoppingMall.entity;

import jakarta.persistence.*;

@Entity
public class BulletinBoard {
    @Id
    private Long boardId;
    @ManyToOne
    @JoinColumn(name = "code")
    private Common common;

    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;

    @Column(length = 1000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "userInfoId")
    private UserInfo userInfo;
    private int views; //조회수
    private boolean hasComment;
}
