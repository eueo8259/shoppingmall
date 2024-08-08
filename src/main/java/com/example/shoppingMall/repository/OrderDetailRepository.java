package com.example.shoppingMall.repository;

import com.example.shoppingMall.dto.OrderDetailDto;
import com.example.shoppingMall.entity.Delivery;
import com.example.shoppingMall.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    OrderDetailDto findByOrders_orderCode(@Param("orderCode")Long orderCode);
}
