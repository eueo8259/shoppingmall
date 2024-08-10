package com.example.shoppingMall.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BulletinBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardCode;

    private String boardTitle;

    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;

    @Column(length = 1000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "userInfoCode")
    private UserInfo userInfo;
    private int views; //조회수
    private boolean hasComment;
}