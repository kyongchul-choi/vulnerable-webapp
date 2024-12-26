package com.vulnapp.vulnerabilities.formatstring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormatStringController {

    /**
     * 포맷 스트링 취약점 설명 페이지 매핑
     * @return templates/formatstring/index.html
     */
    @GetMapping("/vulnerable/formatstring")
    public String showFormatStringPage() {
        return "formatstring/index"; // HTML 페이지 렌더링
    }
}
