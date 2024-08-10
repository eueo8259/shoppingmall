package com.example.shoppingMall.service;

import com.example.shoppingMall.api.CashedExRateProvider;
import com.example.shoppingMall.dto.CartDto;
import com.example.shoppingMall.entity.Cart;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.repository.CartRepository;
import com.example.shoppingMall.repository.ProductRepository;
import com.example.shoppingMall.repository.UserInfoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    @PersistenceContext
    EntityManager em;

    @Autowired
    CashedExRateProvider exRateProvider;

    public List<Cart> findAll(String user) {
        String sql = "SELECT c FROM Cart c WHERE c.userInfo.user.id = :user";
        TypedQuery<Cart> query = em.createQuery(sql, Cart.class).setParameter("user", user);
        List<Cart> cart = null;

        try {
            cart = query.getResultList();
            for (Cart c : cart){
                Product product = c.getProduct();

                BigDecimal priceInCurrency = exRateProvider.getCachedExRate(product.getCurrency())
                        .multiply(product.getProductPrice());
                BigDecimal roundedPrice = priceInCurrency.setScale(0, RoundingMode.HALF_UP);

                product.setProductPrice(roundedPrice);
                c.setProduct(product);
            }
        } catch (NoResultException e) {
            // 필요한 경우 여기에서 적절한 예외 처리
            throw new RuntimeException("장바구니가 비어있습니다.");
        }
        return cart;
    }


    @Transactional
    public void updateQuantity(Long itemId, int quantity) {
        Cart cart = cartRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Invalid item ID: " + itemId));
        cart.setQuantity(quantity);
        cartRepository.save(cart); // 변경된 수량을 저장

        
    }
    @Transactional
    public void removeItem(Long itemId) {
        cartRepository.deleteById(itemId);
    }

    public List<Cart> findCartCodes(Long[] orderItems) {
        List<Cart> cartList = new ArrayList<>();
        Cart cart = new Cart();
        for(Long item : orderItems) {
            cart = cartRepository.findById(item).orElse(null);
            cartList.add(cart);
        }
        return cartList;
    }


    public Cart add(String user, Long productCode) {
        UserInfo userInfo = userInfoRepository.findByUserId(user);
        Product product = productRepository.findByProductCode(productCode);
        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setQuantity(1);
        cart.setUserInfo(userInfo);
        return cartRepository.save(cart);
    }
}
