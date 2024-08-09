package com.example.shoppingMall.service;

import com.example.shoppingMall.entity.Cart;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.entity.WishList;
import com.example.shoppingMall.repository.ProductRepository;
import com.example.shoppingMall.repository.UserInfoRepository;
import com.example.shoppingMall.repository.WishListRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishService {

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    ProductRepository productRepository;

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


    public WishList add(String user, Long productCode) {
        UserInfo userInfo = userInfoRepository.findByUserId(user);
        Product product = productRepository.findByProductCode(productCode);
        WishList wishList = new WishList();
        wishList.setProduct(product);
        wishList.setUserInfo(userInfo);
        return wishListRepository.save(wishList);
    }
}
