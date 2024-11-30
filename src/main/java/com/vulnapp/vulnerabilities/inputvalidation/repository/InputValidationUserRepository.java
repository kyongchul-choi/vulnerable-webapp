package com.vulnapp.vulnerabilities.inputvalidation.repository;

import com.vulnapp.vulnerabilities.inputvalidation.entity.InputValidationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InputValidationUserRepository extends JpaRepository<InputValidationUser, Long> {
    Optional<InputValidationUser> findByUsername(String username);
}