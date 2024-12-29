package com.vulnapp.vulnerabilities.errormessage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorMessageController {

    /**
     * 오류 메시지 정보 노출 취약점 페이지 렌더링
     * @return templates/errormessage/index.html
     */
    @GetMapping("/vulnerable/errormessage")
    public String showErrorMessagePage() {
        return "errormessage/index";
    }
}