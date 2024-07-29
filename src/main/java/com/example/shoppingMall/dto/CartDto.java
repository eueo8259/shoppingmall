package com.example.shoppingMall.dto;

import com.example.shoppingMall.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long cartCode;
    private String user;
    private String productCode;
    private int quantity;

    public static CartDto fromCartEntity(Cart cart){
        return new CartDto(
                cart.getCartCode(),
                cart.getUser().getId(),
                cart.getProduct().getProductName(),
                cart.getQuantity()
        );
    }
}
