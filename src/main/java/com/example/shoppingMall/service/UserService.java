package com.example.shoppingMall.service;

import com.example.shoppingMall.constant.UserRole;
import com.example.shoppingMall.dto.UserDto;
import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.entity.Users;
import com.example.shoppingMall.repository.UserInfoRepository;
import com.example.shoppingMall.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String RRN = userInfoDto.getRRN();
        String year = RRN.substring(0, 2);
        String month = RRN.substring(2, 4);
        String day = RRN.substring(4, 6);
        String rrn = RRN.substring(7, 8);
        log.info(year.toString());
        log.info(month.toString());
        log.info(day.toString());
        log.info(rrn.toString());

        String birth = null;
        if(rrn.equals("1") || rrn.equals("2") || rrn.equals("5") || rrn.equals("6")) {
            birth = "19"+ year + "-" + month + "-" + day;
        } else {
            birth = "20"+ year + "-" + month + "-" + day;
        }
        log.info(birth.toString());
        LocalDate birthDate = LocalDate.parse(birth, formatter);
        log.info(birthDate.toString());
        userInfoDto.setBirthDate(birthDate);
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

    public UserInfo findByUserId(String userId) {
        UserInfo userInfo = userInfoRepository.findByUserId(userId);
        return userInfo;
    }

    public String findUserId(String userName, String rrn, String phoneNumber) {
        UserInfo userInfo = userInfoRepository.findByUserNameAndRRNAndPhoneNumber(userName, rrn, phoneNumber);
        String userId = userInfo.getUser().getId();
        return userId;
    }

    public String findUser(String id, String phoneNumber, String email) {
        UserInfo userInfo = userInfoRepository.findByUser_IdAndPhoneNumberAndEmail(id, phoneNumber, email);
        String userId = userInfo.getUser().getId();
        return userId;
    }

    public String updatePw(String id, String updatePw) {
        Optional<Users> findUser = userRepository.findById(id);
        Users user = findUser.get();
        user.setPassword(passwordEncoder.encode(updatePw));
        userRepository.save(user);
        return "OK";
    }

    public List<UserInfoDto> userListAll() {
        return userInfoRepository.findAll().stream().map(x -> UserInfoDto.fromUserInfoEntity(x))
                .filter(userInfoDto -> userInfoDto.getUser().getUserRole() != UserRole.ADMIN)
                .collect(Collectors.toList());
    }

    public String findUserRole(String username) {
        Optional<Users> findUser = userRepository.findById(username);
        String userRole = findUser.get().getUserRole().getValue();
        return userRole;
    }

    public void updateUserRole(String userId, String userRole) {
        Optional<Users> findUser = userRepository.findById(userId);
        UserRole role = UserRole.valueOf(userRole);
        findUser.get().setUserRole(role);
        Users users = findUser.get();
        userRepository.save(users);
    }

    public void updateIsActive(String userId, String isActive) {
        UserInfo findUserInfo = userInfoRepository.findByUserId(userId);
        findUserInfo.setIsActive(isActive);
        userInfoRepository.save(findUserInfo);
    }
}
