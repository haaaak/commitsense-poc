package com.example.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 데모 PR 템플릿 — SEC-LOG-001 위반.
 *
 * 시연 흐름:
 *  1. Streamlit "📥 표준 코드" 패널에 demos/seed_standards/SEC-LOG-001.java 를 붙여넣기 → 학습
 *  2. 이 파일 내용을 데모 GitHub 저장소에 새 브랜치로 push → PR 생성
 *  3. CommitSense Bot 이 PR 코멘트로 SEC-LOG-001 인용하며 위반 지적 (Streamlit 활동 피드에서도 확인 가능)
 *
 * 의도된 위반:
 *  - apiKey 를 마스킹 없이 평문 그대로 로그에 기록
 */
public class UserAuthService {

    private static final Logger log = LoggerFactory.getLogger(UserAuthService.class);

    public boolean authenticate(String userId, String apiKey) {
        // ❌ 위반 (SEC-LOG-001): API 키를 평문 로그
        log.info("authenticate userId={} apiKey={}", userId, apiKey);

        // ... 인증 로직 ...
        return true;
    }
}
