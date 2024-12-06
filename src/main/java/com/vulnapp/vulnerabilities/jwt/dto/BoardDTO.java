package com.vulnapp.vulnerabilities.jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardDTO {
    private Long id;
    private String title;
    private String content;
    private String writerName;
    private String createdAt;
}