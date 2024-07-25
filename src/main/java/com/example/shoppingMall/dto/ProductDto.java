package com.example.shoppingMall.dto;
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
    private BigDecimal productPrice;
    private String currency;
    private LocalDateTime productRegisterDate;
    private String categoryCode;
    private String productStatus;
    private String mainImg;
    private List<String> imgList = new ArrayList<>();
    private String description;
    private double productRate; //평점


}
