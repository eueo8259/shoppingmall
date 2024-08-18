package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.UserPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserPointRepository extends JpaRepository<UserPoint, Long> {
    Page<UserPoint> findByUserInfo_userInfoCode(@Param("userInfoCode") Long userInfoCode, Pageable pageable);
}
