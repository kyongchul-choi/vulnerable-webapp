// src/main/java/com/vulnapp/vulnerabilities/inputvalidation/dto/BoardRequest.java
package com.vulnapp.vulnerabilities.inputvalidation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardRequest {
    private String title;
    private String content;
    private Long userId;    // 취약점: 사용자 ID를 파라미터로 받음
    private String role;    // 취약점: 권한을 파라미터로 받음
}