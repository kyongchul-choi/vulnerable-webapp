package com.vulnapp.vulnerabilities.api.controller;

import com.vulnapp.vulnerabilities.api.service.ApiTraversalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/vulnerable/api")
@RequiredArgsConstructor
public class ApiTraversalController {

    private final ApiTraversalService apiTraversalService;
    private final String BASE_DIR = System.getProperty("user.dir");
    private final String UPLOAD_DIR = BASE_DIR + File.separator + "uploads";

    @GetMapping
    public String showIndex() {
        return "api/index";
    }

    // '/upload' 매핑을 정확히 지정
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            // 업로드 파일 저장
            File uploadFile = new File(UPLOAD_DIR, file.getOriginalFilename());

            // 부모 디렉토리가 없으면 생성
            uploadFile.getParentFile().mkdirs();

            // 파일 저장
            file.transferTo(uploadFile);

            // ZIP 파일 처리
            String resultMessage = apiTraversalService.processZipFile(uploadFile);
            model.addAttribute("result", resultMessage);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("result", "파일 업로드 중 오류 발생: " + e.getMessage());
        }
        return "api/result";
    }
}