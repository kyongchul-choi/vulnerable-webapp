package com.vulnapp.vulnerabilities.config.controller;

import com.vulnapp.vulnerabilities.config.dto.ConfigFileDTO;
import com.vulnapp.vulnerabilities.config.service.ConfigFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vulnerable/config")
@RequiredArgsConstructor
public class ConfigFileController {

    private final ConfigFileService configFileService;

    // 1. 디렉토리 탐색 취약점
    @GetMapping("/unsafe/file")
    @ResponseBody
    public ConfigFileDTO unsafeGetConfigByPath(@RequestParam String path) {
        return configFileService.unsafeGetConfigByPath(path);
    }

    // 2. 백업 파일 접근 취약점
    @GetMapping("/unsafe/backup/{date}")
    @ResponseBody
    public List<ConfigFileDTO> unsafeGetBackupFiles(@PathVariable String date) {
        return configFileService.unsafeGetBackupFiles(date);
    }

    // 3. 암호화 키 노출 취약점
    @GetMapping("/unsafe/encrypted/{fileName}")
    @ResponseBody
    public ConfigFileDTO unsafeGetEncryptedConfig(
            @PathVariable String fileName,
            @RequestParam String encKey) {
        return configFileService.unsafeGetEncryptedConfig(fileName, encKey);
    }

    // 4. 권한 우회 취약점
    @GetMapping("/unsafe/{fileName}")
    @ResponseBody
    public ConfigFileDTO unsafeGetConfig(
            @PathVariable String fileName,
            @RequestHeader(value = "X-User-Role", defaultValue = "USER") String role) {
        return configFileService.unsafeGetConfigWithHeader(fileName, role);
    }

    // 설정 파일 목록 페이지
    @GetMapping("/list")
    public String configList(Model model) {
        return "config/list";
    }
}