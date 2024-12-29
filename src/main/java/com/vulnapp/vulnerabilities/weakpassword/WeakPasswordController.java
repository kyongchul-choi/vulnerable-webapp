package com.vulnapp.vulnerabilities.weakpassword;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeakPasswordController {

    /**
     * 취약한 비밀번호 허용 취약점 페이지 렌더링
     * @return templates/weakpassword/index.html
     */
    @GetMapping("/vulnerable/weakpassword")
    public String showWeakPasswordPage() {
        return "weakpassword/index";
    }
}
