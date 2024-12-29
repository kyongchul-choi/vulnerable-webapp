package com.vulnapp.vulnerabilities.bruteforce;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BruteForceController {

    /**
     * 반복된 인증 시도 제한 기능 부재 취약점 페이지 렌더링
     * @return templates/bruteforce/index.html
     */
    @GetMapping("/vulnerable/bruteforce")
    public String showBruteForcePage() {
        return "bruteforce/index"; // bruteforce 디렉토리의 index.html을 렌더링
    }
}
