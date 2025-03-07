// src/main/java/com/vulnapp/vulnerabilities/inputvalidation/service/InputValidationService.java
package com.vulnapp.vulnerabilities.inputvalidation.service;

import com.vulnapp.vulnerabilities.inputvalidation.dto.BoardDTO;
import com.vulnapp.vulnerabilities.inputvalidation.dto.BoardRequest;
import com.vulnapp.vulnerabilities.inputvalidation.entity.Board;
import com.vulnapp.vulnerabilities.inputvalidation.entity.InputValidationUser;
import com.vulnapp.vulnerabilities.inputvalidation.repository.InputValidationBoardRepository;
import com.vulnapp.vulnerabilities.inputvalidation.repository.InputValidationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InputValidationService {

    private final InputValidationBoardRepository boardRepository;
    private final InputValidationUserRepository userRepository;

    // 취약한 버전: 파라미터로 전달된 userId와 role을 검증 없이 사용
    @Transactional
    public void unsafeUpdateBoard(Long boardId, BoardRequest request) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        // 취약점: 클라이언트가 전송한 role 파라미터를 그대로 사용
         if ("ADMIN".equals(request.getRole())) {
            // 관리자 권한으로 수정
            updateBoardContent(board, request);

        } else {
            // 취약점: 클라이언트가 전송한 userId를 검증 없이 사용
            if (board.getUser().getId().equals(request.getUserId())) {
                updateBoardContent(board, request);
            } else {
                throw new RuntimeException("수정 권한이 없습니다.");
            }
        }
    }

    // 안전한 버전: 세션에서 가져온 사용자 정보로 권한 확인
    @Transactional
    public void safeUpdateBoard(Long boardId, BoardRequest request, Long sessionUserId, InputValidationUser.UserRole sessionUserRole) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        // 세션의 사용자 정보로 권한 확인
        if (InputValidationUser.UserRole.ADMIN.equals(sessionUserRole)) {
            updateBoardContent(board, request);
        } else {
            if (board.getUser().getId().equals(sessionUserId)) {
                updateBoardContent(board, request);
            } else {
                throw new RuntimeException("수정 권한이 없습니다.");
            }
        }
    }

    private void updateBoardContent(Board board, BoardRequest request) {
        board.setTitle(request.getTitle());
        board.setContent(request.getContent());
        boardRepository.save(board);
    }

    // 게시글 상세 조회
    public BoardDTO getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        BoardDTO dto = new BoardDTO();
        dto.setId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setUserId(board.getUser().getId());
        dto.setUsername(board.getUser().getUsername());

        return dto;
    }
}