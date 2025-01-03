package com.vulnapp.vulnerabilities.informationdisclosure;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InformationDisclosureController {

    /**
     * 잘못된 세세한 정보의 데이터 공개 취약점 페이지 렌더링
     * @return templates/informationdisclosure/index.html
     */
    @GetMapping("/vulnerable/informationdisclosure")
    public String showInformationDisclosurePage() {
        return "informationdisclosure/index"; // informationdisclosure 디렉토리의 index.html을 렌더링
    }
}
