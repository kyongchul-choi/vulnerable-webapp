package com.vulnapp.vulnerabilities.ssrf.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InternalController {

    @GetMapping("/vulnerable/ssrf/hr")
    public String hrPage(HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        if (referrer == null || !referrer.contains("/vulnerable/ssrf")) {
            throw new RuntimeException("직접 접근이 허용되지 않습니다.");
        }
        return "ssrf/hr"; // HR 페이지 템플릿 렌더링
    }
}
