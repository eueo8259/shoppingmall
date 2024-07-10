package com.example.shoppingMall.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    private Long productCode;
    private String productName;
    private int productQuantity;
    private int productPrice;
    private LocalDateTime productRegisterDate;
    @ManyToOne
    @JoinColumn(name = "categoryCode")
    private Category category;
    private String status;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImg> imgList = new ArrayList<>();
    private String description;
    private double productRate; //평점
}
