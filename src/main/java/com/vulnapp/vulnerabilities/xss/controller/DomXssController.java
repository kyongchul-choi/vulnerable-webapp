package com.vulnapp.vulnerabilities.xss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/xss")
public class DomXssController {

    @GetMapping("/dom")
    public String domXssPage() {
        return "xss/dom"; // 취약한 DOM XSS 페이지
    }

    @GetMapping("/securedom")
    public String secureDomXssPage() {
        return "xss/secure-dom"; // 보완된 DOM XSS 페이지
    }
}
