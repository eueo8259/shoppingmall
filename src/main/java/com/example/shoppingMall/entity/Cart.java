package com.example.shoppingMall.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartCode;

    @ManyToOne
    @JoinColumn(name = "userInfoCode")
    private UserInfo userInfo;

    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;

    private int quantity;
}
