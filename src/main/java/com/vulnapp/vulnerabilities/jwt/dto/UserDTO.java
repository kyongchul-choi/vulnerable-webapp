// UserDTO.java
package com.vulnapp.vulnerabilities.jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
    private String username;
    private String password;
    private String token;
}