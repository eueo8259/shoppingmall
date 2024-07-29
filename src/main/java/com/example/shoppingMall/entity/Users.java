package com.example.shoppingMall.entity;

import com.example.shoppingMall.constant.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Users {
    @Id
    private String id;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
