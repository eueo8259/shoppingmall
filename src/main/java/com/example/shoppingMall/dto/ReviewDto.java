package com.example.shoppingMall.dto;

import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long reviewCode;
    private Long productCode;
    private Long userInfoCode;
    private UserInfo userInfo;
    private Product product;
    private int rating;
    private String reviewTitle;
    private String reviewText;
}
