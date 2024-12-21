package com.vulnapp.vulnerabilities.xxe.controller;

import com.vulnapp.vulnerabilities.xxe.service.XMLParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/vulnerable/xxe")
public class XXEController {
    @Autowired
    private XMLParserService xmlParserService;

    @GetMapping("/")
    public String indexPage() {
        return "xxe/index";  // templates/xxe/index.html을 찾습니다
    }

    @PostMapping(value = "/parse", consumes = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody  // 먼저 이 방식으로 테스트해보겠습니다
    public String parseXML(@RequestBody String xmlContent, HttpSession session) {
        try {
            String parsedContent = xmlParserService.parseXML(xmlContent);
            System.out.println("Controller received content: " + parsedContent); // 디버깅
            session.setAttribute("parsedContent", parsedContent);
            return parsedContent;  // 직접 반환하여 테스트
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/result")
    public String showResult(Model model, HttpSession session) {
        String content = (String) session.getAttribute("parsedContent");
        System.out.println("Result page content: " + content); // 디버깅
        model.addAttribute("parsedContent", content);
        session.removeAttribute("parsedContent");
        return "xxe/result";
    }
}