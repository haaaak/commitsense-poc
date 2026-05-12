package com.example.performance;

/* ---commitsense-standard---
 * id: PERF-JDBC-001
 * category: 성능
 * language: java
 * status: verified
 * depends_on: []
 * content: |
 *   JDBC 리소스(Connection, PreparedStatement, ResultSet)는 항상 try-with-resources
 *   문법으로 사용해야 한다. close() 호출 누락 시 커넥션 풀이 고갈되어 운영 장애로
 *   직결된다.
 *
 *   잘못된 예 (리소스 누수):
 *     Connection conn = dataSource.getConnection();
 *     PreparedStatement ps = conn.prepareStatement(sql);
 *     ResultSet rs = ps.executeQuery();
 *     // ... 예외 발생 시 close() 누락 → 커넥션 누수
 *
 *   올바른 예 (try-with-resources):
 *     try (Connection conn = dataSource.getConnection();
 *          PreparedStatement ps = conn.prepareStatement(sql);
 *          ResultSet rs = ps.executeQuery()) {
 *         while (rs.next()) { ... }
 *     }  // 자동 close, 예외 발생해도 안전
 *
 *   추가 규칙:
 *     - select 시 페이징(LIMIT/OFFSET) 적용. 무제한 select 금지.
 *     - 트랜잭션은 @Transactional 또는 명시적 commit/rollback. autocommit 의존 금지.
 * ---
 */
public final class JdbcResources {

    private JdbcResources() {}

    // 표준의 모범 예시 메서드 (실제 운영 코드는 아니지만 가이드 역할)
    public static int countActiveUsers(javax.sql.DataSource ds) {
        final String sql = "SELECT COUNT(*) FROM users WHERE active = true";
        try (java.sql.Connection conn = ds.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("active user count 조회 실패", e);
        }
    }
}
