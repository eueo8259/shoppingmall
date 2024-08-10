package com.example.shoppingMall.dto;

import com.example.shoppingMall.entity.Cart;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long cartCode;
    private Users user;
    private Product productCode;
    private int quantity;

    public static CartDto fromCartEntity(Cart cart){
        return new CartDto(
                cart.getCartCode(),
                cart.getUserInfo().getUser(),
                cart.getProduct(),
                cart.getQuantity()
        );
    }
}
