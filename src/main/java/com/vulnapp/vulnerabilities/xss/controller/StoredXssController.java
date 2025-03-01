package com.vulnapp.vulnerabilities.xss.controller;

import com.vulnapp.vulnerabilities.xss.entity.Comment;
import com.vulnapp.vulnerabilities.xss.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.text.StringEscapeUtils;


@Controller
@RequestMapping("/xss/stored")
@RequiredArgsConstructor
public class StoredXssController {

    private final CommentService commentService;

    @GetMapping
    public String getComments(Model model) {
        model.addAttribute("comments", commentService.getAllComments());
        return "xss/stored";
    }

    @PostMapping
    public String postComment(@RequestParam String username, @RequestParam String content) {
        Comment comment = new Comment();
        comment.setUsername(username);
        comment.setContent(content); // 입력값 검증 없이 저장 (Stored XSS 발생)

        commentService.saveComment(comment);
        return "redirect:/xss/stored";
    }
}
