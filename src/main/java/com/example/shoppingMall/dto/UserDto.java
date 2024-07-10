package com.example.shoppingMall.dto;

import com.example.shoppingMall.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String password;
    private UserRole userRole;
}
