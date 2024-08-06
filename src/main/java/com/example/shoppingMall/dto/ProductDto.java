package com.example.shoppingMall.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long productCode;
    private String productName;
    private int productQuantity;
    private BigDecimal productPrice; //환율까지 적용한 가격
    private BigDecimal originalPrice; //환율 적용 전 currency 통화일 때의 가격
    private String currency;
    private LocalDateTime productRegisterDate;
    private Long userInfoCode;
    private String categoryCode;
    private String categoryName;
    private String productStatus;
    private String mainImg;
    private List<String> imgList = new ArrayList<>();
    private String description;
    private double productRate; //평점
}
