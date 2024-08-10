package com.example.shoppingMall.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((request)->request
                        .requestMatchers("/css/**","/js/**", "/images/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/user/**").permitAll()
                )

                .formLogin((form)->form
                        .loginPage("/login") // 로그인 페이지 URL
                        .loginProcessingUrl("/login") // 로그인 처리 URL
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )

                .logout((out)->out
                        .logoutSuccessUrl("/") // 로그아웃 성공 후 이동할 URL
                        .logoutUrl("/logout")
                        .permitAll() // 로그아웃 페이지는 모든 사용자 접근 허용
                )

                .csrf(csrf -> csrf.disable()); // CSRF 보호 비활성화

        return http.build();
    }


}
