package com.vulnapp.vulnerabilities.racecondition;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RaceConditionController {

    /**
     * 경쟁 조건과 사용 시점에 대한 취약점 페이지 렌더링
     * @return templates/racecondition/index.html
     */
    @GetMapping("/vulnerable/racecondition")
    public String showRaceConditionPage() {
        return "racecondition/index"; // racecondition 디렉토리의 index.html을 렌더링
    }
}
