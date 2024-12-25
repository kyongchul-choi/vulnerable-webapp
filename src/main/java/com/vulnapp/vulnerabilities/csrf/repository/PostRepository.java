package com.vulnapp.vulnerabilities.csrf.repository;

import com.vulnapp.vulnerabilities.csrf.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
