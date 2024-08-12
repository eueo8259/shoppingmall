package com.example.shoppingMall.dto;
import com.example.shoppingMall.entity.Category;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.entity.ProductImg;
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
    private Long userInfoCode; //사실 UserInfo의 userInfo 이런식으로 받아서 이 userInfo안에서 또 값을 꺼내왔어야 하는데 Dto설계를 첨에 잘못했다
    private Category categoryCode;
    private String productStatus;
    private String mainImg;
    private List<String> imgList = new ArrayList<>();
    private String description;
    private double productRate; //평점
    private int SalesStatus; //판매현황
}
