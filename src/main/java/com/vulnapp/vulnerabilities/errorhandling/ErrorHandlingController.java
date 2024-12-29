package com.vulnapp.vulnerabilities.errorhandling;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorHandlingController {

    /**
     * 오류 상황 대응 부재 취약점 페이지 렌더링
     * @return templates/errorhandling/index.html
     */
    @GetMapping("/vulnerable/errorhandling")
    public String showErrorHandlingPage() {
        return "errorhandling/index";
    }
}