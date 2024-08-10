package com.example.shoppingMall.repository;

import com.example.shoppingMall.dto.OrderDto;
import com.example.shoppingMall.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query(value = "SELECT MAX(order_code) FROM orders where user_info_code = :userInfoCode", nativeQuery = true)
    Long findCode(@Param("userInfoCode") Long userInfoCode);

    List<Orders> findByUserInfo_userInfoCode(@Param("userInfoCode")Long userInfoCode);

}
