package com.example.shoppingMall.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishListCode;

    @ManyToOne
    @JoinColumn(name = "userInfoCode")
    private UserInfo userInfo;

    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;
}
