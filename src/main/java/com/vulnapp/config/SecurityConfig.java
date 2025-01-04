// src/main/java/com/vulnapp/config/SecurityConfig.java
package com.vulnapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import com.vulnapp.config.filter.FileAccessSecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
// import com.vulnapp.filter.CustomHtmlEscapeFilter;

/**
 * Spring Security 설정 클래스
 * 보안 필터 체인 구성 및 전역 보안 정책 설정
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
   @Autowired  // 필터가 Spring bean 으로 등록되어 있기에
   private FileAccessSecurityFilter fileAccessSecurityFilter;   // 필터 클래스 선언


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // csrf 토근생성
                /*
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )

                 */
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()  // 모든 요청 허용

                )
                // xss필터 .addFilterBefore(new CustomHtmlEscapeFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(fileAccessSecurityFilter, BasicAuthenticationFilter.class);

        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}