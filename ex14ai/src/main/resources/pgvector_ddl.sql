-- ============================================================
--  PostgreSQL pgvector DDL 참조 스크립트
--  (application.yml에서 initialize-schema: true 설정 시 자동 생성됩니다)
--  수동으로 직접 생성이 필요한 경우에만 실행하세요.
-- ============================================================

-- 1. pgvector 익스텐션 활성화 (슈퍼유저 권한 필요)
CREATE EXTENSION IF NOT EXISTS vector;

-- 2. DB / 사용자 생성 (psql 슈퍼유저로 실행)
-- CREATE DATABASE ragdb;
-- CREATE USER raguser WITH PASSWORD 'ragpassword';
-- GRANT ALL PRIVILEGES ON DATABASE ragdb TO raguser;
-- \c ragdb
-- GRANT ALL ON SCHEMA public TO raguser;

-- 3. Spring AI가 자동 생성하는 벡터 테이블 (initialize-schema: true)
--    참고용 DDL 구조입니다.
CREATE TABLE IF NOT EXISTS vector_store (
    id          UUID         DEFAULT gen_random_uuid() PRIMARY KEY,
    content     TEXT,
    metadata    JSON,
    embedding   VECTOR(1536)    -- text-embedding-3-small = 1536차원
);

-- 4. HNSW 인덱스 (유사도 검색 성능 향상)
CREATE INDEX IF NOT EXISTS vector_store_hnsw_idx
    ON vector_store
    USING hnsw (embedding vector_cosine_ops)
    WITH (m = 16, ef_construction = 64);

-- 5. IVFFlat 인덱스 (대용량 데이터셋 대안)
-- CREATE INDEX IF NOT EXISTS vector_store_ivfflat_idx
--     ON vector_store
--     USING ivfflat (embedding vector_cosine_ops)
--     WITH (lists = 100);
