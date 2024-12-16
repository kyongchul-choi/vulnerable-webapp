package com.vulnapp.vulnerabilities.xss.repository;

import com.vulnapp.vulnerabilities.xss.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
