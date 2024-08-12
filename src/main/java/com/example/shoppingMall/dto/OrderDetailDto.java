package com.example.shoppingMall.dto;

import com.example.shoppingMall.entity.Delivery;
import com.example.shoppingMall.entity.OrderDetail;
import com.example.shoppingMall.entity.Orders;
import com.example.shoppingMall.entity.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    private Long orderNum; // 오더 테이블 순번
    private Orders orders; // 주문번호
    private Product product;
    private int orderQuantity;
    private int orderPrice;
    private String orderStatus; //주문 상태값 (배송중, 배송완료, 배송준비)


    public static OrderDetailDto fromOrderDetailEntity(OrderDetail orders) {
        return new OrderDetailDto(
                orders.getOrderNum(),
                orders.getOrders(),
                orders.getProduct(),
                orders.getOrderQuantity(),
                orders.getOrderPrice(),
                orders.getOrderStatus()
        );
    }

    public static OrderDetail toOrderDetailEntity(OrderDetailDto dto) {
        OrderDetail orders = new OrderDetail();
        orders.setOrderNum(dto.getOrderNum());
        orders.setOrders(dto.getOrders());
        orders.setProduct(dto.getProduct());
        orders.setOrderQuantity(dto.getOrderQuantity());
        orders.setOrderQuantity(dto.getOrderPrice());
        orders.setOrderStatus(dto.getOrderStatus());
        return orders;
    }
}
