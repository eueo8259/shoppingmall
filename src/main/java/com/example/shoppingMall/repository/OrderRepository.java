package com.example.shoppingMall.repository;

import com.example.shoppingMall.dto.OrderDto;
import com.example.shoppingMall.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query(value = "SELECT MAX(order_code) FROM orders where user_info_code = :userInfoCode", nativeQuery = true)
    Long findCode(@Param("userInfoCode") Long userInfoCode);

    Page<Orders> findByUserInfo_userInfoCode(@Param("userInfoCode")Long userInfoCode, Pageable pageable);

    @Query(value = "SELECT ui.user_info_code AS userCode, " +
            "SUM(o.payment_price - o.discount_amount) AS sumAmount " +
            "FROM orders o JOIN user_info ui ON o.user_info_code = ui.user_info_code " +
            "WHERE YEAR(o.order_date) = YEAR(CURDATE()) " +
            "AND MONTH(o.order_date) = MONTH(CURDATE()) " +
            "GROUP BY ui.user_info_code", nativeQuery = true)
    List<Map<String, Object>> findAmountsByUser();

}
