package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.OrderDetailDto;
import com.example.shoppingMall.dto.OrdersDto;
import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.entity.*;
import com.example.shoppingMall.repository.OrderDetailRepository;
import com.example.shoppingMall.repository.OrderRepository;
import com.example.shoppingMall.repository.UserInfoRepository;
import com.example.shoppingMall.repository.UserPointRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    UserPointRepository userPointRepository;
    @Autowired
    UserInfoRepository userInfoRepository;


    public void insertOrder(OrdersDto orderDto, List<Map<String, Object>> orderDetailList) {
        log.info(orderDto.getUserInfo().getUserInfoCode().toString());
        Long userInfoCode = orderDto.getUserInfo().getUserInfoCode();

        UserInfo userInfo = userInfoRepository.findById(orderDto.getUserInfo().getUserInfoCode()).orElse(null);
        userInfo.setCurrentPoint(userInfo.getCurrentPoint() - orderDto.getPaymentPrice());
        userInfoRepository.save(userInfo);

        Orders orders = new Orders();
        orders.setUserInfo(userInfo);
        orders.setPaymentPrice(orderDto.getPaymentPrice());
        orders.setDiscountAmount(orderDto.getDiscountAmount());
        orders.setDelivery(new Delivery());
        orders.getDelivery().setDeliveryCode(orderDto.getDelivery().getDeliveryCode());
        orderRepository.save(orders);

        Long orderCode = orderRepository.findCode(orders.getUserInfo().getUserInfoCode());

        OrderDetail orderDetail = new OrderDetail();
        for(int i = 0; i < orderDetailList.size(); i++) {
            orderDetail.setOrders(new Orders());
            orderDetail.setProduct(new Product());
            orderDetail.getOrders().setOrderCode(orderCode);
            orderDetail.getProduct().setProductCode(Long.valueOf(String.valueOf(orderDetailList.get(i).get("productCode"))));
            orderDetail.setOrderQuantity(Integer.parseInt(String.valueOf(orderDetailList.get(i).get("orderQuantity"))));
            orderDetail.setOrderPrice(Integer.parseInt(String.valueOf(orderDetailList.get(i).get("orderPrice"))));
        }
        orderDetailRepository.save(orderDetail);

        UserPoint userPoint = new UserPoint();
        userPoint.setUserInfo(userInfo);
        userPoint.setUsePoint(orderDto.getPaymentPrice());
        userPoint.setRemarks("주문번호: " + orderCode);
        userPointRepository.save(userPoint);
    }

    public OrdersDto findOrders(Principal principal) {
        UserInfo userInfo = userInfoRepository.findByUserId(principal.getName());
        OrdersDto ordersDto = orderRepository.findByUserInfo_userInfoCode(userInfo.getUserInfoCode());
        return ordersDto;
    }

    public OrderDetailDto findOrderDetail(Long orderCode) {
        OrderDetailDto orderDetailDto = orderDetailRepository.findByOrders_orderCode(orderCode);
        return orderDetailDto;
    }
}
