package com.example.shoppingMall.service;

import com.example.shoppingMall.api.CashedExRateProvider;
import com.example.shoppingMall.dto.OrderDetailDto;
import com.example.shoppingMall.dto.OrderDto;
import com.example.shoppingMall.dto.ProductDto;
import com.example.shoppingMall.entity.*;
import com.example.shoppingMall.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    CashedExRateProvider exRateProvider;

    public void insertOrder(OrderDto orderDto, List<Map<String, Object>> orderDetailList, Long[] cartCodeList, Long couponCode) {
        log.info(orderDto.getUserInfo().getUserInfoCode().toString());
        Long userInfoCode = orderDto.getUserInfo().getUserInfoCode();

        UserInfo userInfo = userInfoRepository.findById(orderDto.getUserInfo().getUserInfoCode()).orElse(null);
        userInfo.setCurrentPoint(userInfo.getCurrentPoint() - orderDto.getPaymentPrice());
        userInfoRepository.save(userInfo);

        Orders orders = new Orders();
        orders.setUserInfo(userInfo);
        orders.setOrderDate(LocalDateTime.now());
        orders.setPaymentPrice(orderDto.getPaymentPrice());
        orders.setDiscountAmount(orderDto.getDiscountAmount());
        orders.setDelivery(new Delivery());
        orders.getDelivery().setDeliveryCode(orderDto.getDelivery().getDeliveryCode());
        orderRepository.save(orders);

        Long orderCode = orderRepository.findCode(orders.getUserInfo().getUserInfoCode());
        log.info(String.valueOf(orderCode));
        for (Map<String, Object> detail : orderDetailList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrders(orders);
            orderDetail.setProduct(new Product());
            orderDetail.getOrders().setOrderCode(orderCode);
            orderDetail.getProduct().setProductCode(Long.valueOf(String.valueOf(detail.get("productCode"))));
            orderDetail.setOrderQuantity(Integer.parseInt(String.valueOf(detail.get("orderQuantity"))));
            orderDetail.setOrderPrice(Integer.parseInt(String.valueOf(detail.get("orderPrice"))));

            // Log each order detail for debugging
            log.info(orderDetail.toString());

            // Save each OrderDetail object
            orderDetailRepository.save(orderDetail);
        }

        UserPoint userPoint = new UserPoint();
        userPoint.setUserInfo(userInfo);
        userPoint.setUsePoint(orderDto.getPaymentPrice());
        userPoint.setRemarks("주문번호: " + orderCode);
        userPointRepository.save(userPoint);

        if (cartCodeList != null) {
            for(int i = 0; i < cartCodeList.length; i++){
                cartRepository.deleteById(cartCodeList[i]);
            }
        }
        if(couponCode != null) {
            userCouponRepository.deleteById(couponCode);
        }
        log.info(couponCode.toString());
    }

    public Page<OrderDto> findOrders(Principal principal, Pageable pageable) {
        UserInfo userInfo = userInfoRepository.findByUserId(principal.getName());
        Page<Orders> orders = orderRepository.findByUserInfo_userInfoCode(userInfo.getUserInfoCode(), pageable);
        List<OrderDto> orderDtos = orders.stream()
                .map(OrderDto::fromOrdersEntity)
                .toList();
        for (OrderDto orderDto : orderDtos){
            Long orderCode = orderDto.getOrderCode();
            List<OrderDetailDto> orderDetailList = orderDetailRepository.findByOrders_orderCode(orderCode)
                    .stream().map(OrderDetailDto::fromOrderDetailEntity).toList();
            orderDto.setOrderDetailList(orderDetailList);
        }
        Page<OrderDto> orderList = new PageImpl<>(orderDtos, pageable, orders.getTotalElements());
        return orderList;
    }

    public List<OrderDetailDto> findOrderDetail(Page<OrderDto> orderList) {
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

    public OrderDetailDto newOrderDetail(Long orderItem, int quantity) {
        Product product = productService.findOrderItem(orderItem);
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        BigDecimal priceInCurrency = exRateProvider.getCachedExRate(product.getCurrency())
                .multiply(product.getProductPrice());
        BigDecimal roundedPrice = priceInCurrency.setScale(0, RoundingMode.HALF_UP);
        product.setProductPrice(roundedPrice);
        orderDetailDto.setProduct(product);
        orderDetailDto.setOrderQuantity(quantity);
        return orderDetailDto;
    }
}
