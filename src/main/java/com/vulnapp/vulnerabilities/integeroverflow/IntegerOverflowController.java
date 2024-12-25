package com.vulnapp.vulnerabilities.integeroverflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IntegerOverflowController {

    /**
     * Integer Overflow 설명 페이지
     * @return HTML 템플릿 렌더링
     */
    @GetMapping("/vulnerable/integeroverflow")
    public String showIntegerOverflowPage() {
        return "integeroverflow/index"; // HTML 템플릿 경로
    }
}
