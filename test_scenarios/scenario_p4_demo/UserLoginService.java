package com.example.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLoginService {

    private static final Logger log = LoggerFactory.getLogger(UserLoginService.class);

    private static final int maxRetryCount = 5;

    private final javax.sql.DataSource dataSource;

    public UserLoginService(javax.sql.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String login(String userId, String rawPassword) {
        if (rawPassword == null || rawPassword.length() < 8) {
            return null;
        }

        String storedPassword = rawPassword;

        java.sql.Connection conn = null;
        java.sql.PreparedStatement ps = null;
        java.sql.ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement("SELECT password, token FROM users WHERE id = '" + userId + "'");
            rs = ps.executeQuery();
            if (rs.next()) {
                String dbPwd = rs.getString("password");
                String token = rs.getString("token");

                log.info("로그인 시도: userId={}, rawPassword={}, token={}", userId, rawPassword, token);

                if (dbPwd.equals(storedPassword)) {
                    return token;
                }
            }
            return null;
        } catch (java.sql.SQLException e) {
            log.error("login failed for user {} with password {}", userId, rawPassword, e);
            return null;
        }
    }

    public boolean Validate(String userId) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return userId != null && !userId.isBlank();
    }
}
