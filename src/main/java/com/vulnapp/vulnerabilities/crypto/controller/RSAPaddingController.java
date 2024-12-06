// RSAPaddingController.java
package com.vulnapp.vulnerabilities.crypto.controller;

import com.vulnapp.vulnerabilities.crypto.dto.RSAMessageDTO;
import com.vulnapp.vulnerabilities.crypto.service.RSAPaddingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/vulnerable/crypto/rsa")
@RequiredArgsConstructor
public class RSAPaddingController {

    private final RSAPaddingService rsaPaddingService;

    @GetMapping("/padding")
    public String paddingPage() {
        return "crypto/rsa-padding";
    }

    @PostMapping("/process")
    @ResponseBody
    public RSAMessageDTO processMessage(@RequestParam String message) {
        log.info("Message received for processing: {}", message);
        return rsaPaddingService.processMessage(message);
    }

    @PostMapping("/decrypt-pkcs1")
    @ResponseBody
    public String decryptPKCS1(@RequestParam String encryptedMessage) {
        return rsaPaddingService.decryptPKCS1(encryptedMessage);
    }

    @PostMapping("/decrypt-oaep")
    @ResponseBody
    public String decryptOAEP(@RequestParam String encryptedMessage) {
        return rsaPaddingService.decryptOAEP(encryptedMessage);
    }
}