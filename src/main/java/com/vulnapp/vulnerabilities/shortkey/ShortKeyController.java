package com.vulnapp.vulnerabilities.shortkey;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShortKeyController {

    /**
     * 충분하지 않은 키 길이 사용 취약점 페이지 렌더링
     * @return templates/shortkey/index.html
     */
    @GetMapping("/vulnerable/shortkey")
    public String showShortKeyPage() {
        return "shortkey/index";
    }
}
