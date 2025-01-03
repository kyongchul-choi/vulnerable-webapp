package com.vulnapp.vulnerabilities.useafterfree;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UseAfterFreeController {

    /**
     * 해제된 자원 사용 취약점 페이지 렌더링
     * @return templates/useafterfree/index.html
     */
    @GetMapping("/vulnerable/useafterfree")
    public String showUseAfterFreePage() {
        return "useafterfree/index"; // useafterfree 디렉토리의 index.html을 렌더링
    }
}
