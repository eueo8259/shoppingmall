package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.OrderDetailDto;
import com.example.shoppingMall.dto.OrderDto;
import com.example.shoppingMall.entity.*;
import com.example.shoppingMall.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
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
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductService productService;
    @Autowired
    UserCouponRepository userCouponRepository;

    public void insertOrder(OrderDto orderDto, List<Map<String, Object>> orderDetailList, Long[] cartCodeList, Long couponCode) {
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
            productService.buyProduct(Long.valueOf(String.valueOf(orderDetailList.get(i).get("productCode"))), Integer.parseInt(String.valueOf(orderDetailList.get(i).get("orderQuantity"))));
        }
        orderDetailRepository.save(orderDetail);

        UserPoint userPoint = new UserPoint();
        userPoint.setUserInfo(userInfo);
        userPoint.setUsePoint(orderDto.getPaymentPrice());
        userPoint.setRemarks("주문번호: " + orderCode);
        userPointRepository.save(userPoint);

        for(int i = 0; i < cartCodeList.length; i++){
            cartRepository.deleteById(cartCodeList[i]);
        }
        if(couponCode != null) {
            userCouponRepository.deleteById(couponCode);
        }
        log.info(couponCode.toString());
    }

    public List<OrderDto> findOrders(Principal principal) {
        UserInfo userInfo = userInfoRepository.findByUserId(principal.getName());
        List<OrderDto> ordersDto = orderRepository.findByUserInfo_userInfoCode(userInfo.getUserInfoCode())
                .stream().map(OrderDto::fromOrdersEntity).toList();
        List<OrderDto> orderList = new ArrayList<>();
        for (OrderDto orderDto : ordersDto){
            Long orderCode = orderDto.getOrderCode();
            List<OrderDetailDto> orderDetailList = orderDetailRepository.findByOrders_orderCode(orderCode)
                    .stream().map(OrderDetailDto::fromOrderDetailEntity).toList();
            orderDto.setOrderDetailList(orderDetailList);
            orderList.add(orderDto);
        }
        log.info(orderList.toString());
        return orderList;
    }

    public List<OrderDetailDto> findOrderDetail(List<OrderDto> orderList) {
        List<OrderDetailDto> orderDetailList = new ArrayList<>();
        for(OrderDto orderDto : orderList) {
            Long orderCode = orderDto.getOrderCode();
            List<OrderDetail> list = orderDetailRepository.findByOrders_orderCode(orderCode);
            for(OrderDetail detail : list) {
                orderDetailList.add(OrderDetailDto.fromOrderDetailEntity(detail));
            }
        }
        return orderDetailList;
    }
}
