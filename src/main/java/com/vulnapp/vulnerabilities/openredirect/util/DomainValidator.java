package com.vulnapp.vulnerabilities.openredirect.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class DomainValidator {

    private static final List<String> ALLOWED_DOMAINS = Arrays.asList(
            "opentext.com",
            "naver.com"
    );

    public static boolean isValidRedirect(String url) {
        try {
            URI uri = new URI(url);
            String host = uri.getHost();

            // www. 제거 (서브도메인 포함 가능하도록)
            if (host != null && host.startsWith("www.")) {
                host = host.substring(4);
            }

            return ALLOWED_DOMAINS.contains(host);
        } catch (URISyntaxException e) {
            return false; // 잘못된 URL은 차단
        }
    }
}
