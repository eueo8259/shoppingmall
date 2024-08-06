package com.example.shoppingMall.repository;

import com.example.shoppingMall.dto.UserPointDto;
import com.example.shoppingMall.entity.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserPointRepository extends JpaRepository<UserPoint, Long> {
    List<UserPoint> findByUserInfo_userInfoCode(@Param("userInfoCode") Long userInfoCode);
}
