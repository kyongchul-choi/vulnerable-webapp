package com.vulnapp.vulnerabilities.openredirect;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class OpenRedirectController {

    @GetMapping("/vulnerable/openredirect/")
    public String redirectPage() {
        return "openredirect/index"; // index.html 경로
    }


    @GetMapping("/vulnerable/openredirect/redirect")
    public RedirectView handleRedirect(@RequestParam("url") String url) {
        // Open Redirect를 테스트하기 위해 전달받은 URL로 리다이렉트
        return new RedirectView(url);
    }
}
