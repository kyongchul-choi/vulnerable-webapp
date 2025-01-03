package com.vulnapp.vulnerabilities.improperresource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImproperResourceReleaseController {

    /**
     * 부적절한 자원 해제 취약점 페이지 렌더링
     * @return templates/improperresource/index.html
     */
    @GetMapping("/vulnerable/improperresource")
    public String showImproperResourceReleasePage() {
        return "improperresource/index"; // improperresource 디렉토리의 index.html을 렌더링
    }
}
