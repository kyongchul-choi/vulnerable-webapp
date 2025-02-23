package com.vulnapp.vulnerabilities.ssrf.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class InternalDomainValidator {

    // 차단할 내부 IP 및 로컬 주소 목록
    private static final List<String> BLOCKED_DOMAINS = List.of(
            "localhost",
            "127.0.0.1",
            "0.0.0.0",
            "169.254.", // 링크-로컬 주소
            "192.168.", // 사설 IP 대역
            "10.",      // 사설 IP 대역
            "172.16.", "172.17.", "172.18.", "172.19.", "172.20.", "172.21.", "172.22.", "172.23.",
            "172.24.", "172.25.", "172.26.", "172.27.", "172.28.", "172.29.", "172.30.", "172.31."
    );

    // 허용할 외부 도메인 (화이트리스트 방식)
    private static final List<String> ALLOWED_DOMAINS = List.of(
            "www.opentext.com",
            "www.naver.com",
            "cafe.naver.com"
    );

    // URL이 내부 네트워크로 연결되는지 확인
    public static boolean isInternalDomain(String url) {
        try {
            URI uri = new URI(url);
            String host = uri.getHost();

            if (host == null) {
                return true; // 잘못된 URL은 차단
            }

            // 내부 IP 및 로컬 주소 차단
            for (String blocked : BLOCKED_DOMAINS) {
                if (host.startsWith(blocked)) {
                    return true;
                }
            }

            return false;
        } catch (URISyntaxException e) {
            return true; // 잘못된 URL 형식은 차단
        }
    }

    // 허용된 외부 도메인인지 확인
    public static boolean isAllowedDomain(String url) {
        try {
            URI uri = new URI(url);
            String host = uri.getHost();

            if (host == null) {
                return false;
            }

            return ALLOWED_DOMAINS.contains(host);
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
