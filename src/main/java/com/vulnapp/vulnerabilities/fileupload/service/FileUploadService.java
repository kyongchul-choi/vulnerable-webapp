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
        try {
            // 절대 경로 확인
            File destination = new File(uploadDir, file.getOriginalFilename());
            System.out.println("Upload directory: " + uploadDir);
            System.out.println("Destination path: " + destination.getAbsolutePath());

            // 디렉토리 생성
            if (!destination.getParentFile().exists()) {
                System.out.println("Directory does not exist. Creating...");
                destination.getParentFile().mkdirs();
            }

            // 파일 저장
            file.transferTo(destination);
            System.out.println("File saved successfully at: " + destination.getAbsolutePath());
            System.out.println("User dir: " + System.getProperty("user.dir"));


            return destination.getAbsolutePath();
        } catch (IOException e) {
            throw new InvalidFileException("파일 저장 중 오류 발생: " + e.getMessage());
        }
    }


}
