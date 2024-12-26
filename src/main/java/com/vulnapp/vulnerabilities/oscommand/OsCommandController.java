package com.vulnapp.vulnerabilities.oscommand;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OsCommandController {

    /**
     * OS 명령어 삽입 취약점 페이지 렌더링
     * @return templates/oscommand/index.html
     */
    @GetMapping("/vulnerable/oscommand")
    public String showOsCommandPage() {
        return "oscommand/index"; // oscommand 디렉토리의 HTML 렌더링
    }
}
