package com.example.shoppingMall.constant;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    SELLER("ROLE_SELLER"),
    USER("ROLE_USER"),
    TEMP("ROLE_TEMP");


    private String value;
    UserRole(String value){
        this.value = value;
    }

}
