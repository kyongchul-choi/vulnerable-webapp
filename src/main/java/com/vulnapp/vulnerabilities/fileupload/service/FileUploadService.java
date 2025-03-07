package com.vulnapp.vulnerabilities.fileupload.service;

import com.vulnapp.vulnerabilities.fileupload.exceptions.InvalidFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@Service
public class FileUploadService {

    @Value("${fileupload.directory}")
    private String uploadDir;

    public String saveFile(MultipartFile file) throws InvalidFileException {
        if (file.isEmpty()) {
            throw new InvalidFileException("파일이 비어있습니다.");
        }

        try {
            // 파일명에서 특수문자 제거 및 공백을 언더스코어로 변경
            String fileName = file.getOriginalFilename();
            if (fileName == null) {
                throw new InvalidFileException("파일명이 없습니다.");
            }

            fileName = fileName.replaceAll("[^a-zA-Z0-9._-]", "_");

            // 업로드 디렉토리 확인 및 생성
            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                if (!uploadDirectory.mkdirs()) {
                    throw new InvalidFileException("업로드 디렉토리 생성에 실패했습니다.");
                }
            }

            // 파일 저장 경로 설정
            File destination = new File(uploadDirectory, fileName);

            // 디버깅을 위한 로그
            System.out.println("Upload directory path: " + uploadDirectory.getAbsolutePath());
            System.out.println("File destination path: " + destination.getAbsolutePath());

            // 파일 저장
            file.transferTo(destination);

            System.out.println("File saved successfully at: " + destination.getAbsolutePath());

            return destination.getName(); // 상대 경로만 반환

        } catch (IOException e) {
            System.err.println("File upload error: " + e.getMessage());
            throw new InvalidFileException("파일 저장 중 오류 발생: " + e.getMessage());
        }
    }
}