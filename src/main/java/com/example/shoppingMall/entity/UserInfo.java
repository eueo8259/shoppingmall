package com.example.shoppingMall.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userInfoCode;
    private String userName;
    private String RRN; //주민번호 약자
    private String phoneNumber;
    private LocalDate createdDate;
    private String email;
    private LocalDate birthDate;
    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Delivery> deliveryList = new ArrayList<>();
    @ColumnDefault("'일반'")
    private String grade;
    @ManyToOne
    @JoinColumn(name = "id")
    private Users user;
    @ColumnDefault("'Y'")
    private String isActive;
}