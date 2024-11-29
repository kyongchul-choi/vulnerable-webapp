// src/main/java/com/vulnapp/config/SecurityConfig.java
package com.vulnapp.config;

import com.vulnapp.config.filter.RedirectSecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Spring Security 설정 클래스
 * 보안 필터 체인 구성 및 전역 보안 정책 설정
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RedirectSecurityFilter redirectSecurityFilter;

    public SecurityConfig(RedirectSecurityFilter redirectSecurityFilter) {
        this.redirectSecurityFilter = redirectSecurityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화 (교육용)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()  // 모든 요청 허용 (교육용)
                );
                // 아래 필터 사용시, 바로위 ); 에서 ; 제거할것
                // 리다이렉트 보안 필터를 BasicAuthenticationFilter 이전에 추가
               // .addFilterBefore(redirectSecurityFilter, BasicAuthenticationFilter.class);

        // H2 콘솔 사용을 위한 설정 (교육용)
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}