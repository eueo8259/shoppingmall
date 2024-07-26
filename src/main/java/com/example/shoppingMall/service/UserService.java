package com.example.shoppingMall.service;

import com.example.shoppingMall.configuration.PrincipalDetails;
import com.example.shoppingMall.dto.UserDto;
import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.entity.Users;
import com.example.shoppingMall.repository.UserInfoRepository;
import com.example.shoppingMall.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EntityManager em;

    public void userInfoInsert(UserInfoDto userInfoDto) {
        LocalDate now = LocalDate.now();
        userInfoDto.setCreatedDate(now);
        userInfoDto.setGrade("일반");
        userInfoDto.setIsActive("Y");
        log.info(now.toString());
        log.info(userInfoDto.toString());
        UserInfo userInfo = UserInfoDto.toUserInfoEntity(userInfoDto);
        userInfoRepository.save(userInfo);
    }

    public void userInsert(UserDto userDto) {
        String password = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(password);
        Users user = UserDto.toUserEntity(userDto);
        userRepository.save(user);
    }

    public boolean isIdDuplicate(String id) {
        return userRepository.findById(id).isPresent();
    }

    public UserDto getOneUser(String id) {
        Users user = em.find(Users.class, id);
        return UserDto.fromUserEntity(user);
    }

    public UserInfoDto loginUserInfoDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();

            // 여기서 userDetails에서 사용자 정보 추출
            Users user = userDetails.getUsers();

            UserInfo userInfo = userInfoRepository.findByUserId(user.getId());
            UserInfoDto userInfoDto = UserInfoDto.fromUserInfoEntity(userInfo);

            System.out.println("========================" + userInfoDto.getUserInfoCode());
            return userInfoDto;
        }
        return null;
    }

}
