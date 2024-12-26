package com.vulnapp.vulnerabilities.fileupload.controller;

import com.vulnapp.vulnerabilities.fileupload.service.FileUploadService;
import com.vulnapp.vulnerabilities.fileupload.exceptions.InvalidFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vulnerable/fileupload")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping("/")   // 루트 경로 명시
    public String uploadPage() {
        return "fileupload/fileupload";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        try {
            if (file.isEmpty()) {
                model.addAttribute("error", "파일을 선택해주세요.");
                return "fileupload/fileupload";
            }

            String filePath = fileUploadService.saveFile(file);
            model.addAttribute("message", "파일이 성공적으로 업로드되었습니다: " + filePath);

            // 디버깅을 위한 로그
            System.out.println("File upload success - Path: " + filePath);

        } catch (InvalidFileException e) {
            model.addAttribute("error", e.getMessage());

            // 디버깅을 위한 로그
            System.out.println("File upload error: " + e.getMessage());
        }
        return "fileupload/fileupload";
    }
}