package com.vulnapp.vulnerabilities.fileupload.util;

import org.springframework.web.multipart.MultipartFile;

public class FileValidator {

    public static boolean isValid(MultipartFile file) {
       // String contentType = file.getContentType();
       // return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png"));
          return true;
    }
}
