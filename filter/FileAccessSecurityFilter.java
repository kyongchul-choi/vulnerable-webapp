package com.vulnapp.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

@Component
public class FileAccessSecurityFilter implements Filter {

    private static final String BASE_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/config";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getParameter("path");

        if (path != null && httpRequest.getRequestURI().contains("/config/")) {
            try {
                if (!isSecurePath(path)) {
                    sendForbiddenResponse(response, "접근이 거부되었습니다.");
                    return;
                }
            } catch (SecurityException e) {
                sendForbiddenResponse(response, e.getMessage());
                return;
            } catch (Exception e) {
                sendBadRequestResponse(response, "잘못된 요청입니다.");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isSecurePath(String path) throws Exception {
        // URL 디코딩
        String decodedPath = URLDecoder.decode(path, "UTF-8");

        // 악의적인 패턴 필터링
        if (decodedPath.indexOf(0) != -1 || // Null Byte 탐지
                decodedPath.contains("..") ||  // 경로 이탈 방지
                decodedPath.contains("/") ||   // 슬래시 차단
                decodedPath.contains("\\")     // 백슬래시 차단
        ) {
            throw new SecurityException("Unauthorized access detected"); // 403 리턴
        }

        // baseDir 내부 요청 확인
        File baseDir = new File(BASE_DIRECTORY);
        File requestedFile = new File(baseDir, decodedPath);
        String canonicalBaseDir = baseDir.getCanonicalPath();
        String canonicalRequestedPath = requestedFile.getCanonicalPath();

        if (!canonicalRequestedPath.startsWith(canonicalBaseDir)) {
            throw new SecurityException("Unauthorized access");
        }

        return true;
    }

    private void sendForbiddenResponse(ServletResponse response, String message) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, message);
    }

    private void sendBadRequestResponse(ServletResponse response, String message) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
    }
}
