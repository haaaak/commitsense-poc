# Code Standards (Phase 4 — Standard-as-Code)

이 디렉토리의 자바 파일들은 **사내 표준 그 자체** 입니다.

각 파일의 클래스 주석(`/* ---commitsense-standard--- ... --- */`) 안에
**YAML 형식**으로 표준 메타데이터와 본문이 박혀 있습니다. `make ingest-code` 를
실행하면 이 블록들을 자동 추출해 ChromaDB(RAG)에 적재합니다.

## 사용법

```bash
# 전체 추출 + ChromaDB 적재
make ingest-code

# 다른 경로 스캔
make ingest-code PATH_OVERRIDE=src/main/java

# 미리보기만 (실제 적재 X)
python -m src.code_extractor --path code_standards --dry-run
```

## YAML 블록 작성 규칙

```java
/* ---commitsense-standard---
 * id: SEC-LOG-001          # 필수, 고유 ID
 * category: 보안            # 보안 / 코딩스타일 / 성능 / 문서화 / 테스트
 * language: java
 * status: verified          # verified / draft / legacy
 * depends_on: [SEC-AUTH-001] # 선택, 의존 표준 ID 목록 (GraphRAG)
 * content: |
 *   여기에 표준 본문을 자유롭게 작성한다.
 *   여러 줄, 마크다운 가능.
 * ---
 */
public class MyClass { ... }
```

## 파일별 표준 목록

| 파일 | ID | Category | 비고 |
|------|-----|----------|------|
| `security/TokenMasker.java` | `SEC-LOG-001` | 보안 | 로그 민감정보 마스킹 |
| `security/PasswordPolicy.java` | `SEC-AUTH-003` | 보안 | 비밀번호 정책 |
| `performance/JdbcResources.java` | `PERF-JDBC-001` | 성능 | JDBC try-with-resources |
| `style/NamingConvention.java` | `STYLE-NAMING-001` | 코딩스타일 | 명명 규칙 (PascalCase / camelCase / UPPER_SNAKE_CASE) |
| `style/MagicNumber.java` | `STYLE-CONST-001` | 코딩스타일 | 매직 넘버 금지, 의미 있는 상수 추출 |
