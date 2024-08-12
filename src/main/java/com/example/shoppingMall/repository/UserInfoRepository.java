package com.example.shoppingMall.repository;

import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {
    @Query(value = "SELECT * FROM user_info where id = :userId", nativeQuery = true)
    UserInfo findByUserId(@Param("userId") String id);

    UserInfo findByUserNameAndRRNAndPhoneNumber(@Param("userName") String userName,
                                                @Param("RRN") String rrn,
                                                @Param("phoneNumber") String phoneNumber);

    UserInfo findByUser_IdAndPhoneNumberAndEmail(@Param("id") String id,
                                            @Param("phoneNumber") String phoneNumber,
                                            @Param("email") String email);
}
