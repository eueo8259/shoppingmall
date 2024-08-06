package com.example.shoppingMall.dto;
import com.example.shoppingMall.entity.Delivery;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.entity.UserPoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {
    private Long deliveryCode;
    private UserInfo userInfo;
    private String postalCode;
    private String address;
    private String contactNumber;
    private String contactName;
    private String defaultYn;

    public static DeliveryDto fromDeliveryEntity(Delivery delivery) {
        return new DeliveryDto(
                delivery.getDeliveryCode(),
                delivery.getUserInfo(),
                delivery.getPostalCode(),
                delivery.getAddress(),
                delivery.getContactNumber(),
                delivery.getContactName(),
                delivery.getDefaultYn()
        );
    }
    public static Delivery toDeliveryEntity(DeliveryDto dto) {
        Delivery delivery = new Delivery();
        delivery.setDeliveryCode(dto.getDeliveryCode());
        delivery.setUserInfo(dto.getUserInfo());
        delivery.setPostalCode(dto.getPostalCode());
        delivery.setAddress(dto.getAddress());
        delivery.setContactNumber(dto.getContactNumber());
        delivery.setContactName(dto.getContactName());
        delivery.setDefaultYn(dto.getDefaultYn());
        return delivery;
    }
}

