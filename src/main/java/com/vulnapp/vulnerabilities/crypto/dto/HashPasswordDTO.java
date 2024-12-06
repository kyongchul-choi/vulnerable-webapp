// HashPasswordDTO.java
package com.vulnapp.vulnerabilities.crypto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HashPasswordDTO {
    private String originalPassword;      // 원본 비밀번호
    private String simpleHash;           // 단순 해시 (취약)
    private String saltedHash;           // 솔트 적용 해시 (안전)
    private String salt;                 // 사용된 솔트값
    private boolean isRainbowTableVulnerable;  // 레인보우 테이블 취약 여부
    private String commonPasswordMatch;   // 레인보우 테이블에서 찾은 매칭값
}