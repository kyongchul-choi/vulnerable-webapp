// src/main/java/com/vulnapp/vulnerabilities/sql/controller/SQLInjectionController.java
package com.vulnapp.vulnerabilities.sql.controller;

import com.vulnapp.vulnerabilities.sql.service.SQLInjectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vulnerable/sql")
@RequiredArgsConstructor
public class SQLInjectionController {

    private final SQLInjectionService sqlInjectionService;

    @GetMapping("/login")
    public String loginPage() {
        return "sql/login";
    }

    @PostMapping("/unsafe-login")
    public String unsafeLogin(@RequestParam String username,
                              @RequestParam String password,
                              RedirectAttributes redirectAttributes) {
        boolean success = sqlInjectionService.unsafeLogin(username, password);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "로그인 성공!");
            return "redirect:/vulnerable/sql/success";
        }
        redirectAttributes.addFlashAttribute("error", "로그인 실패!");
        return "redirect:/vulnerable/sql/login";
    }

    @PostMapping("/safe-login")
    public String safeLogin(@RequestParam String username,
                            @RequestParam String password,
                            RedirectAttributes redirectAttributes) {
        boolean success = sqlInjectionService.safeLogin(username, password);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "안전한 로그인 성공!");
            return "redirect:/vulnerable/sql/success";
        }
        redirectAttributes.addFlashAttribute("error", "로그인 실패!");
        return "redirect:/vulnerable/sql/login";
    }

    @GetMapping("/success")
    public String successPage() {
        return "sql/success";
    }
}