package com.vulnapp.vulnerabilities.uninitialized;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UninitializedVariableController {

    /**
     * 초기화되지 않는 변수 사용 취약점 페이지 렌더링
     * @return templates/uninitialized/index.html
     */
    @GetMapping("/vulnerable/uninitialized")
    public String showUninitializedVariablePage() {
        return "uninitialized/index"; // uninitialized 디렉토리의 index.html을 렌더링
    }
}
