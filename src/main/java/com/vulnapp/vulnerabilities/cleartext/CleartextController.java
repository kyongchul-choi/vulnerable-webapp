package com.vulnapp.vulnerabilities.cleartext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CleartextController {

    /**
     * OS 명령어 삽입 취약점 페이지 렌더링
     * @return templates/oscommand/index.html
     */
    @GetMapping("/vulnerable/cleartext")
    public String showOsCommandPage() {
        return "cleartext/index"; // oscommand 디렉토리의 HTML 렌더링
    }
}