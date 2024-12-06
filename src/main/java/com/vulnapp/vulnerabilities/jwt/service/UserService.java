// UserService.java
package com.vulnapp.vulnerabilities.jwt.service;

import com.vulnapp.vulnerabilities.jwt.model.JwtUser;
import com.vulnapp.vulnerabilities.jwt.model.UserRole;  // 이 줄 추가
import com.vulnapp.vulnerabilities.jwt.repository.JwtUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;  // 이 줄 추가
import lombok.extern.slf4j.Slf4j;  // 추가

@Slf4j  // 추가
@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtUserRepository userRepository;
    private final JwtService jwtService;

    public String login(String username, String password) {
       try{
           log.info("Attempting login for username: {}", username);

           // 전체 사용자 조회 로그 추가
           log.info("All users in database:");
           userRepository.findAll().forEach(u ->
                   log.info("User: {}, Role: {}", u.getUsername(), u.getRole())
           );
        JwtUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 디버깅을 위한 로그 추가
        log.info("User found: {}", user.getUsername());
        log.info("User role: {}", user.getRole());

        String token = jwtService.generateToken(username, user.getRole().toString());
        log.info("Generated token: {}", token);

        return token;
    } catch (Exception e) {
            log.error("Login error for user {}: {}", username, e.getMessage()); // 에러 로그 추가
            throw e;
        }
    }

    public JwtUser getUserFromToken(String token) {
        Claims claims = jwtService.parseToken(token);
        String username = claims.get("username", String.class);
        String role = claims.get("role", String.class);  // 토큰에서 role 가져오기

        JwtUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 토큰의 role 값을 사용
        user.setRole(UserRole.valueOf(role));

        return user;
    }
}