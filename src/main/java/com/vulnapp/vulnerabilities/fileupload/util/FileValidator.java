package com.vulnapp.vulnerabilities.fileupload.util;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileValidator {
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif");
    private static final Tika tika = new Tika(); // Apache Tika를 이용하여 실제 MIME 타입 검출

    public static boolean isValid(MultipartFile file) {
        if (file == null || file.isEmpty()) return false;

        String filename = file.getOriginalFilename();
        if (filename == null || !filename.contains(".")) return false;

        // 확장자 검사
        String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(ext)) return false;

        try {
            // 실제 MIME 타입 검사
            String detectedType = tika.detect(file.getInputStream());
            return ALLOWED_CONTENT_TYPES.contains(detectedType);
        } catch (IOException e) {
            return false;
        }
    }
}
