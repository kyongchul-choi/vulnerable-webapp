// src/main/java/com/vulnapp/config/SecurityConfig.java
package com.vulnapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
// import com.vulnapp.filter.CustomHtmlEscapeFilter;

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
        // xss필터 .addFilterBefore(new CustomHtmlEscapeFilter(), UsernamePasswordAuthenticationFilter.class)
                http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}