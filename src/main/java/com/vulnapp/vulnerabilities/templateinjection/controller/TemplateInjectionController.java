package com.vulnapp.vulnerabilities.templateinjection.controller;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TemplateInjectionController {

    @GetMapping("/vulnerable/template-injection")
    public String index() {
        return "templateinjection/index";
    }

    @GetMapping("/vulnerable/template-injection/process")
    public String process(@RequestParam(required = false, defaultValue = "") String input, Model model) {
        // 취약한 처리: SpringEL을 사용하여 입력값 평가
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        String evaluatedResult;

        try {
            // 입력값을 SpringEL 표현식으로 평가
            evaluatedResult = parser.parseExpression(input).getValue(context, String.class);
        } catch (SpelEvaluationException e) {
            evaluatedResult = "표현식 평가 중 오류 발생: " + e.getMessage();
        } catch (Exception e) {
            evaluatedResult = "알 수 없는 오류 발생: " + e.getMessage();
        }

        model.addAttribute("unsafeInput", evaluatedResult); // 취약한 결과
        model.addAttribute("safeInput", input); // 안전한 결과 (평가 없이 그대로 전달)
        return "templateinjection/index";
    }
}
