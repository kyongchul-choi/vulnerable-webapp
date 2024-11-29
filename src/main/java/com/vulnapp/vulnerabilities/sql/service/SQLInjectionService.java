// src/main/java/com/vulnapp/vulnerabilities/sql/service/SQLInjectionService.java
package com.vulnapp.vulnerabilities.sql.service;

import com.vulnapp.vulnerabilities.sql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
@RequiredArgsConstructor
public class SQLInjectionService {

    private static final Logger logger = LoggerFactory.getLogger(SQLInjectionService.class);

    private final DataSource dataSource;
    private final UserRepository userRepository;

    public boolean unsafeLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = '" + username +
                "' AND password = '" + password + "'";

        logger.info("Executing SQL query: {}", sql);

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.next();

        } catch (Exception e) {
            logger.error("Login failed", e);
            return false;
        }
    }

    public boolean safeLogin(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password).isPresent();
    }
}