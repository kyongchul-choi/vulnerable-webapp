package com.vulnapp.vulnerabilities.csrf.controller;

import com.vulnapp.vulnerabilities.csrf.entity.Post;
import com.vulnapp.vulnerabilities.csrf.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vulnerable/csrf")
public class CsrfController {

    private final PostRepository postRepository;

    @GetMapping("/list")
    public String listPosts(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "csrf/list";
    }

    @GetMapping("/view/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        model.addAttribute("post", post);
        return "csrf/view";
    }

    @GetMapping("/create")
    public String createForm() {
        return "csrf/create";
    }

    @PostMapping("/post")
    public String createPost(@RequestParam String title, @RequestParam String content) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);
        return "redirect:/vulnerable/csrf/list";
    }
}
