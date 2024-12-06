// RSAMessageDTO.java
package com.vulnapp.vulnerabilities.crypto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RSAMessageDTO {
    private String originalMessage;              // 원본 메시지
    private String encryptedPKCS1;              // PKCS1v1.5로 암호화된 메시지
    private String encryptedOAEP;               // OAEP로 암호화된 메시지
    private String pkcs1DecryptionError;        // PKCS1v1.5 복호화 에러 메시지
    private String oaepDecryptionError;         // OAEP 복호화 에러 메시지
    private long pkcs1ProcessingTime;           // PKCS1v1.5 처리 시간
    private long oaepProcessingTime;            // OAEP 처리 시간
}