// UserRepository.java
package com.vulnapp.vulnerabilities.jwt.repository;

import com.vulnapp.vulnerabilities.jwt.model.JwtUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JwtUserRepository extends JpaRepository<JwtUser, Long> {
    Optional<JwtUser> findByUsername(String username);
}
