package com.vulnapp.vulnerabilities.config.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ConfigFileDTO {
    private Long id;
    private String fileName;
    private String content;
    private String filePath;
    private Boolean isSecret;
    private Boolean isEncrypted;
    private String backupDate;
}