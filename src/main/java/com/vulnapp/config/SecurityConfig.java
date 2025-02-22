// src/main/java/com/vulnapp/config/SecurityConfig.java
package com.vulnapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.servlet.http.HttpServletResponse;

// import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
// import com.vulnapp.filter.CustomHtmlEscapeFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


/**
 * Spring Security 설정 클래스
 * 보안 필터 체인 구성 및 전역 보안 정책 설정
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()  // 모든 요청 허용
                );

        // XSS 필터 추가
        // http.addFilterBefore(new CustomHtmlEscapeFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}