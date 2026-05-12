package com.example.style;

/* ---commitsense-standard---
 * id: STYLE-CONST-001
 * category: 코딩스타일
 * language: java
 * status: verified
 * depends_on: [STYLE-NAMING-001]
 * content: |
 *   매직 넘버(코드 본문에 직접 박힌 숫자 리터럴)를 사용해서는 안 된다.
 *   숫자에는 반드시 의미가 있으며, 그 의미를 식별 가능한 상수명으로 추출해야 한다.
 *
 *   허용 예외:
 *     - 0, 1, -1 (인덱스/카운트 등 자명한 의미)
 *     - 명백한 수학 상수 (예: 2.0 으로 나누어 평균 계산)
 *     - 단위 표현이 명백한 경우 (예: 60 * 60 * 24 보다 86_400 더 의도 모호)
 *
 *   잘못된 예:
 *     if (user.getAge() > 18) { ... }                 // 18 의 의미?
 *     Thread.sleep(3000);                              // 왜 3초?
 *     if (password.length() < 8) { ... }               // 정책 변경 시 추적 불가
 *     for (int i = 0; i < 100; i++) { retry(); }       // 100의 의미?
 *
 *   올바른 예:
 *     private static final int LEGAL_ADULT_AGE = 18;
 *     private static final long RETRY_BACKOFF_MS = 3_000L;
 *     private static final int MIN_PASSWORD_LENGTH = 8;
 *     private static final int MAX_RETRY_ATTEMPTS = 100;
 *
 *     if (user.getAge() > LEGAL_ADULT_AGE) { ... }
 *     Thread.sleep(RETRY_BACKOFF_MS);
 *     if (password.length() < MIN_PASSWORD_LENGTH) { ... }
 *     for (int i = 0; i < MAX_RETRY_ATTEMPTS; i++) { retry(); }
 *
 *   상수명 작명 규칙은 STYLE-NAMING-001 의 4번 항목(UPPER_SNAKE_CASE)을 따른다.
 * ---
 */
public final class MagicNumber {

    private static final int LEGAL_ADULT_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 12;
    private static final long RETRY_BACKOFF_MS = 3_000L;
    private static final int MAX_RETRY_ATTEMPTS = 5;

    private MagicNumber() {}

    public static boolean isAdult(int age) {
        return age >= LEGAL_ADULT_AGE;
    }

    public static boolean isPasswordLongEnough(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH;
    }

    public static int getMaxRetries() {
        return MAX_RETRY_ATTEMPTS;
    }

    public static long getRetryBackoffMs() {
        return RETRY_BACKOFF_MS;
    }
}
