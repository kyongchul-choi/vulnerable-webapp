// src/main/java/com/vulnapp/vulnerabilities/xss/controller/XssController.java
package com.vulnapp.vulnerabilities.xss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class XssController {

    /**
     * XSS 공격 유형 페이지 목록
     * @return templates/xss/index.html 템플릿
     */
    @GetMapping("/vulnerable/xss")
    public String index() {
        return "xss/index"; // templates/xss/index.html 반환
    }
}
