// BoardService.java
package com.vulnapp.vulnerabilities.jwt.service;

import com.vulnapp.vulnerabilities.jwt.model.JwtBoard;
import com.vulnapp.vulnerabilities.jwt.model.JwtUser;
import com.vulnapp.vulnerabilities.jwt.model.UserRole;
import com.vulnapp.vulnerabilities.jwt.repository.JwtBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final JwtBoardRepository boardRepository;

    public List<JwtBoard> getAllBoards() {
        return boardRepository.findAll();
    }

    public JwtBoard createBoard(String title, String content, JwtUser user) {
        // 취약한 권한 검증: JWT 토큰의 role 값만 확인
        if (!user.getRole().equals(UserRole.ROLE_ADMIN)) {
            throw new RuntimeException("Admin only can write posts");
        }

        JwtBoard board = new JwtBoard();
        board.setTitle(title);
        board.setContent(content);
        board.setWriter(user);
        board.setCreatedAt(LocalDateTime.now());

        return boardRepository.save(board);
    }
}