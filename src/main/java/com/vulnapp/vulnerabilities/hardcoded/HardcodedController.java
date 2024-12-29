package com.vulnapp.vulnerabilities.hardcoded;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HardcodedController {

    /**
     * 하드코드된 중요정보 취약점 페이지 렌더링
     * @return templates/hardcoded/index.html
     */
    @GetMapping("/vulnerable/hardcoded")
    public String showHardcodedPage() {
        return "hardcoded/index";
    }
}