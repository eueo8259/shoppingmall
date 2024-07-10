package com.example.shoppingMall.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Comment {
    @Id
    private Long commentId;

    @Column(length = 1000)
    private String commentText;
    private LocalDate creationDate;
    @ManyToOne
    @JoinColumn(name = "userInfoId")
    private UserInfo userInfo;
    @ManyToOne
    @JoinColumn(name = "boardId")
    private BulletinBoard board;
}
