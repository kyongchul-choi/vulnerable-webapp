package com.vulnapp.vulnerabilities.exceptionhandling;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionHandlingController {

    /**
     * 부적절한 예외처리 취약점 페이지 렌더링
     * @return templates/exceptionhandling/index.html
     */
    @GetMapping("/vulnerable/exceptionhandling")
    public String showExceptionHandlingPage() {
        return "exceptionhandling/index";
    }
}