package com.vulnapp.vulnerabilities.infiniteRecursion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfiniteRecursionController {

    /**
     * 종료되지 않는 반복 재귀함수 취약점 페이지 렌더링
     * @return templates/infiniterecursion/index.html
     */
    @GetMapping("/vulnerable/infiniterecursion")
    public String showInfiniteRecursionPage() {
        return "infiniterecursion/index"; // infiniterecursion 디렉토리의 index.html을 렌더링
    }
}
