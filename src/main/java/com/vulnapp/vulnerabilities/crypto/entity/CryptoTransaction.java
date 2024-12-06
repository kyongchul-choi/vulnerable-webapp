// CryptoTransaction.java
package com.vulnapp.vulnerabilities.crypto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "crypto_transactions")
@Getter @Setter
public class CryptoTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionType;
    private String amount;
    private String userName;
    private String encryptedData;
}
