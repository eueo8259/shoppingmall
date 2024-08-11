package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.ReviewDto;
import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.entity.Product;
import com.example.shoppingMall.entity.Review;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.repository.ReviewRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    @Autowired
    EntityManager em;

    @Transactional
    public ReviewDto getReviewBowl(Long productCode, Long userInfoCode) {
        Product product = em.find(Product.class, productCode);
        UserInfo userInfo = em.find(UserInfo.class, userInfoCode);
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setProduct(product);
        reviewDto.setUserInfo(userInfo);
        return reviewDto;
    }
    @Transactional
    public void insertReview(ReviewDto reviewDto) {
        Review review = new Review();
        Product product = em.find(Product.class, reviewDto.getProductCode());
        UserInfo userInfo = em.find(UserInfo.class, reviewDto.getUserInfoCode());
        review.setReviewText(reviewDto.getReviewText());
        review.setReviewTitle(reviewDto.getReviewTitle());
        review.setRating(reviewDto.getRating());
        review.setProduct(product);
        review.setUserInfo(userInfo);
        em.persist(review);
    }

    public List<ReviewDto> findProductReview(Long productCode) {
        String sql = "SELECT r FROM Review r WHERE r.product.productCode = :productCode";
        TypedQuery<Review> query = em.createQuery(sql, Review.class);
        query.setParameter("productCode", productCode);
        List<Review> reviewList = query.getResultList();

        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (Review r : reviewList){
            ReviewDto reviewDto = new ReviewDto();

            reviewDto.setReviewTitle(r.getReviewTitle());
            reviewDto.setReviewText(r.getReviewText());
            reviewDto.setProduct(r.getProduct());
            reviewDto.setRating(r.getRating());
            reviewDto.setReviewCode(r.getReviewCode());
            reviewDto.setUserInfo(r.getUserInfo());
            reviewDtoList.add(reviewDto);
        }
        return reviewDtoList;
    }
}
