package com.example.style;

/* ---commitsense-standard---
 * id: STYLE-NAMING-001
 * category: 코딩스타일
 * language: java
 * status: verified
 * depends_on: []
 * content: |
 *   Java 식별자 명명 규칙은 다음을 따른다. 일관된 명명은 코드 가독성과
 *   IDE 자동완성/리팩토링 도구의 동작 정확도에 직결된다.
 *
 *   1) 클래스/인터페이스/enum 타입명: PascalCase
 *      ✅ UserService, OrderRepository, PaymentStatus
 *      ❌ userService, user_service, USERSERVICE
 *
 *   2) 메서드명: camelCase, 동사로 시작
 *      ✅ findUserById, calculateTotal, sendNotification
 *      ❌ FindUserById, find_user_by_id, user_find
 *
 *   3) boolean 반환 메서드: is/has/can 접두사
 *      ✅ isActive, hasPermission, canEdit
 *      ❌ active, permission, edit
 *
 *   4) 상수(static final): UPPER_SNAKE_CASE
 *      ✅ MAX_RETRY_COUNT, DEFAULT_TIMEOUT_MS
 *      ❌ maxRetryCount, max_retry_count, MAXRETRYCOUNT
 *
 *   5) 일반 변수/필드: camelCase
 *      ✅ userId, totalAmount, retryCount
 *      ❌ UserId, user_id, USER_ID (단, 상수가 아닌 경우)
 *
 *   6) 패키지명: lowercase, 점(.) 구분, 단어 사이 언더스코어 금지
 *      ✅ com.example.userservice
 *      ❌ com.Example.user_service
 * ---
 */
public final class NamingConvention {

    public static final int MAX_RETRY_COUNT = 3;
    public static final long DEFAULT_TIMEOUT_MS = 5_000L;

    private NamingConvention() {}

    /** boolean 메서드는 is/has/can 접두사를 사용한다. */
    public static boolean isValidIdentifier(String name) {
        if (name == null || name.isBlank()) return false;
        if (!Character.isJavaIdentifierStart(name.charAt(0))) return false;
        for (int i = 1; i < name.length(); i++) {
            if (!Character.isJavaIdentifierPart(name.charAt(i))) return false;
        }
        return true;
    }
}
