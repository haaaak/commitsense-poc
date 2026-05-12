# CommitSense Demo Repository

이 저장소는 **CommitSense AI 코드 리뷰 봇**의 동작을 시연하기 위한 샘플 코드 저장소입니다.

## 구성

### `code_standards/` — Standard-as-Code
사내 코딩 표준이 코드의 클래스 주석(YAML 블록) 안에 정의되어 있습니다.
CommitSense 봇이 `make ingest-code` 명령으로 자동 추출 → ChromaDB 적재합니다.

| 파일 | 표준 ID | 카테고리 |
|------|---------|----------|
| `security/TokenMasker.java` | `SEC-LOG-001` | 보안 |
| `security/PasswordPolicy.java` | `SEC-AUTH-003` | 보안 |
| `performance/JdbcResources.java` | `PERF-JDBC-001` | 성능 |
| `style/NamingConvention.java` | `STYLE-NAMING-001` | 코딩스타일 |
| `style/MagicNumber.java` | `STYLE-CONST-001` | 코딩스타일 |

### `test_scenarios/scenario_p4_demo/` — 데모 PR 대상
의도적으로 위 5개 표준을 모두 위반하는 자바 코드입니다.
이 코드를 PR로 올리면 CommitSense 봇이 5개 표준 ID를 모두 인용하며 리뷰합니다.

## 사용 흐름

1. 봇 저장소(별도 리포)에서 `make webhook` 으로 봇 서버 기동 + ngrok 노출
2. 이 저장소의 GitHub Webhook 이 그 ngrok URL 을 가리키도록 설정
3. 봇 저장소에서 `make ingest-code PATH_OVERRIDE=<이 저장소>/code_standards` 실행
4. 이 저장소에 위반 코드를 PR로 push → 봇이 자동 리뷰
