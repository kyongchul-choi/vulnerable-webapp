// src/main/java/com/vulnapp/vulnerabilities/inputvalidation/dto/LoginRequest.java
package com.vulnapp.vulnerabilities.inputvalidation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
    private String username;
    private String password;
}