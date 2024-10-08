package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.Cart;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface CartRepository extends JpaRepository<Cart, Long> {
}
