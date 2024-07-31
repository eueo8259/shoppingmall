package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.CartDto;
import com.example.shoppingMall.entity.Cart;
import com.example.shoppingMall.repository.CartRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    @PersistenceContext
    EntityManager em;

    public List<Cart> findAll(String user) {
        String sql = "SELECT c FROM Cart c WHERE c.userInfo.user.id = :user";
        TypedQuery<Cart> query = em.createQuery(sql, Cart.class).setParameter("user", user);
        List<Cart> cart = null;
        try {
            cart = query.getResultList();
        } catch (NoResultException e) {
            // 필요한 경우 여기에서 적절한 예외 처리
            throw new RuntimeException("장바구니가 비어있습니다.");
        }
        return cart;
    }

    public Long total(String user) {
        String sql = "SELECT SUM(c.quantity) FROM Cart c WHERE c.userInfo.user.id = :user";
        TypedQuery<Long> query = em.createQuery(sql, Long.class).setParameter("user", user);
        return query.getSingleResult();
    }
}
