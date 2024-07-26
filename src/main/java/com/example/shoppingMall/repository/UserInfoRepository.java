package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    @Query(value = "SELECT * FROM user_info where id = :userId", nativeQuery = true)
    UserInfo findByUserId(@Param("userId") String id);
}
