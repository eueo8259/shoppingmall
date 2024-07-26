package com.example.shoppingMall.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {
    private Long deliveryCode;
    private Long userInfoCode;
    private int postalCode;
    private String address;
    private String contactNumber;
}