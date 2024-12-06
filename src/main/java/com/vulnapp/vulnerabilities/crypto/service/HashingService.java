// HashingService.java
package com.vulnapp.vulnerabilities.crypto.service;

import com.vulnapp.vulnerabilities.crypto.dto.HashPasswordDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;  // 추가
import java.util.Map;   // 추가

@Service
@Slf4j
public class HashingService {

    // 레인보우 테이블 시뮬레이션 (실제로는 매우 큰 테이블)
    private static final Map<String, String> RAINBOW_TABLE = new HashMap<>();

    static {
        // 미리 계산된 해시값들 (SHA-256)
        RAINBOW_TABLE.put("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "123456");
        RAINBOW_TABLE.put("ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f", "password123");
        RAINBOW_TABLE.put("481f6cc0511143ccdd7e2d1b1b94faf0a700a8b49cd13922a70b5ae28acaa8c5", "qwerty");
    }

    // 단순 해시 (취약한 방식)
    public String simpleHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("해시 생성 실패", e);
        }
    }

    // 솔트 생성
    public String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // 솔트 적용 해시 (안전한 방식)
    public String hashWithSalt(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(Base64.getDecoder().decode(salt));
            byte[] hash = digest.digest(password.getBytes());
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("솔트 해시 생성 실패", e);
        }
    }

    // 레인보우 테이블 조회
    public String checkRainbowTable(String hash) {
        return RAINBOW_TABLE.get(hash);
    }

    // 비밀번호 처리 및 분석
    public HashPasswordDTO processPassword(String password) {
        HashPasswordDTO dto = new HashPasswordDTO();
        dto.setOriginalPassword(password);

        // 단순 해시 생성
        String simpleHash = simpleHash(password);
        dto.setSimpleHash(simpleHash);

        // 솔트 적용 해시 생성
        String salt = generateSalt();
        dto.setSalt(salt);
        dto.setSaltedHash(hashWithSalt(password, salt));

        // 레인보우 테이블 취약성 검사
        String rainbowMatch = checkRainbowTable(simpleHash);
        dto.setRainbowTableVulnerable(rainbowMatch != null);
        dto.setCommonPasswordMatch(rainbowMatch);

        return dto;
    }

    // 바이트 배열을 16진수 문자열로 변환
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public Map<String, String> batchAnalyzeHashes(List<String> stolenHashes) {
        Map<String, String> crackedPasswords = new HashMap<>();
        for(String hash : stolenHashes) {
            String cracked = RAINBOW_TABLE.get(hash);
            if(cracked != null) {
                crackedPasswords.put(hash, cracked);
            }
        }
        return crackedPasswords;
    }
}