package com.example.shoppingMall.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
//이 어노테이션을 통해서 JSON타입에서 우리는 아래에 정의한 우리가 원하는 애들만 받을건데 JSON에 있는 애들 매핑 안된다는 오류를 우리는 필요없는 애들 안 받을거야
//처리해서 오류없이 안 받고 사용가능하다.
public record ExRateData(String result, Map<String, BigDecimal> rates) {
//    기본적으로 데이터를 담는 DTO같은 걸 만들면 필드를 정의하고 생성자에서 주입을 하거나 Setter로 주입을 하게하고 Getter로 다시 읽도록 만드는건데
//    record는 이 작업을 생성자만 정의하면 된다. 이걸 만들면 내부에 저장해주는 생성자 코드도 만들어주고 Getter도 만들어준다.
//    그리고 JSON 방식으로 값을 가져오는 방식이 Map 형식이기에 rates를 Map형식으로 받은 것
//    레코드의 특징으로는 한 번 값을 정하고 수정불가능하다.
}
