package com.vulnapp.vulnerabilities.inputvalidation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardDTO {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String username;
}