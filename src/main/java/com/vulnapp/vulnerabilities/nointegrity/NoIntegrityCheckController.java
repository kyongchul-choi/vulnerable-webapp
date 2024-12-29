package com.vulnapp.vulnerabilities.nointegrity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoIntegrityCheckController {

    /**
     * 무결성 검사 없는 코드 다운로드 취약점 페이지 렌더링
     * @return templates/nointegrity/index.html
     */
    @GetMapping("/vulnerable/nointegrity")
    public String showNoIntegrityCheckPage() {
        return "nointegrity/index"; // nointegrity 디렉토리의 index.html을 렌더링
    }
}
