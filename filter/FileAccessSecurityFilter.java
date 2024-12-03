package com.vulnapp.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

// src/main/java/com/vulnapp/config/filter/FileAccessSecurityFilter.java
@Component
public class FileAccessSecurityFilter implements Filter {

    private static final String VALID_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/config";
    private static final Pattern VALID_FILE_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+\\.(properties|txt|log)$");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getParameter("path");

        if (path != null && httpRequest.getRequestURI().contains("/config/")) {
            if (!isSecureFileAccess(path)) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "접근이 거부되었습니다.");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isSecureFileAccess(String path) {
        try {
            // NULL byte 검사
            if (path.indexOf(0) != -1 || path.contains("\0")) {
                return false;
            }
            // 디렉토리 트래버설 방지
            if (path.contains("..")) {
                return false;
            }

            // 파일 패턴 검증
            if (!VALID_FILE_PATTERN.matcher(new File(path).getName()).matches()) {
                return false;
            }

            // 접근 가능 디렉토리 검증
            File file = new File(VALID_DIRECTORY, path);
            String canonicalPath = file.getCanonicalPath();
            if (!canonicalPath.startsWith(new File(VALID_DIRECTORY).getCanonicalPath())) {
                return false;
            }

            // 특정 파일 접근 제한
            if (canonicalPath.endsWith("secure.conf")) {
                return false;
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
