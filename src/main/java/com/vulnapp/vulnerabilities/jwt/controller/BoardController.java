package com.vulnapp.vulnerabilities.jwt.controller;

import com.vulnapp.vulnerabilities.jwt.dto.BoardDTO;
import com.vulnapp.vulnerabilities.jwt.model.JwtBoard;
import com.vulnapp.vulnerabilities.jwt.model.JwtUser;
import com.vulnapp.vulnerabilities.jwt.model.UserRole;
import com.vulnapp.vulnerabilities.jwt.service.BoardService;
import com.vulnapp.vulnerabilities.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;  // 추가

@Slf4j  // 추가
@Controller
@RequestMapping("/vulnerable/jwt/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String boardList(Model model, @CookieValue(required = false, name = "token", defaultValue = "") String token) {
        try {
            if (!token.isEmpty()) {
                log.info("Received token: {}", token);
                JwtUser user = userService.getUserFromToken(token);
                model.addAttribute("isAdmin", user.getRole().equals(UserRole.ROLE_ADMIN));
                model.addAttribute("boards", boardService.getAllBoards());
            }
        } catch (Exception e) {
            log.error("Error processing token: {}", e.getMessage());
        }
        return "jwt/board-list";
    }

    @PostMapping
    public String createBoard(BoardDTO boardDTO, @CookieValue String token) {
        JwtUser user = userService.getUserFromToken(token);
        boardService.createBoard(boardDTO.getTitle(), boardDTO.getContent(), user);
        return "redirect:/vulnerable/jwt/board";
    }
}