// BoardRepository.java
package com.vulnapp.vulnerabilities.jwt.repository;

import com.vulnapp.vulnerabilities.jwt.model.JwtBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtBoardRepository extends JpaRepository<JwtBoard, Long> {
}
