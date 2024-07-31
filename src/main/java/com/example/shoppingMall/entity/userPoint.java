package com.example.shoppingMall.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class userPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;
    @ManyToOne
    @JoinColumn(name = "id")
    private Users user;
    private LocalDateTime occurDate;
    private int chargePoint;
    private int usePoint;
}
