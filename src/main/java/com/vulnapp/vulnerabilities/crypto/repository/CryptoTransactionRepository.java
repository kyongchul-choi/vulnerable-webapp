// CryptoTransactionRepository.java
package com.vulnapp.vulnerabilities.crypto.repository;

import com.vulnapp.vulnerabilities.crypto.entity.CryptoTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoTransactionRepository extends JpaRepository<CryptoTransaction, Long> {
    // 기본 CRUD 작업만 필요하므로 추가 메서드 없음
}