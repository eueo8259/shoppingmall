package com.example.shoppingMall.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgCode;
    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;
    private String imgUrl;
}