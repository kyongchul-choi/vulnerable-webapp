package com.vulnapp.vulnerabilities.crlf.controller.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class CsrfValidator {

    public static String sanitizeUrl(String inputUrl) {
        try {
            // URL 디코딩 처리
            String decodedUrl = URLDecoder.decode(inputUrl, StandardCharsets.UTF_8);

            // CRLF 문자 포함 여부 확인 (있으면 차단)
            if (decodedUrl.contains("\r") || decodedUrl.contains("\n")) {
                throw new IllegalArgumentException("Invalid URL: CRLF characters are not allowed");
            }

            return decodedUrl; // 검증된 URL 반환
        } catch (IllegalArgumentException ex) {
            System.err.println("Error occurred: " + ex.getMessage());
            return "/index.html"; // 예외 발생 시 안전한 페이지로 이동
        }
    }
}
