// DataInitializer.java
package com.vulnapp.vulnerabilities.jwt.config;

import com.vulnapp.vulnerabilities.jwt.model.JwtUser;
import com.vulnapp.vulnerabilities.jwt.model.UserRole;
import com.vulnapp.vulnerabilities.jwt.repository.JwtUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final JwtUserRepository userRepository;

    @Override
    public void run(String... args) {
        // 테스트용 계정 생성
        if (userRepository.count() == 0) {
            JwtUser admin = new JwtUser();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setRole(UserRole.ROLE_ADMIN);
            userRepository.save(admin);

            JwtUser user = new JwtUser();
            user.setUsername("user");
            user.setPassword("user");
            user.setRole(UserRole.ROLE_USER);
            userRepository.save(user);
        }
    }
}