package com.example.shoppingMall.dto;

import com.example.shoppingMall.entity.BulletinBoard;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BulletinBoardDto {
    private Long boardCode;
    private String boardTitle;
    private Product productCode;
    private String content;
    private UserInfo userInfoCode;
    private int views; //조회수
    private boolean hasComment;

    public static BulletinBoardDto fromBoardEntity (BulletinBoard bulletinBoard){
        return new BulletinBoardDto(
                bulletinBoard.getBoardCode(),
                bulletinBoard.getBoardTitle(),
                bulletinBoard.getProduct(),
                bulletinBoard.getContent(),
                bulletinBoard.getUserInfo(),
                bulletinBoard.getViews(),
                bulletinBoard.isHasComment()
        );

    }

}

