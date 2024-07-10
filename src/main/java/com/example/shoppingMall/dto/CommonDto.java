package com.example.shoppingMall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonDto {
    private String code;
    private String codeName;
    private int parentCode;
    private String parentCodeName;
}
