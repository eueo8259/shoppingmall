package com.example.shoppingMall.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDto {
    private Long orderCode;
    private Long productCode;
    private Long userInfoCode;
    private int orderQuantity;
    private String orderStatus; //주문 상태값 (배송중, 배송완료, 배송준비)
    private LocalDate orderDate;
    private Long deliveryCode;

}
