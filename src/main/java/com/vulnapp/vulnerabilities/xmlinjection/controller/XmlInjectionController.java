package com.vulnapp.vulnerabilities.xmlinjection.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vulnerable")  // 기본 경로 설정
public class XmlInjectionController {

    @GetMapping("/xmlinjection")  // 경로 매핑 수정
    public String showXmlInjectionPage() {
        return "xmlinjection/index";
    }
}