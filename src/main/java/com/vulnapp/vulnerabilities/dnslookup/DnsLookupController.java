package com.vulnapp.vulnerabilities.dnslookup;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DnsLookupController {

    /**
     * DNS Lookup에 의존한 보안 결정 취약점 페이지 렌더링
     * @return templates/dnslookup/index.html
     */
    @GetMapping("/vulnerable/dnslookup")
    public String showDnsLookupPage() {
        return "dnslookup/index"; // dnslookup 디렉토리의 index.html을 렌더링
    }
}