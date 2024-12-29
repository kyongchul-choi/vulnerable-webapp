package com.vulnapp.vulnerabilities.comments;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommentsController {

    /**
     * 주석문 안에 포함된 시스템 주요정보 취약점 페이지 렌더링
     * @return templates/comments/index.html
     */
    @GetMapping("/vulnerable/comments")
    public String showCommentsPage() {
        return "comments/index";
    }
}
