package com.example.shoppingMall.service;

import com.example.shoppingMall.configuration.PrincipalDetails;
import com.example.shoppingMall.constant.UserRole;
import com.example.shoppingMall.dto.UserDto;
import com.example.shoppingMall.dto.UserInfoDto;
import com.example.shoppingMall.entity.UserInfo;
import com.example.shoppingMall.entity.Users;
import com.example.shoppingMall.repository.OrderRepository;
import com.example.shoppingMall.repository.UserInfoRepository;
import com.example.shoppingMall.repository.UserRepository;
import com.example.shoppingMall.repository.specification.UserSpecification;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
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
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EntityManager em;

    public void userInfoInsert(UserInfoDto userInfoDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String RRN = userInfoDto.getRRN();
        String year = RRN.substring(0, 2);
        String month = RRN.substring(2, 4);
        String day = RRN.substring(4, 6);
        String rrn = RRN.substring(7, 8);

        String birth = null;
        if(rrn.equals("1") || rrn.equals("2") || rrn.equals("5") || rrn.equals("6")) {
            birth = "19"+ year + "-" + month + "-" + day;
        } else {
            birth = "20"+ year + "-" + month + "-" + day;
        }
        userInfoDto.setBirthDate(birth);
        LocalDate now = LocalDate.now();
        userInfoDto.setCreatedDate(now);
        userInfoDto.setGrade("일반");
        userInfoDto.setIsActive("Y");
        UserInfo userInfo = UserInfoDto.toUserInfoEntity(userInfoDto);
        userInfoRepository.save(userInfo);
    }
    public void userInsert(UserInfoDto userInfoDto, String role) {
        UserDto userDto = new UserDto();
        userDto.setId(userInfoDto.getUser().getId());
        userDto.setPassword(userInfoDto.getUser().getPassword());
        if(role.equals("user")){
            userDto.setUserRole(UserRole.USER);
        } else if (role.equals("seller")){
            userDto.setUserRole(UserRole.TEMP);
        }
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

    public UserInfoDto findUserInfo(String userId){
        return UserInfoDto.fromUserInfoEntity(findByUserId(userId));
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

    public Page<UserInfoDto> userListAll(Pageable pageable) {
        return userInfoRepository.findAll(pageable).map(UserInfoDto::fromUserInfoEntity);

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


    public UserInfoDto findUserInfo(Long userInfoCode) {
        return userInfoRepository.findById(userInfoCode).map(UserInfoDto::fromUserInfoEntity).orElse(null);
    }

    public Boolean passwordCheck(String userId, String password) {
        Users user = userRepository.findById(userId).orElse(null);
        Boolean response = passwordEncoder.matches(password, user.getPassword());
        return response;
    }

    public void userInfoModify(UserInfoDto userInfoDto, String applySeller, String password) {
        UserInfo userInfo = userInfoRepository.findById(userInfoDto.getUserInfoCode()).orElse(null);
        userInfo.setPhoneNumber(userInfoDto.getPhoneNumber());
        userInfo.setEmail(userInfoDto.getEmail());
        if(applySeller.equals("apply")) {
            userInfo.getUser().setUserRole(UserRole.valueOf("TEMP"));
        } else if (applySeller.equals("cancel")){
            userInfo.getUser().setUserRole(UserRole.valueOf("USER"));
        }
        if(!password.isEmpty()) {
            userInfo.getUser().setPassword(passwordEncoder.encode(password));
        }
        userRepository.save(userInfo.getUser());
        userInfoRepository.save(userInfo);
    }

    public List<UserInfoDto> applyUserList() {
        return userInfoRepository.findAll().stream().map(UserInfoDto::fromUserInfoEntity)
                .filter(userInfoDto -> userInfoDto.getUser().getUserRole() == UserRole.TEMP).collect(Collectors.toList());
    }

    public List<UserInfoDto> filterUserInfo(String grade, String userRole, String isActive, String keyword){
        Specification<UserInfo> spec = Specification.where(null);
        if (grade != null && !grade.isEmpty()) {
            spec = spec.and(UserSpecification.hasGrade(grade));
        }
        if (userRole != null && !userRole.isEmpty()) {
            spec = spec.and(UserSpecification.hasUserRole(userRole));
        }
        if (isActive != null && !isActive.isEmpty()) {
            spec = spec.and(UserSpecification.hasIsActive(isActive));
        }
        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and(UserSpecification.hasSearchKeyword(keyword));
        }
        return userInfoRepository.findAll(spec).stream().map(UserInfoDto::fromUserInfoEntity).toList();

    }

    public void updateGrade() {
        List<Map<String, Object>> results = orderRepository.findAmountsByUser();
        log.info(results.toString());
        for(Map<String, Object> result : results) {
            Long userCode = ((Number) result.get("userCode")).longValue();
            int sumAmount = ((Number) result.get("sumAmount")).intValue();
            System.out.println(userCode);
            System.out.println(sumAmount);
            if(sumAmount > 200000) {
                UserInfo userInfo = userInfoRepository.findById(userCode).orElse(null);
                userInfo.setGrade("VIP");
                log.info(userInfo.toString());
                userInfoRepository.save(userInfo);
            }
        }
    }

    public void deleteUser(Long userInfoCode) {
        UserInfo findUser = userInfoRepository.findById(userInfoCode).orElse(null);
        if(findUser != null) {
            String deleteId = findUser.getUser().getId();
            findUser.setUser(null);
            userRepository.deleteById(deleteId);
            findUser.setIsActive("N");
            userInfoRepository.save(findUser);
        }
    }
}
