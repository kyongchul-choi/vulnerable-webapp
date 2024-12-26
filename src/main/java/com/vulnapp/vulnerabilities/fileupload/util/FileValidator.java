package com.vulnapp.vulnerabilities.fileupload.util;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Arrays;

public class FileValidator {
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif");

    public static boolean isValid(MultipartFile file) {
        if (file == null || file.isEmpty()) return false;

        // 파일명 가져오기
        String filename = file.getOriginalFilename();
        if (filename == null) return false;

        // 확장자 검사
        String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(ext)) return false;

        // Content-Type 검사
        String contentType = file.getContentType();
        return contentType != null && ALLOWED_CONTENT_TYPES.contains(contentType);
    }
}