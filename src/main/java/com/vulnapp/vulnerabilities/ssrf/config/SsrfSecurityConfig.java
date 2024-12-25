package com.vulnapp.vulnerabilities.ssrf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SsrfSecurityConfig {

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/vulnerable/ssrf/hr").denyAll()  // 내부 리소스 직접 접근 차단
                        .requestMatchers("/vulnerable/ssrf/").permitAll()   // SSRF 접근 허용
                        .anyRequest().authenticated()
                )
                .csrf().disable();  // CSRF 보호 비활성화 (테스트 용도)
    }
}
