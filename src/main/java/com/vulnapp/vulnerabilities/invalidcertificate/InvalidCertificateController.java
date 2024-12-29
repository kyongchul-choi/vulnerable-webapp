package com.vulnapp.vulnerabilities.invalidcertificate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InvalidCertificateController {

    /**
     * 부적절한 인증서 유효성 검증 취약점 페이지 렌더링
     * @return templates/invalidcertificate/index.html
     */
    @GetMapping("/vulnerable/invalidcertificate")
    public String showInvalidCertificatePage() {
        return "invalidcertificate/index";
    }
}
