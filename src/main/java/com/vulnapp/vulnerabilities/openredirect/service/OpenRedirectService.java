// OpenRedirectService.java 파일에 다음 코드 입력
package com.vulnapp.vulnerabilities.openredirect.service;

import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@Service
public class OpenRedirectService {

    private static final List<String> ALLOWED_DOMAINS = Arrays.asList(
            "localhost",
            "127.0.0.1"
    );

    public String processUnsafeRedirect(String redirectUrl) {
        return redirectUrl;
    }

    public String processSafeRedirect(String redirectUrl) {
        try {
            if (redirectUrl.startsWith("/")) {
                return redirectUrl;
            }

            URI uri = new URI(redirectUrl);
            String host = uri.getHost();

            if (host != null && ALLOWED_DOMAINS.contains(host)) {
                return redirectUrl;
            }

        } catch (URISyntaxException e) {
        }
        return "/error";
    }
}