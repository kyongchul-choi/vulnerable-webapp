package com.vulnapp.vulnerabilities.config.service;

import com.vulnapp.vulnerabilities.config.dto.ConfigFileDTO;
import com.vulnapp.vulnerabilities.config.entity.ConfigFile;
import com.vulnapp.vulnerabilities.config.repository.ConfigFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.io.ClassPathResource;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.ClassPathResource;

@Service
@RequiredArgsConstructor
public class ConfigFileService {

    private final ConfigFileRepository configFileRepository;

    // 1. 디렉토리 탐색 취약점
    // ConfigFileService.java의 unsafeGetConfigByPath 메소드 수정
    @Transactional(readOnly = true)
    public ConfigFileDTO unsafeGetConfigByPath(String path) {
        try {
            // 기본 디렉토리 설정 (프로젝트 루트)
            String baseDir = System.getProperty("user.dir") + "/src/main/resources/config/";
            // 경로 조합 (취약점: 상대 경로 ../를 허용)
            String fullPath = new File(baseDir + path).getCanonicalPath();

            System.out.println("Attempting to access: " + fullPath); // 디버깅용

            // secure.conf 직접 접근 차단
            if (path.endsWith("secure.conf")) {
                throw new RuntimeException("직접 접근할 수 없습니다.");
            }

            File file = new File(fullPath);
            if (file.exists() && file.canRead()) {
                String content = new String(Files.readAllBytes(file.toPath()));
                ConfigFileDTO dto = new ConfigFileDTO();
                dto.setFileName(file.getName());
                dto.setContent(content);
                dto.setFilePath(fullPath);
                return dto;
            } else {
                throw new RuntimeException("파일을 찾을 수 없습니다: " + fullPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("파일 접근 오류: " + e.getMessage());
        }
    }

    // 2. 백업 파일 접근 취약점
    @Transactional(readOnly = true)
    public List<ConfigFileDTO> unsafeGetBackupFiles(String date) {
        // 취약점: 날짜 패턴만으로 백업 파일 접근 가능
        return configFileRepository.findByBackupDate(date)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 3. 암호화 키 노출 취약점
    @Transactional(readOnly = true)
    public ConfigFileDTO unsafeGetEncryptedConfig(String fileName, String encKey) {
        ConfigFile configFile = configFileRepository.findByFileName(fileName)
                .orElseThrow(() -> new RuntimeException("파일을 찾을 수 없습니다."));

        // 취약점: 암호화 키를 URL 파라미터로 받아서 처리
        if (configFile.getIsEncrypted() && configFile.getEncryptionKey().equals(encKey)) {
            return convertToDTO(configFile);
        }
        throw new RuntimeException("잘못된 암호화 키입니다.");
    }

    // 4. 권한 우회 취약점
    @Transactional(readOnly = true)
    public ConfigFileDTO unsafeGetConfigWithHeader(String fileName, String role) {
        ConfigFile configFile = configFileRepository.findByFileName(fileName)
                .orElseThrow(() -> new RuntimeException("파일을 찾을 수 없습니다."));

        // 취약점: 헤더의 role 값을 그대로 신뢰
        if (configFile.getIsSecret() && !"ADMIN".equals(role)) {
            throw new RuntimeException("관리자 권한이 필요합니다.");
        }

        return convertToDTO(configFile);
    }

    private ConfigFileDTO convertToDTO(ConfigFile configFile) {
        ConfigFileDTO dto = new ConfigFileDTO();
        dto.setId(configFile.getId());
        dto.setFileName(configFile.getFileName());
        dto.setContent(configFile.getContent());
        dto.setFilePath(configFile.getFilePath());
        dto.setIsSecret(configFile.getIsSecret());
        dto.setIsEncrypted(configFile.getIsEncrypted());
        dto.setBackupDate(configFile.getBackupDate());
        return dto;
    }
}