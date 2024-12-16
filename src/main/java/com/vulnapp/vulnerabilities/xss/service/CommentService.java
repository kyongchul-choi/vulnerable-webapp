package com.vulnapp.vulnerabilities.xss.service;

import com.vulnapp.vulnerabilities.xss.entity.Comment;
import com.vulnapp.vulnerabilities.xss.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
