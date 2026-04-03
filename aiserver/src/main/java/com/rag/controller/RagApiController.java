package com.rag.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rag.dto.EmbedRequestDto;
import com.rag.dto.RagAnswerDto;
import com.rag.dto.SearchRequest;
import com.rag.dto.UploadResultDto;
import com.rag.service.DocumentIngestionService;
import com.rag.service.RagSearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * RAG REST API 컨트롤러
 *
 * ┌──────────────────────────────────────────────────────┐
 * │ POST /api/embed 파일명 → 임베딩 → pgvector 등록 │
 * │ POST /api/search 질문 → RAG 검색 → 답변 반환 │
 * └──────────────────────────────────────────────────────┘
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RagApiController {

        private final DocumentIngestionService ingestionService;
        private final RagSearchService ragSearchService;

        /*
         * ═══════════════════════════════════════════════════════
         * 1. 임베딩 등록 핸들러
         * ═══════════════════════════════════════════════════════
         */

        /**
         * 파일명을 받아 임베딩 후 pgvector에 등록합니다.
         *
         * POST /api/embed
         *
         * Request Body:
         * {
         * "fileName": "2024/12/15/report.pdf", ← 공유 스토리지 기준 상대 경로
         * "category": "기술문서" ← 메타데이터 (선택)
         * }
         *
         * Response 200 OK:
         * {
         * "success": true,
         * "fileName": "2024/12/15/report.pdf",
         * "chunkCount": 12,
         * "message": "12개 청크가 pgvector에 저장되었습니다."
         * }
         * Response 400: fileName 누락
         * Response 422: 파일 없음 또는 임베딩 처리 실패
         */
        @PostMapping("/embed")
        public ResponseEntity<UploadResultDto> embed(
                        @RequestBody EmbedRequestDto request) {

                log.info("[임베딩 요청] fileName={}, category={}",
                                request.getFileName(), request.getCategory());

                if (request.getFileName() == null || request.getFileName().isBlank()) {
                        return ResponseEntity.badRequest().body(
                                        UploadResultDto.builder()
                                                        .success(false)
                                                        .message("fileName은 필수입니다.")
                                                        .build());
                }

                UploadResultDto result = ingestionService.ingestByFileName(request);

                log.info("[임베딩 완료] success={}, chunkCount={}",
                                result.isSuccess(), result.getChunkCount());

                return result.isSuccess()
                                ? ResponseEntity.ok(result)
                                : ResponseEntity.unprocessableEntity().body(result);
        }

        /*
         * ═══════════════════════════════════════════════════════
         * 2. RAG 검색 핸들러
         * ═══════════════════════════════════════════════════════
         */

        /**
         * 검색 문장을 받아 pgvector에서 유사 문서를 검색하고
         * LLM을 통해 RAG 답변을 반환합니다.
         *
         * POST /api/search
         *
         * Request Body:
         * {
         * "question": "Spring AI에서 pgvector 설정 방법은?",
         * "topK": 5 ← 참조할 최대 문서 수 (선택, 기본값 5)
         * }
         *
         * Response 200 OK:
         * {
         * "question": "Spring AI에서 pgvector 설정 방법은?",
         * "answer": "pgvector를 설정하려면...",
         * "sourceDocuments": [
         * {
         * "id": "550e8400-e29b-41d4-a716-446655440000",
         * "content": "관련 문서 내용...",
         * "metadata": {
         * "source_file": "spring_guide.pdf",
         * "category": "기술문서"
         * },
         * "score": 0.913
         * }
         * ]
         * }
         * Response 400: question 누락
         */
        @PostMapping("/search")
        public ResponseEntity<RagAnswerDto> search(
                        @RequestBody SearchRequest request) {

                log.info("[RAG 검색 요청] question={}, topK={}",
                                request.getQuestion(), request.getTopK());

                if (request.getQuestion() == null || request.getQuestion().isBlank()) {
                        return ResponseEntity.badRequest().build();
                }

                int topK = request.getTopK() > 0 ? request.getTopK() : 5;
                RagAnswerDto result = ragSearchService.askWithRag(request.getQuestion(), topK);

                log.info("[RAG 검색 완료] sourceDocuments={}건",
                                result.getSourceDocuments().size());

                return ResponseEntity.ok(result);
        }
        
        @GetMapping("/")
        public String main() {
        	return "test";
        }
        
        @GetMapping("/search2")
        public ResponseEntity<RagAnswerDto> search2(
                         SearchRequest request) {

                log.info("[RAG 검색 요청] question={}, topK={}",
                                request.getQuestion(), request.getTopK());

                if (request.getQuestion() == null || request.getQuestion().isBlank()) {
                        return ResponseEntity.badRequest().build();
                }

                int topK = request.getTopK() > 0 ? request.getTopK() : 5;
                RagAnswerDto result = ragSearchService.askWithRag(request.getQuestion(), topK);

                log.info("[RAG 검색 완료] sourceDocuments={}건",
                                result.getSourceDocuments().size());

                return ResponseEntity.ok(result);
        }
}