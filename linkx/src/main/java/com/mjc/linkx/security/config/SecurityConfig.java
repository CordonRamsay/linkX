package com.mjc.linkx.security.config;


import com.mjc.linkx.security.dto.UserRole;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(logout ->
                        logout
                                .logoutUrl("/security-login/logout")  // 로그아웃 처리 경로
                                .logoutSuccessUrl("/security-login")  // 로그아웃 성공 시 이동 경로
                                .permitAll()
                )
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/boardFree/board_delete/**").hasAuthority(UserRole.ADMIN.name()) // 관리자만 접근 가능
                                .requestMatchers("/boardFree/board_update/**").hasAuthority(UserRole.ADMIN.name()) // 관리자만 접근 가능
                                .anyRequest().permitAll() // 그 외 요청은 모두 허용
                )
        ;

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 인코딩
    }
}
