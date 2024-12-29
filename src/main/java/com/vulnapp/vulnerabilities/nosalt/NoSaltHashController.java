package com.vulnapp.vulnerabilities.nosalt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoSaltHashController {

    /**
     * 솔트 없이 일방향 해쉬함수 사용 취약점 페이지 렌더링
     * @return templates/nosalt/index.html
     */
    @GetMapping("/vulnerable/nosalt")
    public String showNoSaltHashPage() {
        return "nosalt/index";
    }
}
