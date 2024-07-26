package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    @Query(value = "SELECT * FROM user_info WHERE id = :id", nativeQuery = true)
    UserInfo findByUserId(@Param("id") String userId);


}
