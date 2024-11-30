// src/main/java/com/vulnapp/vulnerabilities/inputvalidation/entity/InputValidationUser.java
package com.vulnapp.vulnerabilities.inputvalidation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "input_validation_users")
@Getter @Setter
public class InputValidationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;  // 추가된 필드

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public enum UserRole {
        USER, ADMIN
    }
}