package com.example.shoppingMall.dto;

import com.example.shoppingMall.constant.UserRole;
import com.example.shoppingMall.entity.Users;
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

    public static UserDto fromUserEntity(Users users) {
        return new UserDto(
                users.getId(),
                users.getPassword(),
                users.getUserRole()
        );
    }

    public static Users toUserEntity(UserDto dto) {
        Users user = new Users();
        user.setId(dto.getId());
        user.setPassword(dto.getPassword());
        user.setUserRole(dto.getUserRole());
        return user;
    }
}