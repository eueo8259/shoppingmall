package com.example.shoppingMall.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;
}
