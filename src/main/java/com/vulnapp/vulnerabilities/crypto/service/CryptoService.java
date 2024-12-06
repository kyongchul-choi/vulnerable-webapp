package com.vulnapp.vulnerabilities.crypto.service;

import com.vulnapp.vulnerabilities.crypto.dto.CryptoTransactionDTO;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import jakarta.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoService {
    private SecretKey secretKey;
    private static final int GCM_NONCE_LENGTH = 12;  // 96 bits (recommended for GCM)
    private static final int GCM_TAG_LENGTH = 128;   // bits

    @PostConstruct
    public void init() {
        try {
            // AES-256 키 생성 (NIST 권장)
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256, SecureRandom.getInstanceStrong());
            this.secretKey = keyGen.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("보안 키 생성 실패", e);
        }
    }

    // ECB 모드 암호화 (취약점 시연용 - 실제 사용 금지)
    public String encryptECB(String plaintext) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error("ECB 암호화 실패", e);
            throw new RuntimeException("ECB 암호화 실패: " + e.getMessage());
        }
    }

    // GCM 모드 암호화 (권장되는 안전한 방식)
    public String encryptGCM(String plaintext) {
        try {
            // 랜덤 nonce 생성 (매 암호화마다 새로 생성)
            byte[] nonce = new byte[GCM_NONCE_LENGTH];
            SecureRandom.getInstanceStrong().nextBytes(nonce);

            // GCM 스펙 생성
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, nonce);

            // 암호화 수행
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);

            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

            // nonce와 암호문 결합
            byte[] combined = new byte[nonce.length + encryptedBytes.length];
            System.arraycopy(nonce, 0, combined, 0, nonce.length);
            System.arraycopy(encryptedBytes, 0, combined, nonce.length, encryptedBytes.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            log.error("GCM 암호화 실패", e);
            throw new RuntimeException("GCM 암호화 실패: " + e.getMessage());
        }
    }

    // 거래 데이터 생성 (ECB와 GCM 비교용)
    public CryptoTransactionDTO createTransaction(String type, String amount, String userName) {
        String plaintext = String.format("%s|%s|%s", type, amount, userName);

        CryptoTransactionDTO dto = new CryptoTransactionDTO();
        dto.setTransactionType(type);
        dto.setAmount(amount);
        dto.setUserName(userName);
        dto.setOriginalData(plaintext);
        dto.setEncryptedDataECB(encryptECB(plaintext));
        dto.setEncryptedDataGCM(encryptGCM(plaintext));

        return dto;
    }
}