package com.example.shoppingMall.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long orderId;
    private Long productCode;
    private Long userInfoId;
    private int orderQuantity;
    private String code;
    private LocalDate orderDate;
    private Long deliveryCode;

}
