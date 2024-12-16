package com.vulnapp.filter;

import org.apache.commons.text.StringEscapeUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.IOException;
import java.util.Map;

public class CustomHtmlEscapeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 필요 시 구현
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            chain.doFilter(new HtmlEscapeRequestWrapper((HttpServletRequest) request), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // 종료 시 필요 로직
    }

    private static class HtmlEscapeRequestWrapper extends HttpServletRequestWrapper {

        public HtmlEscapeRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(name);
            return (value != null) ? StringEscapeUtils.escapeHtml4(value) : null;
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] values = super.getParameterValues(name);
            if (values == null) return null;

            for (int i = 0; i < values.length; i++) {
                values[i] = StringEscapeUtils.escapeHtml4(values[i]);
            }
            return values;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            Map<String, String[]> paramMap = super.getParameterMap();
            paramMap.forEach((key, values) -> {
                for (int i = 0; i < values.length; i++) {
                    values[i] = StringEscapeUtils.escapeHtml4(values[i]);
                }
            });
            return paramMap;
        }
    }
}
