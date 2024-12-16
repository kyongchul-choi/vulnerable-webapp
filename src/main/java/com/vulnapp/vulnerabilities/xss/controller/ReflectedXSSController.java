package com.vulnapp.vulnerabilities.xss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReflectedXSSController {

    /**
     * 검색 페이지
     * @param query 사용자 검색어
     * @param model Thymeleaf 모델
     * @return 검색 페이지 템플릿
     */
    @GetMapping("/xss/search")
    public String searchPage(@RequestParam(value = "query", required = false) String query, Model model) {
        // 검색어를 모델에 추가 (검증하지 않음)
        if (query == null || query.isBlank()) {
            query = "검색어가 없습니다.";
        }
        model.addAttribute("query", query);
        return "xss/search"; // resources/templates/xss/search.html 렌더링
    }
}
