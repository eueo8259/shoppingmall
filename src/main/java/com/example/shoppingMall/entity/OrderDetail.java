package com.example.shoppingMall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNum; // 오더 테이블 순번
    @ManyToOne
    @JoinColumn(name = "orderCode")
    private Orders orders; // 주문번호
    @ManyToOne
    @JoinColumn(name = "productCode")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;
    private int orderQuantity;
    private int orderPrice;
    private String orderStatus = "주문완료"; //주문 상태값 (배송중, 배송완료, 배송준비)
}