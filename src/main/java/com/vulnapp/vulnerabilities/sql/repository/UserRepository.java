// src/main/java/com/vulnapp/vulnerabilities/sql/repository/UserRepository.java
package com.vulnapp.vulnerabilities.sql.repository;

import com.vulnapp.vulnerabilities.sql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);
}
