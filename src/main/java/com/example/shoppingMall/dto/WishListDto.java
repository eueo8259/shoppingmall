package com.example.shoppingMall.dto;

import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishListDto {
    private Long wishListCode;
    private UserInfo userInfoCode;
    private Product productCode;
}