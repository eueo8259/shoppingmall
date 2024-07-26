package com.example.shoppingMall.entity;

import jakarta.persistence.*;

@Entity
public class BulletinBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long boardId;
    @ManyToOne
    @JoinColumn(name = "code")
    private Common common;

    private String boardTitle;

    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;

    @Column(length = 1000)
    private String content;

    private String creationDate;

    @ManyToOne
    @JoinColumn(name = "userInfoCode")
    private UserInfo userInfo;
    private int views; //조회수
    private boolean hasComment;
}
