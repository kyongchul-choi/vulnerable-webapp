package com.vulnapp.config;

import com.vulnapp.config.filter.FileAccessSecurityFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.servlet.DispatcherType;
import java.util.EnumSet;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<FileAccessSecurityFilter> fileAccessSecurityFilterRegistration(FileAccessSecurityFilter filter) {
        FilterRegistrationBean<FileAccessSecurityFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class)); // 모든 DispatcherType 적용
        registration.setOrder(-100); // 우선 순위 지정 (낮은 숫자가 높은 우선순위)
        return registration;
    }
}
