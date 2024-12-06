// CryptoTransactionDTO.java
package com.vulnapp.vulnerabilities.crypto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CryptoTransactionDTO {
    private String transactionType;
    private String amount;
    private String userName;
    private String originalData;
    private String encryptedDataECB;    // ECB 모드로 암호화된 데이터
    private String encryptedDataGCM;    // GCM 모드로 암호화된 데이터
}