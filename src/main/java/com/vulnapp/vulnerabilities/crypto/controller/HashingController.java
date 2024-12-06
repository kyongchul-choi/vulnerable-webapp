// HashingController.java
package com.vulnapp.vulnerabilities.crypto.controller;

import com.vulnapp.vulnerabilities.crypto.dto.HashPasswordDTO;
import com.vulnapp.vulnerabilities.crypto.service.HashingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@Controller
@RequestMapping("/vulnerable/crypto/hash")
@RequiredArgsConstructor
public class HashingController {

    private final HashingService hashingService;

    @GetMapping("/password")
    public String passwordHashPage() {
        return "crypto/password-hash";
    }

    @PostMapping("/process")
    @ResponseBody
    public HashPasswordDTO processPassword(@RequestParam String password) {
        log.info("Password received for processing");
        return hashingService.processPassword(password);
    }

    @PostMapping("/crack")
    @ResponseBody
    public Map<String, Object> crackPasswords(@RequestBody List<String> hashes) {
        Map<String, Object> result = new HashMap<>();
        result.put("totalHashes", hashes.size());
        Map<String, String> crackedPasswords = hashingService.batchAnalyzeHashes(hashes);
        result.put("crackedPasswords", crackedPasswords);
        result.put("successRate",
                String.format("%.2f%%",
                        (crackedPasswords.size() * 100.0) / hashes.size()));
        return result;
    }


}