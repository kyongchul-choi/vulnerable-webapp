// OpenRedirectController.java 파일을 다음과 같이 수정
package com.vulnapp.vulnerabilities.openredirect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.vulnapp.vulnerabilities.openredirect.service.OpenRedirectService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/vulnerable/redirect")
@RequiredArgsConstructor
public class OpenRedirectController {

    private final OpenRedirectService openRedirectService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/unsafe-redirect")
    public String unsafeRedirect(@RequestParam("redirect_url") String redirectUrl) {
        return "redirect:" + openRedirectService.processUnsafeRedirect(redirectUrl);
    }

    @GetMapping("/safe-redirect")
    public String safeRedirect(@RequestParam("redirect_url") String redirectUrl) {
        return "redirect:" + openRedirectService.processSafeRedirect(redirectUrl);
    }
}