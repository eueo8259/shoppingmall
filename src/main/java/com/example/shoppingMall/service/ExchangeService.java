package com.example.shoppingMall.service;

import com.example.shoppingMall.api.CashedExRateProvider;
import com.example.shoppingMall.dto.ExchangeDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ExchangeService {
    private final CashedExRateProvider exchangeRate;
    //캐시에 저장되어있는 환율 꺼내서 가져오기

    public ExchangeService(CashedExRateProvider exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public ExchangeDto prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) {
            BigDecimal exRate = exchangeRate.getCachedExRate(currency);
            //금액 계산
            BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate); //BigDecimal 타입이라 이런식으로 곱하기 해준다.
            //유효 시간 계산
            LocalDateTime validUntil = LocalDateTime.now().plusDays(1);
            return new ExchangeDto(orderId, currency, foreignCurrencyAmount, exRate, convertedAmount, validUntil);
        }

    }
