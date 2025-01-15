package com.vulnapp.vulnerabilities.templateinjection.controller;

import com.vulnapp.vulnerabilities.templateinjection.service.TemplateInjectionService;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/vulnerable/templateinjection")
public class TemplateInjectionController {

    @Autowired
    private TemplateInjectionService templateService;

    @GetMapping("")
    public String templatePage(Model model) {
        // 기본 컨텍스트 데이터 추가
        templateService.addTemplateContext(model);
        return "templateinjection/index";
    }

    @PostMapping("/render")
    public String renderTemplate(@RequestParam String message, Model model) {
        templateService.addTemplateContext(model);  // 1. service layer의 addTemplateContext 의 기본 컨텍스트 추가(기본변수설정)
        String result = templateService.processTemplate(message);  // 2. 템플릿 처리(입력값을 service layer의 processtemplate 메소드에서 처리)
        model.addAttribute("userMessage", result);  // 3. index.html 에서 처리된 결과 추가
        return "templateinjection/index";
    }

    @PostMapping("/render-safe")
    public String renderTemplateSafe(@RequestParam String message, Model model) {
        templateService.addTemplateContext(model);
        String result = templateService.processTemplate(message);
        // 안전한 버전 - StringEscapeUtils 사용
        String escapedMessage = StringEscapeUtils.escapeHtml4(message);
        model.addAttribute("safeMessage", escapedMessage);
        return "templateinjection/index";
    }
}