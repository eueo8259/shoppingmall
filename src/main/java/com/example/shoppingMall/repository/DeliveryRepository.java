package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByUserInfo_userInfoCode(@Param("userInfoCode") Long userInfoCode);

    Delivery findByUserInfo_userInfoCodeAndDefaultYn(@Param("userInfoCode") Long userInfoCode,
                                                             @Param("defaultYn") String defaultYn);

    @Query(value = "SELECT MAX(delivery_code) FROM delivery where user_info_code = :userInfoCode", nativeQuery = true)
    Long findDeliveryCode(@Param("userInfoCode") Long userInfoCode);
}
