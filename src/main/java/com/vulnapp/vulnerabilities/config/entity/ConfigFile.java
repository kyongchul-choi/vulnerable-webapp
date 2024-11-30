package com.vulnapp.vulnerabilities.config.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "config_files")
@Getter @Setter
public class ConfigFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String filePath;

    private Boolean isSecret;

    private Boolean isEncrypted;    // 암호화된 파일 여부

    private String encryptionKey;   // 암호화 키 (실제로는 이렇게 저장하면 안됨)

    private String backupDate;      // 백업 파일 날짜
}