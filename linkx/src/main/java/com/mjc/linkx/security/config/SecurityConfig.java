package com.mjc.linkx.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(
                        headerConfig ->
                                headerConfig
                                        .frameOptions(
                                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                        )
                )
                .formLogin(AbstractHttpConfigurer::disable) // spring security 기본 로그인 페이지 비활성화
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/board/board_view/**").authenticated() // 로그인한 유저만 접근 가능
                                .requestMatchers("/board/board_delete/**").hasRole("ADMIN") // 관리자만 접근 가능
                                .anyRequest().permitAll() // 그 외 요청은 모두 허용
                )
        ;

        return http.build();
    }
}
