package com.example.ragoracle.controller;

import com.example.ragoracle.dto.RagAnswerDto;
import com.example.ragoracle.dto.SearchResultDto;
import com.example.ragoracle.dto.UploadResultDto;
import com.example.ragoracle.service.DocumentIngestionService;
import com.example.ragoracle.service.RagSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Thymeleaf 기반 RAG 웹 컨트롤러
 *
 * GET  /           → 메인(업로드 폼)
 * POST /upload     → 파일 업로드 + 임베딩 저장
 * GET  /search     → 유사도 검색 결과 페이지
 * GET  /ask        → RAG 질의응답 페이지 (폼)
 * POST /ask        → RAG 질의응답 처리
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class RagWebController {

    private final DocumentIngestionService ingestionService;
    private final RagSearchService ragSearchService;

    /* ─────────────────────────────────────
       1. 메인 / 업로드 폼 페이지
    ───────────────────────────────────── */
    @GetMapping("/")
    public String uploadForm(Model model) {
        model.addAttribute("pageTitle", "문서 등록");
        return "upload";        // templates/upload.html
    }

    /* ─────────────────────────────────────
       2. 파일 업로드 & 임베딩 저장 핸들러
    ───────────────────────────────────── */
    @PostMapping("/upload")
    public String handleUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "category", defaultValue = "general") String category,
            RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "파일을 선택해 주세요.");
            return "redirect:/";
        }

        try {
            UploadResultDto result = ingestionService.ingestDocument(file, category);
            redirectAttributes.addFlashAttribute("uploadResult", result);
            log.info("업로드 성공: {}", result.getFileName());
        } catch (Exception e) {
            log.error("업로드 실패", e);
            redirectAttributes.addFlashAttribute("error", "업로드 중 오류 발생: " + e.getMessage());
        }

        return "redirect:/";
    }

    /* ─────────────────────────────────────
       3. 유사도 검색 페이지
    ───────────────────────────────────── */
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

        return "search";        // templates/search.html
    }

    /* ─────────────────────────────────────
       4. RAG 질의응답 폼 페이지
    ───────────────────────────────────── */
    @GetMapping("/ask")
    public String askForm(Model model) {
        model.addAttribute("pageTitle", "AI 질의응답 (RAG)");
        return "ask";           // templates/ask.html
    }

    /* ─────────────────────────────────────
       5. RAG 질의응답 처리 핸들러
    ───────────────────────────────────── */
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
