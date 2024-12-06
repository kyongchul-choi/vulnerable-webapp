// RSAPaddingService.java
package com.vulnapp.vulnerabilities.crypto.service;

import com.vulnapp.vulnerabilities.crypto.dto.RSAMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;
import javax.crypto.BadPaddingException;  // 추가
import javax.crypto.IllegalBlockSizeException;  // 추가
import javax.crypto.NoSuchPaddingException;  // 추가

@Service
@Slf4j
public class RSAPaddingService {
    private KeyPair keyPair;
    private static final int KEY_SIZE = 2048;

    public RSAPaddingService() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(KEY_SIZE);
            this.keyPair = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("RSA 키 생성 실패", e);
        }
    }

    // PKCS1v1.5 패딩으로 암호화 (취약한 방식)
    public String encryptPKCS1(String message) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            byte[] encrypted = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("PKCS1 암호화 실패", e);
            throw new RuntimeException("PKCS1 암호화 실패");
        }
    }

    // PKCS1v1.5 패딩으로 복호화 (취약한 방식 - 상세 에러 메시지 반환)
    public String decryptPKCS1(String encryptedMessage) {
        try {
            byte[] encrypted = Base64.getDecoder().decode(encryptedMessage);

            // RSA 블록 구조 확인 (첫 바이트 체크)
            if (encrypted[0] != 0x00) {
                return "패딩 오류: 첫 바이트가 0x00이 아닙니다.";
            }

            // PKCS1 타입 체크 (두 번째 바이트)
            if (encrypted[1] != 0x02) {
                return "패딩 오류: 두 번째 바이트가 0x02가 아닙니다.";
            }

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());

            try {
                byte[] decrypted = cipher.doFinal(encrypted);
                return new String(decrypted);
            } catch (BadPaddingException e) {
                return "패딩 오류: 패딩 바이트가 올바르지 않습니다 (0x00 구분자 위치 오류)";
            }

        } catch (IllegalBlockSizeException e) {
            return "블록 크기 오류: RSA 블록 길이가 올바르지 않습니다.";
        } catch (Exception e) {
            return "기타 오류: " + e.getMessage();
        }
    }

    // OAEP 패딩으로 암호화 (안전한 방식)
    public String encryptOAEP(String message) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            byte[] encrypted = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("OAEP 암호화 실패", e);
            throw new RuntimeException("암호화 실패");
        }
    }

    // OAEP 패딩으로 복호화 (안전한 방식 - 일관된 에러 메시지)
    public String decryptOAEP(String encryptedMessage) {
        try {
            byte[] encrypted = Base64.getDecoder().decode(encryptedMessage);
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted);
        } catch (Exception e) {
            // 안전: 일관된 에러 메시지 반환
            return "복호화 실패";
        }
    }

    // 메시지 처리 및 비교
    public RSAMessageDTO processMessage(String message) {
        RSAMessageDTO dto = new RSAMessageDTO();
        dto.setOriginalMessage(message);

        // PKCS1v1.5 처리
        long startTime = System.nanoTime();
        String pkcs1Encrypted = encryptPKCS1(message);
        dto.setPkcs1ProcessingTime(System.nanoTime() - startTime);
        dto.setEncryptedPKCS1(pkcs1Encrypted);
        dto.setPkcs1DecryptionError(decryptPKCS1(pkcs1Encrypted));

        // OAEP 처리
        startTime = System.nanoTime();
        String oaepEncrypted = encryptOAEP(message);
        dto.setOaepProcessingTime(System.nanoTime() - startTime);
        dto.setEncryptedOAEP(oaepEncrypted);
        dto.setOaepDecryptionError(decryptOAEP(oaepEncrypted));

        return dto;
    }
}