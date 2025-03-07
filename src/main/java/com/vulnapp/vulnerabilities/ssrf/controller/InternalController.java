package com.vulnapp.vulnerabilities.ssrf.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


@Controller
public class InternalController {

    @GetMapping("/vulnerable/ssrf/hr")
    public String hrPage(HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        if (referrer == null || !referrer.contains("/vulnerable/ssrf")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한 없는 파일 접근 시도 차단됨");

        }
        return "ssrf/hr"; // HR 페이지 템플릿 렌더링
    }
}
