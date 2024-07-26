package com.example.shoppingMall.service;

import com.example.shoppingMall.dto.UserDto;
import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.entity.Users;
import com.example.shoppingMall.repository.UserInfoRepository;
import com.example.shoppingMall.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

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
}
