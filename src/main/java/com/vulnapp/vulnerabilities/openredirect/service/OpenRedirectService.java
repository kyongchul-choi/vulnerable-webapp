package com.vulnapp.vulnerabilities.openredirect.service;

import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * Open Redirect 취약점 실습을 위한 서비스 클래스
 */
@Service
public class OpenRedirectService {

    /**
     * 리다이렉트가 허용된 도메인 목록
     * 현재는 로컬 환경에서의 테스트를 위해 localhost만 허용
     */
    private static final List<String> ALLOWED_DOMAINS = Arrays.asList(
            "localhost",
            "127.0.0.1"
    );

    /**
     * 취약한 리다이렉트 처리 메소드
     * URL 검증 없이 리다이렉트를 수행하여 어떤 외부 사이트로도 이동 가능
     *
     * @param redirectUrl 리다이렉트할 URL
     * @return 검증 없이 그대로 반환된 URL
     */
    public String processUnsafeRedirect(String redirectUrl) {
        return redirectUrl;  // 취약점: URL 검증 없이 그대로 반환
    }

    /**
     * 안전한 리다이렉트 처리 메소드
     * URL의 유효성을 검사하고 허용된 도메인에 대해서만 리다이렉트 허용
     *
     * @param redirectUrl 리다이렉트할 URL
     * @return 검증된 URL 또는 에러 페이지 경로
     */
    public String processSafeRedirect(String redirectUrl) {
        try {
            // 내부 경로("/")로 시작하는 경우 허용
            if (redirectUrl.startsWith("/")) {
                return redirectUrl;
            }

            // URL 파싱 및 검증
            URI uri = new URI(redirectUrl);
            String host = uri.getHost();

            // 허용된 도메인인 경우에만 리다이렉트 허용
            if (host != null && ALLOWED_DOMAINS.contains(host)) {
                return redirectUrl;
            }

        } catch (URISyntaxException e) {
            // URL 파싱 실패시 에러 페이지로 리다이렉트
        }

        // 검증 실패시 에러 페이지로 리다이렉트
        return "/error";
    }
}