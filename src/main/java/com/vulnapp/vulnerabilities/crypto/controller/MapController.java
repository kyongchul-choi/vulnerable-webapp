package com.vulnapp.vulnerabilities.crypto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vulnerable/crypto")
public class MapController {

    @GetMapping
    public String cryptoHome() {
        return "crypto/index";
    }
}
