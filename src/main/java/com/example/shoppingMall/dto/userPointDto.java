package com.example.shoppingMall.dto;

import com.example.shoppingMall.entity.Users;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userPointDto {

    private Long pointId;
    private Users user;
    private LocalDateTime occurDate;
    private int chargePoint;
    private int usePoint;
}
