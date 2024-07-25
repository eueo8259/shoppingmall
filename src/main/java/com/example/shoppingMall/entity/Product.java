package com.example.shoppingMall.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCode;
    private String productName;
    private int productQuantity;
    private BigDecimal productPrice;
    private LocalDateTime productRegisterDate;
    String currency; //상품 통화 명칭 ex)USD, KRW, EUR
    @ManyToOne
    @JoinColumn(name = "categoryCode")
    private Category category;
    private String status;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImg> imgList = new ArrayList<>();
    private String description;
    private double productRate; //평점
}
