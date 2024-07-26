package com.example.shoppingMall.dto;

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
    private Long userInfoCode;
    private Long boardCode;

}
