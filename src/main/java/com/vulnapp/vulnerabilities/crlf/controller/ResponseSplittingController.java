package com.vulnapp.vulnerabilities.crlf.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vulnerable/crlf")
public class ResponseSplittingController {

    @GetMapping
    public String indexPage() {
        return "crlf/index";
    }

    // 취약한 코드
    @GetMapping("/redirect")
    public void redirectPage(HttpServletResponse response, @RequestParam String url) throws IOException {
        // URL 디코딩 처리
        String decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8);

        // 직접 출력 스트림에 쓰기
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("HTTP/1.1 302 Found\r\n");
        out.print("Location: " + decodedUrl + "\r\n");
        out.print("\r\n");
        out.flush();
    }


    /*
    // spring 표준 redirect
    @GetMapping("/redirect")
    public String redirectPage(@RequestParam String url) {
        // URL 유효성 검증
        if (!isValidUrl(url)) {
            return "redirect:/error";  // 기본 에러 페이지로 리다이렉션
        }
        return "redirect:" + url;
    }
    private boolean isValidUrl(String url) {
        try {
            // URL 형식 검증
            new URL(url);

            // 허용된 도메인 검증
            String domain = new URL(url).getHost();
            List<String> allowedDomains = Arrays.asList("www.naver.com", "safe-site.com");
            return allowedDomains.contains(domain);

        } catch (MalformedURLException e) {
            return false;
        }
    }
    */
}