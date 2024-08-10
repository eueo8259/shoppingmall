package com.example.shoppingMall.service;

import com.example.shoppingMall.configuration.PrincipalDetails;
import com.example.shoppingMall.entity.Users;
import com.example.shoppingMall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findById(username);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        Users userAccount = user.get();

        // 비밀번호 암호화
//        String password = passwordEncoder.encode(user.get().getPassword());
//        userAccount.setPassword(password);

        return new PrincipalDetails(userAccount);
    }

}
