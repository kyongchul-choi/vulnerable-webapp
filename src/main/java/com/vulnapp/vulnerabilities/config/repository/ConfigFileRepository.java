package com.vulnapp.vulnerabilities.config.repository;

import com.vulnapp.vulnerabilities.config.entity.ConfigFile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ConfigFileRepository extends JpaRepository<ConfigFile, Long> {
    Optional<ConfigFile> findByFileName(String fileName);
    List<ConfigFile> findByBackupDate(String backupDate);  // 이 메서드가 누락되었었네요
}