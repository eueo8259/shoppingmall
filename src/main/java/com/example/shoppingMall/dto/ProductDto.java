package com.example.shoppingMall.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long productCode;
    private String productName;
    private int productQuantity;
    private int productPrice;
    private LocalDateTime productRegisterDate;
    private String code;
//    private List<ProductImg> imgList = new ArrayList<>(); 양방향 매핑땜시 List인데 Dto에도 필요한지는 모르겠음 일단 주석처리
    private String description;
    private double productRate; //평점

}
