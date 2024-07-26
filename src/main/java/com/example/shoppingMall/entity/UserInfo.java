package com.example.shoppingMall.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserInfo {
    @Id
    private Long userInfoCode;
    private String userName;
    private String RRN; //주민번호 약자
    private String phoneNumber;
    private LocalDate createdDate;
    private String email;
    private LocalDate birthdate;
    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Delivery> deliveryList = new ArrayList<>();
    private String grade;
    @ManyToOne
    @JoinColumn(name = "id")
    private Users user;
    private String isActive;
}