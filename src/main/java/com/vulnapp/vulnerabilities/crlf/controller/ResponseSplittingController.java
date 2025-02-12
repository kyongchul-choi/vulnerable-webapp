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
}