package com.example.shoppingMall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewCode;
    @ManyToOne
    @JoinColumn(name = "userInfoCode")
    private UserInfo userInfo;
    @ManyToOne
    @JoinColumn(name = "productCode")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;
    private int rating;
    @Column(length = 1000)
    private String reviewTitle;
    @Column(length = 1000)
    private String reviewText;
}
