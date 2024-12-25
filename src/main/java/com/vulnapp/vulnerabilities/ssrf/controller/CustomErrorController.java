package com.vulnapp.vulnerabilities.ssrf.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleRuntimeException(RuntimeException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return new ModelAndView("ssrf/error"); // 에러 페이지 렌더링
    }
}
