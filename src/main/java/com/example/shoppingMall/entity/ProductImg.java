package com.example.shoppingMall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductImg {
    @Id
    private String imgCode;
    @ManyToOne
    @JoinColumn(name = "productCode")
    private Product product;
    private String fileName;
}
