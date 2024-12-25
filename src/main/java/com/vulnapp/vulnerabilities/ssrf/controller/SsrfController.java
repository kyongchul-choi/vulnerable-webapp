package com.vulnapp.vulnerabilities.ssrf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SsrfController {

    @GetMapping("/vulnerable/ssrf")
    public ModelAndView fetchImage(@RequestParam(required = false) String imageUrl) {
        // 파라미터가 없는 경우 입력 폼 렌더링
        if (imageUrl == null || imageUrl.isEmpty()) {
            return new ModelAndView("ssrf/index"); // SSRF 입력 폼 페이지
        }

        // 내부 리소스 요청 처리
        if (imageUrl.equals("http://localhost:8080/vulnerable/ssrf/hr")) {
            return new ModelAndView("redirect:/vulnerable/ssrf/hr");
        }

        // 외부 URL로 리다이렉트
        return new ModelAndView("redirect:" + imageUrl);
    }
}
