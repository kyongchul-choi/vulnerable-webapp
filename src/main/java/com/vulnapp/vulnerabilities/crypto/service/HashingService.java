package com.vulnapp.vulnerabilities.crypto.service;

import com.vulnapp.vulnerabilities.crypto.dto.HashPasswordDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class HashingService {

    // 레인보우 테이블 시뮬레이션
    private static final Map<String, String> RAINBOW_TABLE = new HashMap<>();

    static {
        RAINBOW_TABLE.put("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "123456");
        RAINBOW_TABLE.put("ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f", "password123");
        RAINBOW_TABLE.put("481f6cc0511143ccdd7e2d1b1b94faf0a700a8b49cd13922a70b5ae28acaa8c5", "qwerty");
    }

    public String simpleHash(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("비밀번호를 입력해야 합니다.");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("해시 생성 실패", e);
        }
    }

    public String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public String hashWithSalt(String password, String salt) {
        if (password == null || password.isEmpty() || salt == null || salt.isEmpty()) {
            throw new IllegalArgumentException("비밀번호 및 솔트를 입력해야 합니다.");
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(Base64.getDecoder().decode(salt));
            byte[] hash = digest.digest(password.getBytes());
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("솔트 해시 생성 실패", e);
        }
    }

    public String checkRainbowTable(String hash) {
        return RAINBOW_TABLE.get(hash);
    }

    public HashPasswordDTO processPassword(String password) {
        HashPasswordDTO dto = new HashPasswordDTO();
        dto.setOriginalPassword(password);

        String simpleHash = simpleHash(password);
        dto.setSimpleHash(simpleHash);

        String salt = generateSalt();
        dto.setSalt(salt);
        dto.setSaltedHash(hashWithSalt(password, salt));

        String rainbowMatch = checkRainbowTable(simpleHash);
        dto.setRainbowTableVulnerable(rainbowMatch != null);
        dto.setCommonPasswordMatch(rainbowMatch);

        return dto;
    }

    public Map<String, String> batchAnalyzeHashes(List<String> hashes) {
        Map<String, String> crackedPasswords = new HashMap<>();
        for (String hash : hashes) {
            String cracked = RAINBOW_TABLE.get(hash);
            if (cracked != null) {
                crackedPasswords.put(hash, cracked);
            }
        }
        return crackedPasswords;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
