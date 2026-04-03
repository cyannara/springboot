package com.example.ragoracle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.ragoracle.dto.EmbedRequestDto;
import com.example.ragoracle.dto.RagAnswerDto;
import com.example.ragoracle.dto.SearchResultDto;
import com.example.ragoracle.dto.UploadResultDto;
import com.example.ragoracle.service.DocumentIngestionService;
import com.example.ragoracle.service.RagSearchService;

import java.util.List;

/**
 * RAG 서버 컨트롤러
 *
 * [REST API - 파일 업로드 서버에서 호출]
 * POST /api/embed → 파일명 수신 후 임베딩 저장
 *
 * [Thymeleaf 웹 UI]
 * GET / → 임베딩 요청 폼 (파일명 직접 입력)
 * POST /embed → 폼에서 파일명 임베딩 요청
 * GET /search → 유사도 검색 페이지
 * GET /ask, POST /ask → RAG 질의응답 페이지
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class RagWebRestController {

    private final DocumentIngestionService ingestionService;
    private final RagSearchService ragSearchService;

    /*
     * ═══════════════════════════════════════════════
     * REST API — 파일 업로드 서버에서 호출하는 엔드포인트
     * ═══════════════════════════════════════════════
     */

    /**
     * POST /api/embed
     * 파일 업로드 서버가 파일을 저장한 후 파일명을 전달하면
     * 공유 스토리지에서 파일을 읽어 임베딩 저장합니다.
     *
     * Request Body:
     * {
     * "fileName": "report_2024.pdf",
     * "category": "기술문서"
     * }
     */
    @PostMapping("/api/embed")
    @ResponseBody
    public ResponseEntity<UploadResultDto> embedByFileName(
            @RequestBody EmbedRequestDto request) {

        log.info("REST API 임베딩 요청: {}", request.getFileName());

        if (request.getFileName() == null || request.getFileName().isBlank()) {
            return ResponseEntity.badRequest().body(
                    UploadResultDto.builder()
                            .success(false)
                            .message("fileName은 필수입니다.")
                            .build());
        }

        UploadResultDto result = ingestionService.ingestByFileName(request);
        return result.isSuccess()
                ? ResponseEntity.ok(result)
                : ResponseEntity.unprocessableEntity().body(result);
    }

    /*
     * ═══════════════════════════════════════════════
     * Thymeleaf 웹 UI
     * ═══════════════════════════════════════════════
     */

    /** GET / → 파일명 입력 폼 */
    @GetMapping("/")
    public String embedForm(Model model) {
        model.addAttribute("pageTitle", "문서 임베딩 등록");
        return "upload";
    }

    /** POST /embed → 폼에서 파일명으로 임베딩 요청 */
    @PostMapping("/embed")
    public String handleEmbedForm(
            @RequestParam("fileName") String fileName,
            @RequestParam(value = "category", defaultValue = "general") String category,
            Model model) {

        model.addAttribute("pageTitle", "문서 임베딩 등록");
        model.addAttribute("fileName", fileName);
        model.addAttribute("category", category);

        if (fileName == null || fileName.isBlank()) {
            model.addAttribute("error", "파일명을 입력해 주세요.");
            return "upload";
        }

        try {
            UploadResultDto result = ingestionService.ingestByFileName(
                    new EmbedRequestDto(fileName, category));
            model.addAttribute("uploadResult", result);
        } catch (Exception e) {
            log.error("임베딩 실패", e);
            model.addAttribute("error", "임베딩 중 오류 발생: " + e.getMessage());
        }

        return "upload";
    }

    /** GET /search → 유사도 검색 페이지 */
    @GetMapping("/search")
    public String searchForm(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "topK", defaultValue = "5") int topK,
            @RequestParam(value = "threshold", defaultValue = "0.3") double threshold,
            Model model) {

        model.addAttribute("pageTitle", "문서 검색");
        model.addAttribute("query", query);
        model.addAttribute("topK", topK);
        model.addAttribute("threshold", threshold);

        if (query != null && !query.isBlank()) {
            try {
                List<SearchResultDto> results = ragSearchService.similaritySearch(query, topK, threshold);
                model.addAttribute("results", results);
                model.addAttribute("resultCount", results.size());
            } catch (Exception e) {
                log.error("검색 실패", e);
                model.addAttribute("searchError", "검색 중 오류 발생: " + e.getMessage());
            }
        }
        return "search";
    }

    /** GET /ask → RAG 질의응답 폼 */
    @GetMapping("/ask")
    public String askForm(Model model) {
        model.addAttribute("pageTitle", "AI 질의응답 (RAG)");
        return "ask";
    }

    /** POST /ask → RAG 질의응답 처리 */
    @PostMapping("/ask")
    public String handleAsk(
            @RequestParam("question") String question,
            @RequestParam(value = "topK", defaultValue = "5") int topK,
            Model model) {

        model.addAttribute("pageTitle", "AI 질의응답 (RAG)");
        model.addAttribute("question", question);
        model.addAttribute("topK", topK);

        if (question != null && !question.isBlank()) {
            try {
                RagAnswerDto answer = ragSearchService.askWithRag(question, topK);
                model.addAttribute("ragAnswer", answer);
            } catch (Exception e) {
                log.error("RAG 질의 실패", e);
                model.addAttribute("askError", "답변 생성 중 오류 발생: " + e.getMessage());
            }
        }
        return "ask";
    }
}
