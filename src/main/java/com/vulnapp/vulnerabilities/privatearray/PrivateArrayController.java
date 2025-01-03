package com.vulnapp.vulnerabilities.privatearray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrivateArrayController {

    /**
     * Private 배열에 Public 데이터 할당 취약점 페이지 렌더링
     * @return templates/privatearray/index.html
     */
    @GetMapping("/vulnerable/privatearray")
    public String showPrivateArrayPage() {
        return "privatearray/index"; // privatearray 디렉토리의 index.html을 렌더링
    }
}
