package com.vulnapp.vulnerabilities.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;  // 추가

@Slf4j  // 추가
@Service
public class JwtService {
    // 취약한 구현: 하드코딩된 시크릿 키
    private static final String SECRET_KEY = "12345678901234567890123456789012";
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username, String role) {
        log.info("Generating token for username: {} with role: {}", username, role);

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("role", role);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                //.signWith(key, SignatureAlgorithm.HS256)
                .compact();

        log.info("Generated token: {}", token);
        return token;
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .parseClaimsJwt(token)
                .getBody();
    }

}