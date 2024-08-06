package com.example.shoppingMall.service;

import com.example.shoppingMall.entity.Cart;
import com.example.shoppingMall.entity.WishList;
import com.example.shoppingMall.repository.WishListRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    @PersistenceContext
    EntityManager em;

    public List<WishList> findAll(String user) {
        String sql = "SELECT w FROM WishList w WHERE w.userInfo.user.id = :user";
        TypedQuery<WishList> query = em.createQuery(sql, WishList.class).setParameter("user", user);
        List<WishList> WishList = null;
        try {
            WishList = query.getResultList();
        } catch (NoResultException e) {
            // 필요한 경우 여기에서 적절한 예외 처리
            throw new RuntimeException("찜 목록이 없습니다.");
        }
        return WishList;
    }

    public void removeItem(Long itemId) {
        wishListRepository.deleteById(itemId);
    }
}
