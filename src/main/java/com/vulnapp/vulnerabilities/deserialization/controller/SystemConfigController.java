package com.vulnapp.vulnerabilities.deserialization.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.ObjectInputStream;

@Controller
@RequestMapping("/vulnerable/deserialization")
public class SystemConfigController {

    @GetMapping("/upload")
    public String uploadPage() {
        return "deserialization/upload"; // 업로드 폼 페이지 반환
    }

    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 업로드된 파일을 역직렬화
            InputStream inputStream = file.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            Object config = objectInputStream.readObject(); // 역직렬화 수행
            objectInputStream.close();

            System.out.println("Configuration Details: " + config); // 설정 정보 출력

            return "정상적으로 처리되었습니다. 설정 내용: " + config.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "설정 처리 실패: " + e.getMessage();
        }
    }
}
