package com.example.shoppingMall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Common {
    @Id
    private String code;
    private String codeName;
    private int parentCode;
    private String parentCodeName;
}
