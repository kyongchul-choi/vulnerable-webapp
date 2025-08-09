package com.vulnapp.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class FileAccessSecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        Map<String, String[]> params = httpRequest.getParameterMap();

        // 디버그 로그 추가 (필터가 실행되는지 확인)
        System.out.println("FileAccessSecurityFilter 실행: " + uri);

        // 원본 요청 URL 확인 (정규화 이전)
        String requestURL = httpRequest.getRequestURL().toString();
        String queryString = httpRequest.getQueryString();
        String fullURL = requestURL + (queryString != null ? "?" + queryString : "");

        System.out.println("전체 원본 URL: " + fullURL);

        try {
            // 원본 URL도 검사
            if (!isSecureInput(fullURL)) {
                sendForbiddenResponse(response, "잘못된 접근 시도 감지 (원본 URL)");
                return;
            }

            // URI 검사
            if (!isSecureInput(uri)) {
                sendForbiddenResponse(response, "잘못된 접근 시도 감지 (URI)");
                return;
            }

            // URI 경로 세그먼트별 검사 (경로 변수 포함)
            String[] pathSegments = uri.split("/");
            for (String segment : pathSegments) {
                if (!isSecureInput(segment)) {
                    sendForbiddenResponse(response, "잘못된 접근 시도 감지 (경로 세그먼트)");
                    return;
                }
            }

            // 파라미터 검사
            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                for (String value : entry.getValue()) {
                    if (!isSecureInput(value)) {
                        sendForbiddenResponse(response, "잘못된 접근 시도 감지 (파라미터)");
                        return;
                    }
                }
            }

            chain.doFilter(request, response);
        } catch (SecurityException e) {
            sendForbiddenResponse(response, e.getMessage());
        }
    }

    /**
     * 사용자가 정의한 공격 문자열 차단:
     * - Null Byte, "..", "./", "\" 포함 여부 검사
     */
    private boolean isSecureInput(String input) throws SecurityException {
        if (input == null) return true;

        String decodedInput;

        // 디코딩 시도
        try {
            decodedInput = URLDecoder.decode(input, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            System.out.println("디코딩 실패: " + input);
            throw new SecurityException("잘못된 인코딩 감지");
        }

        // 디버깅용 - 원본과 디코딩된 값 출력
        System.out.println("원본: " + input + " -> 디코딩: " + decodedInput);

        // 보안 검사 (디코딩 성공 후)
        if (decodedInput.indexOf(0) != -1) {  // Null Byte 탐지
            System.out.println("Null Byte 감지!");
            throw new SecurityException("허용되지 않는 문자 감지: Null Byte");
        }

        if (decodedInput.contains("..")) {    // 경로 이탈 탐지
            System.out.println(".. 패턴 감지!");
            throw new SecurityException("허용되지 않는 경로 이탈 시도 감지");
        }

        if (decodedInput.contains("./")) {    // 상대 경로 탐지
            System.out.println("./ 패턴 감지!");
            throw new SecurityException("허용되지 않는 상대 경로 감지");
        }

        if (decodedInput.contains("\\")) {    // 백슬래시 차단
            System.out.println("백슬래시 감지!");
            throw new SecurityException("허용되지 않는 문자 감지: 백슬래시");
        }

        if (decodedInput.contains("secure.conf")) {    // secure.conf 파일 차단
            System.out.println("secure.conf 파일 접근 시도 감지!");
            throw new SecurityException("접근 금지된 파일");
        }

        return true;
    }

    /**
     * 403 Forbidden 응답 반환
     */
    private void sendForbiddenResponse(ServletResponse response, String message) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpResponse.setContentType("application/json;charset=UTF-8");
        httpResponse.getWriter().write("{\"error\": \"" + message + "\"}");
        httpResponse.getWriter().flush();
    }
}
