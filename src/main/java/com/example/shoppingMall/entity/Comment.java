package com.example.shoppingMall.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Comment {
    @Id
    private Long commentCode;
    @Column(length = 1000)
    private String commentText;
    private LocalDate creationDate;
    @ManyToOne
    @JoinColumn(name = "userInfoCode")
    private UserInfo userInfo; //(admin) 작성자
    @ManyToOne
    @JoinColumn(name = "boardCode")
    private BulletinBoard board; //문의 고객 정보가 나옴
}
