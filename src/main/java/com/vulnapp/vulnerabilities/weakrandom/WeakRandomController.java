package com.vulnapp.vulnerabilities.weakrandom;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeakRandomController {

    /**
     * 적절하지 않은 난수값 사용 취약점 페이지 렌더링
     * @return templates/weakrandom/index.html
     */
    @GetMapping("/vulnerable/weakrandom")
    public String showWeakRandomPage() {
        return "weakrandom/index";
    }
}
