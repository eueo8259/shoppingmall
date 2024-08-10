package com.example.shoppingMall.dto;
import com.example.shoppingMall.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long orderCode;
    private UserInfo userInfo;
    private LocalDateTime orderDate;
    private int paymentPrice;   // 결제 총 금액
    private int discountAmount;     // 할인 총 금액 (쿠폰+등급할인)
    private Delivery delivery;
    private List<OrderDetailDto> orderDetailList;

    public OrderDto(Long orderCode, UserInfo userInfo, LocalDateTime orderDate, int paymentPrice, int discountAmount, Delivery delivery) {
        this.orderCode = orderCode;
        this.userInfo = userInfo;
        this.orderDate = orderDate;
        this.paymentPrice = paymentPrice;
        this.discountAmount = discountAmount;
        this.delivery = delivery;
    }

    public static OrderDto fromOrdersEntity(Orders orders) {
        return new OrderDto(
                orders.getOrderCode(),
                orders.getUserInfo(),
                orders.getOrderDate(),
                orders.getPaymentPrice(),
                orders.getDiscountAmount(),
                orders.getDelivery()
        );
    }

    public static Orders toOrdersEntity(OrderDto dto) {
        Orders orders = new Orders();
        orders.setOrderCode(dto.getOrderCode());
        orders.setUserInfo(dto.getUserInfo());
        orders.setOrderDate(dto.getOrderDate());
        orders.setPaymentPrice(dto.getPaymentPrice());
        orders.setDiscountAmount(dto.getDiscountAmount());
        orders.setDelivery(dto.getDelivery());
        return orders;
    }
}
