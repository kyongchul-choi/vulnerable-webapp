package com.vulnapp.vulnerabilities.jwt.controller;

import com.vulnapp.vulnerabilities.jwt.dto.UserDTO;
import com.vulnapp.vulnerabilities.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vulnerable/jwt")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 로그인 페이지를 위한 GET 요청 처리 추가
    @GetMapping("/login")
    public String loginPage() {
        return "jwt/login";
    }

    // 기존 로그인 API 경로 수정
    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) {
        String token = userService.login(userDTO.getUsername(), userDTO.getPassword());
        userDTO.setToken(token);
        return ResponseEntity.ok(userDTO);
    }
}