package com.example.shoppingMall.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImgDto {
    private String imgId;
    private Long productId;
    private String fileName;

}
