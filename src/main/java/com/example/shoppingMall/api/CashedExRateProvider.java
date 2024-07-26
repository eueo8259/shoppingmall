package com.example.shoppingMall.api;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Component
public class CashedExRateProvider {
    private final ExchangeRate target;

    private BigDecimal cachedExRate;
    //currency마다 달라지니 currency에 맞춰서 만약 US달러로했을경우 그 정보가 캐시에 있으면 걔를 사용하고 없으면 뒤에서 타겟으로 가져오는 방식으로해야한다.
    //여기서는 간단하게 US달러만 가져오는 식으로하고 실제로는 맞춰서 바꾸자
    private LocalDateTime cacheExpiryTime; //캐시 유효시간

    public CashedExRateProvider(ExchangeRate target) {
        this.target = target;
    }

    public BigDecimal getCachedExRate(String currency) {
        if (cachedExRate == null || cacheExpiryTime.isBefore(LocalDateTime.now())) { 
            //cache에 저장된 값이 있는가? 또는 만료시간이 현재시간과 비교했을 때 지나갔는가 를 기준으로 Update
            cachedExRate = this.target.getExRate(currency);

            cacheExpiryTime = LocalDateTime.now().plusDays(1);

        }
        return cachedExRate;
    }
}
