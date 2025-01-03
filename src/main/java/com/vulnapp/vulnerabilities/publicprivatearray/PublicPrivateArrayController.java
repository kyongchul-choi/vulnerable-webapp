package com.vulnapp.vulnerabilities.publicprivatearray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicPrivateArrayController {

    /**
     * Public 메소드에서 반환된 Private 배열 취약점 페이지 렌더링
     * @return templates/publicprivatearray/index.html
     */
    @GetMapping("/vulnerable/publicprivatearray")
    public String showPublicPrivateArrayPage() {
        return "publicprivatearray/index"; // publicprivatearray 디렉토리의 index.html을 렌더링
    }
}
