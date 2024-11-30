// src/main/java/com/vulnapp/vulnerabilities/inputvalidation/controller/InputValidationController.java
package com.vulnapp.vulnerabilities.inputvalidation.controller;

import com.vulnapp.vulnerabilities.inputvalidation.dto.BoardRequest;
import com.vulnapp.vulnerabilities.inputvalidation.entity.InputValidationUser;
import com.vulnapp.vulnerabilities.inputvalidation.service.InputValidationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vulnerable/inputvalidation")
@RequiredArgsConstructor
public class InputValidationController {

    private final InputValidationService inputValidationService;

    @GetMapping("/board/{id}")
    public String getBoard(@PathVariable Long id, Model model) {
        model.addAttribute("board", inputValidationService.getBoard(id));
        return "inputvalidation/board";
    }

    // 취약한 엔드포인트
    @PostMapping("/unsafe/board/{id}")
    @ResponseBody
    public ResponseEntity<String> unsafeUpdateBoard(
            @PathVariable Long id,
            @RequestBody BoardRequest request) {
        try {
            inputValidationService.unsafeUpdateBoard(id, request);
            return ResponseEntity.ok("게시글이 수정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 안전한 엔드포인트
    @PostMapping("/safe/board/{id}")
    @ResponseBody
    public ResponseEntity<String> safeUpdateBoard(
            @PathVariable Long id,
            @RequestBody BoardRequest request,
            HttpSession session) {

        // 세션에서 사용자 정보 확인
        Long userId = (Long) session.getAttribute("userId");
        InputValidationUser.UserRole userRole = (InputValidationUser.UserRole) session.getAttribute("userRole");

        if (userId == null || userRole == null) {
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        }

        try {
            inputValidationService.safeUpdateBoard(id, request, userId, userRole);
            return ResponseEntity.ok("게시글이 수정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}