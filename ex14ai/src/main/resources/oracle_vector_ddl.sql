-- ============================================================
--  Oracle DB Vector Store 수동 생성 스크립트
--  (application.yml에서 initialize-schema: true 설정 시 자동 생성되므로
--   수동 생성이 필요한 경우에만 이 스크립트를 실행하세요)
-- ============================================================

-- 1. RAG 전용 사용자 생성 (DBA 계정으로 실행)
-- CREATE USER rag_user IDENTIFIED BY rag_password;
-- GRANT CONNECT, RESOURCE TO rag_user;
-- ALTER USER rag_user QUOTA UNLIMITED ON USERS;

-- 2. Oracle 23ai 이상에서 VECTOR 타입 지원
--    Spring AI가 자동으로 아래 테이블을 생성합니다 (initialize-schema: true)
--    참고용으로 DDL 구조를 확인하세요.

CREATE TABLE VECTOR_STORE (
    ID          VARCHAR2(36)    DEFAULT SYS_GUID() PRIMARY KEY,
    CONTENT     CLOB,
    METADATA    CLOB,           -- JSON 형태로 저장
    EMBEDDING   VECTOR(1536)    -- text-embedding-3-small = 1536차원
);

-- 3. IVF 벡터 인덱스 생성 (유사도 검색 성능 향상)
CREATE VECTOR INDEX VECTOR_STORE_IVF_IDX
    ON VECTOR_STORE(EMBEDDING)
    ORGANIZATION NEIGHBOR PARTITIONS
    WITH TARGET ACCURACY 95
    DISTANCE COSINE
    PARAMETERS (TYPE IVF, NEIGHBOR PARTITIONS 10);
