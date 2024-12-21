package com.vulnapp.vulnerabilities.fileupload.controller;

import com.vulnapp.vulnerabilities.fileupload.service.FileUploadService;
import com.vulnapp.vulnerabilities.fileupload.exceptions.InvalidFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/vulnerable/fileupload")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping("/")   // 루트 경로 명시
    public String uploadPage() {
        return "fileupload/fileupload";
    }

    @PostMapping("/")  // 루트 경로에 대한 POST 매핑
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String filePath = fileUploadService.saveFile(file);
            model.addAttribute("message", "파일이 성공적으로 업로드되었습니다: " + filePath);
        } catch (InvalidFileException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "fileupload/fileupload";
    }
}