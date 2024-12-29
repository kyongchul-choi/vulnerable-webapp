package com.vulnapp.vulnerabilities.invalidsignature;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InvalidSignatureController {

    /**
     * 부적절한 전자서명 확인 취약점 페이지 렌더링
     * @return templates/invalidsignature/index.html
     */
    @GetMapping("/vulnerable/invalidsignature")
    public String showInvalidSignaturePage() {
        return "invalidsignature/index";
    }
}
