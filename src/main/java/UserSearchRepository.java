package com.example.user;

/**
 * 데모 PR 템플릿 — SEC-SQL-001 위반 (SQL Injection).
 *
 * 의도된 위반:
 *  - 사용자 입력(email)을 문자열 결합으로 SQL 에 끼워넣음
 */
public class UserSearchRepository {

    private final JdbcTemplate jdbc;

    public UserSearchRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public User findByEmail(String email) {
        // ❌ 위반 (SEC-SQL-001): 문자열 결합 SQL
        String sql = "SELECT id, name, email FROM users WHERE email = '" + email + "'";
        return jdbc.queryForObject(sql, new UserRowMapper());
    }
}
