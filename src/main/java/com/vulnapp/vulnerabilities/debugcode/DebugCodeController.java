package com.vulnapp.vulnerabilities.debugcode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DebugCodeController {

    /**
     * 제거되지 않고 남은 디버그 코드 취약점 페이지 렌더링
     * @return templates/debugcode/index.html
     */
    @GetMapping("/vulnerable/debugcode")
    public String showDebugCodePage() {
        return "debugcode/index"; // debugcode 디렉토리의 index.html을 렌더링
    }
}
