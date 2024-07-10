package com.example.shoppingMall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulletinBoardDto {
    private Long boardCode;
    private String code;
    private Long productCode;
    private String content;
    private Long userInfoCode;
    private int views; //조회수
    private boolean hasComment;

}
