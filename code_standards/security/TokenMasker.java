package com.example.security;

/* ---commitsense-standard---
 * id: SEC-LOG-001
 * category: 보안
 * language: java
 * status: verified
 * depends_on: []
 * content: |
 *   인증 토큰, 비밀번호, 주민등록번호, 카드번호 등 모든 민감정보(PII)는
 *   로그 출력 시 반드시 마스킹해야 한다.
 *
 *   - 잘못된 예: log.info("token = {}", token);
 *   - 올바른 예: log.info("token = {}", TokenMasker.mask(token));
 *
 *   토큰을 그대로 로그에 출력하면 로그 수집/저장 단계에서 평문 노출이 발생하며,
 *   사고 발생 시 키 로테이션과 영향 평가가 사실상 불가능해진다.
 *
 *   적용 범위:
 *     - 모든 서비스/배치/스케줄러의 INFO 이상 로그
 *     - 예외 메시지(throwable.getMessage()) 포함
 *     - HTTP 요청/응답 본문 로깅 시에도 동일 적용
 * ---
 */
public final class TokenMasker {

    private TokenMasker() {}

    /**
     * 입력값의 앞 2자 + ****  + 뒤 2자만 노출하고 나머지는 마스킹한다.
     *
     * @param value 원본 민감정보 (null 안전)
     * @return 마스킹된 문자열, null/짧은 값은 "****" 반환
     */
    public static String mask(String value) {
        if (value == null || value.length() < 4) {
            return "****";
        }
        int len = value.length();
        return value.substring(0, 2) + "****" + value.substring(len - 2);
    }
}
