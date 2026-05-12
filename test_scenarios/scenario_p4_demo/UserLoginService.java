package com.example.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 사용자 로그인 처리 서비스.
 *
 * ⚠ 이 코드에는 의도적으로 사내 표준 5건의 위반이 들어 있습니다.
 *    Phase 4 (Standard-as-Code) 데모용 PR 입니다.
 *
 *    - SEC-LOG-001    : 로그 민감정보 마스킹
 *    - SEC-AUTH-003   : 비밀번호 정책 (평문 저장 금지)
 *    - PERF-JDBC-001  : JDBC try-with-resources
 *    - STYLE-CONST-001: 매직 넘버 금지
 *    - STYLE-NAMING-001: Java 명명 규칙 (PascalCase / camelCase / UPPER_SNAKE_CASE)
 */
public class UserLoginService {

    private static final Logger log = LoggerFactory.getLogger(UserLoginService.class);

    // ── ❌ STYLE-NAMING-001 위반: 상수는 UPPER_SNAKE_CASE 여야 함 ──────
    //    (현재는 camelCase 형태로 작성되어 상수임을 식별하기 어려움)
    private static final int maxRetryCount = 5;

    private final javax.sql.DataSource dataSource;

    public UserLoginService(javax.sql.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 로그인 요청 처리 — 의도된 위반 다수 포함.
     */
    public String login(String userId, String rawPassword) {
        // ── ❌ STYLE-CONST-001 위반: 매직 넘버 8 (의미 모호, 상수 추출 필요) ──
        if (rawPassword == null || rawPassword.length() < 8) {
            return null;
        }

        // ── ❌ SEC-AUTH-003 위반: 평문 비밀번호 저장 + 해시 미적용 ────────
        String storedPassword = rawPassword;

        // ── ❌ PERF-JDBC-001 위반: try-with-resources 미사용 (close 누락) ─
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

                // ── ❌ SEC-LOG-001 위반: 토큰/비밀번호 평문 로그 ───────────
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
        // ❌ finally 블록 없음 → conn/ps/rs close() 누락 → 커넥션 누수
    }

    // ── ❌ STYLE-NAMING-001 위반: 메서드명은 camelCase, 동사로 시작해야 함 ──
    //    (PascalCase 로 작성되어 클래스/생성자처럼 보임)
    public boolean Validate(String userId) {
        // ── ❌ STYLE-CONST-001 위반: 매직 넘버 1000 (재시도 백오프? 무엇?) ──
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return userId != null && !userId.isBlank();
    }
}
// trigger review
// trigger review
