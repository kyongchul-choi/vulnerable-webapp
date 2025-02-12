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
    public String redirectPage(@RequestParam String url) {
        try {
            String decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8);
            if (decodedUrl.contains("\r") || decodedUrl.contains("\n")) {
                throw new IllegalArgumentException("Invalid URL: CRLF characters are not allowed");
            }
            String sanitizedUrl = decodedUrl.replaceAll("[\r\n]", "");
            return "redirect:" + sanitizedUrl;
        } catch (IllegalArgumentException ex) {
            System.err.println("Error occurred: " + ex.getMessage());
            return "redirect:/index.html"; // 예외 발생 시 /index.html로 이동
        }
    }
}
