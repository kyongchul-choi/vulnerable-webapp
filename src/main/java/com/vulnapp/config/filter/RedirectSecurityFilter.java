// src/main/java/com/vulnapp/config/filter/RedirectSecurityFilter.java
package com.vulnapp.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * 모든 리다이렉트 요청에 대한 보안 검증을 수행하는 전역 필터
 * Spring Security Filter Chain에 등록되어 모든 요청에 대해 동작
 */
@Component
public class RedirectSecurityFilter implements Filter {

    /**
     * 리다이렉트가 허용된 도메인 목록
     * 실제 운영환경에서는 설정 파일이나 DB에서 관리하는 것을 권장
     */
    private static final List<String> ALLOWED_DOMAINS = Arrays.asList(
            "localhost",
            "127.0.0.1"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // redirect_url 파라미터가 있는 요청에 대해서만 검증 수행
        String redirectUrl = httpRequest.getParameter("redirect_url");

        if (redirectUrl != null) {
            // 유효하지 않은 리다이렉트 URL인 경우 에러 페이지로 이동
            if (!isValidRedirect(redirectUrl)) {
                ((HttpServletResponse) response).sendRedirect("/error");
                return;
            }
        }

        // 검증을 통과하거나 리다이렉트 요청이 아닌 경우 다음 필터로 진행
        chain.doFilter(request, response);
    }

    /**
     * 리다이렉트 URL의 유효성 검증
     * 1. 내부 경로("/")인 경우 허용
     * 2. 허용된 도메인으로의 리다이렉트인 경우 허용
     */
    private boolean isValidRedirect(String redirectUrl) {
        try {
            // 내부 경로는 허용
            if (redirectUrl.startsWith("/")) {
                return true;
            }

            // URL 파싱 및 도메인 추출
            URI uri = new URI(redirectUrl);
            String host = uri.getHost();

            // 허용된 도메인 목록에 포함된 경우에만 허용
            return host != null && ALLOWED_DOMAINS.contains(host);

        } catch (URISyntaxException e) {
            return false;
        }
    }
}