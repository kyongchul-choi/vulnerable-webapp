// src/main/java/com/vulnapp/vulnerabilities/inputvalidation/repository/BoardRepository.java
package com.vulnapp.vulnerabilities.inputvalidation.repository;

import com.vulnapp.vulnerabilities.inputvalidation.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InputValidationBoardRepository extends JpaRepository<Board, Long> {
}