package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByUserInfo_userInfoCode(@Param("userInfoCode") Long userInfoCode);
}
