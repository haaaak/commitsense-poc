package com.example.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 사용자 조회 서비스.
 *
 * 의도적으로 사내 표준 위반 코드를 포함합니다 (테스트 목적).
 *
 * 예상 위반 항목 (CommitSense가 검출해야 함):
 *  - 보안     : 하드코딩된 DB 비밀번호 (JAVA-SEC, COMMON-SEC-001)
 *  - DB/JDBC : try-with-resources 미사용, 자원 누수 (JAVA-DB)
 *  - 예외처리 : catch 후 무시 + 로깅 누락 (JAVA-EXC, COMMON-LOG-001)
 *  - 코딩스타일: 매직 넘버, Javadoc 누락 (JAVA-STYLE)
 *  - 테스트   : 테스트 파일 미포함 (COMMON-TEST-001)
 */
public class UserService {

    public List<String> findActiveUsers() {
        List<String> users = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(
                "jdbc:postgresql://prod-db:5432/users",
                "admin",
                "P@ssw0rd1234"
            );
            ps = conn.prepareStatement("SELECT name FROM users WHERE status = 1");
            rs = ps.executeQuery();
            while (rs.next()) {
                users.add(rs.getString("name"));
            }
        } catch (Exception e) {
        }

        return users;
    }

    public boolean isExpired(long lastLoginEpoch) {
        long now = System.currentTimeMillis();
        return (now - lastLoginEpoch) > 2592000000L;
    }
}
