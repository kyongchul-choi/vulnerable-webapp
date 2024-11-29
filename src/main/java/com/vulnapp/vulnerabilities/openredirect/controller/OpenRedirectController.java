package com.vulnapp.vulnerabilities.openredirect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/vulnerable/redirect")
public class OpenRedirectController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // login.html 페이지로 이동
    }

    @GetMapping("/unsafe-redirect")
    public String unsafeRedirect(@RequestParam("redirect_url") String redirectUrl) {
        // 취약한 리다이렉트 - URL 검증 없이 리다이렉트 수행
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/safe-redirect")
    public String safeRedirect(@RequestParam("redirect_url") String redirectUrl) {
        // 안전한 리다이렉트 - 허용된 도메인으로만 리다이렉트
        if (redirectUrl.startsWith("/") || redirectUrl.startsWith("http://localhost")) {
            return "redirect:" + redirectUrl;
        }
        return "redirect:/error";
    }
}