package com.example.shoppingMall.dto;

import com.example.shoppingMall.entity.BulletinBoard;
import com.example.shoppingMall.entity.Comment;
import com.example.shoppingMall.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CommentDto {
    private Long commentCode;
    private String commentText;
    private LocalDate creationDate;
    private UserInfo userInfoCode;
    private BulletinBoard boardCode;

    public static CommentDto fromCommentEntity (Comment comment){
        return new CommentDto(
              comment.getCommentCode(),
              comment.getCommentText(),
              comment.getCreationDate(),
              comment.getUserInfo(),
              comment.getBoard()
        );
    }
}
