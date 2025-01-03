package com.vulnapp.vulnerabilities.nullpointer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NullPointerController {

    /**
     * 널 포인트 역참조 취약점 페이지 렌더링
     * @return templates/nullpointer/index.html
     */
    @GetMapping("/vulnerable/nullpointer")
    public String showNullPointerPage() {
        return "nullpointer/index"; // nullpointer 디렉토리의 index.html을 렌더링
    }
}
