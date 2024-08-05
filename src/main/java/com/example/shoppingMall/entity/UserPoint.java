package com.example.shoppingMall.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class UserPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;
    @ManyToOne
    @JoinColumn(name = "userInfoCode")
    private UserInfo userInfo;
    private LocalDateTime occurDate;
    private int chargePoint;
    private int usePoint;
    private String remarks;
}
