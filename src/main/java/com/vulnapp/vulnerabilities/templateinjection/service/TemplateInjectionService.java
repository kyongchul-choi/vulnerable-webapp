package com.vulnapp.vulnerabilities.templateinjection.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Service
public class TemplateInjectionService {

    @Autowired
    private Environment environment;

    private TemplateEngine stringTemplateEngine;

    @PostConstruct
    public void init() {
        stringTemplateEngine = new TemplateEngine();
        StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();
        stringTemplateResolver.setCacheable(false);
        stringTemplateResolver.setTemplateMode("HTML");
        stringTemplateEngine.setTemplateResolver(stringTemplateResolver);
    }

    public void addTemplateContext(Model model) {
        model.addAttribute("user", "Admin");
        model.addAttribute("version", getVersion());
        model.addAttribute("env", environment);
    }

    public String processTemplate(String input) {
        try {
            Context context = new Context();
            context.setVariable("user", "Admin");
            context.setVariable("version", getVersion());
            context.setVariable("env", environment);
            context.setVariable("userInput", input);

            String template;
            if (input.trim().startsWith("${")) {
                // ${...} 형태라면 표현식에서 ...부분만 추출
                String expressionContent = input.substring(2, input.length());
                if (expressionContent.endsWith("}")) {
                    expressionContent = expressionContent.substring(0, expressionContent.length() - 1);
                    template = "<div th:remove='tag' th:utext='${" + expressionContent + "}'>Default</div>";
                } else {
                    // 형식이 맞지 않으면 그대로 출력
                    template = "<div th:remove='tag' th:utext='${userInput}'>Default</div>";
                }
            } else {
                // 일반 텍스트는 그대로 처리
                template = "<div th:remove='tag' th:utext='${userInput}'>Default</div>";
            }

            return stringTemplateEngine.process(template, context);
        } catch (Exception e) {
            return "템플릿 처리 오류: " + e.getMessage();
        }
    }

    private String getVersion() {
        return "App Version: 1.0.0";
    }
}