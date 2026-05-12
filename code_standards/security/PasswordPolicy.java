package com.example.security;

/* ---commitsense-standard---
 * id: SEC-AUTH-003
 * category: 보안
 * language: java
 * status: verified
 * depends_on: [SEC-LOG-001]
 * content: |
 *   비밀번호는 다음 정책을 모두 만족해야 한다.
 *
 *   1) 길이: 최소 12자 이상
 *   2) 구성: 영문 대/소문자, 숫자, 특수문자 중 3종 이상 포함
 *   3) 금지: 사용자 ID, 이름, 생년월일을 포함할 수 없음
 *   4) 저장: 평문 저장 금지. 반드시 BCrypt(strength >= 12) 또는 Argon2id 사용
 *   5) 로그: 평문/해시 모두 로그 출력 금지 (SEC-LOG-001 적용)
 *
 *   잘못된 예:
 *     userRepo.save(new User(id, password));    // 평문 저장
 *     log.info("hash={}", encoded);             // 해시 로그 노출
 *
 *   올바른 예:
 *     userRepo.save(new User(id, encoder.encode(password)));
 *     log.info("user {} 가입 완료", id);
 * ---
 */
public final class PasswordPolicy {

    private static final int MIN_LENGTH = 12;
    private static final int REQUIRED_CHARSET_CATEGORIES = 3;

    private PasswordPolicy() {}

    /**
     * 비밀번호가 사내 정책 SEC-AUTH-003 을 만족하는지 검증한다.
     *
     * @param password 검증할 평문 비밀번호
     * @param userId   사용자 ID (포함 금지 검사용)
     * @return 정책 위반 사유 목록 (비어 있으면 통과)
     */
    public static java.util.List<String> validate(String password, String userId) {
        java.util.List<String> reasons = new java.util.ArrayList<>();

        if (password == null || password.length() < MIN_LENGTH) {
            reasons.add("비밀번호는 최소 " + MIN_LENGTH + "자 이상이어야 합니다.");
        }
        if (password != null && userId != null && !userId.isBlank()
                && password.toLowerCase().contains(userId.toLowerCase())) {
            reasons.add("비밀번호에 사용자 ID 를 포함할 수 없습니다.");
        }
        if (countCharsetCategories(password) < REQUIRED_CHARSET_CATEGORIES) {
            reasons.add("영문 대/소문자, 숫자, 특수문자 중 3종 이상을 포함해야 합니다.");
        }
        return reasons;
    }

    private static int countCharsetCategories(String s) {
        if (s == null) return 0;
        boolean upper = false, lower = false, digit = false, special = false;
        for (char c : s.toCharArray()) {
            if (Character.isUpperCase(c)) upper = true;
            else if (Character.isLowerCase(c)) lower = true;
            else if (Character.isDigit(c)) digit = true;
            else special = true;
        }
        int count = 0;
        if (upper) count++;
        if (lower) count++;
        if (digit) count++;
        if (special) count++;
        return count;
    }
}
