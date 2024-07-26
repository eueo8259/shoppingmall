package com.example.shoppingMall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeDto {
    private Long orderId;
    private String currency;

    private BigDecimal foreignCurrencyAmount; //환전 하기 전 외국 금액
    private BigDecimal exRate; //환율
    private BigDecimal convertedAmount; //환전된 금액
    private LocalDateTime validUntil; //환율 변동 유효 시간

}
