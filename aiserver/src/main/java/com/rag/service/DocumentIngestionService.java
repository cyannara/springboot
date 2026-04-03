package com.rag.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rag.dto.EmbedRequestDto;
import com.rag.dto.UploadResultDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 파일을 읽어 청크로 분할하고 Oracle Vector Store에 임베딩하여 저장합니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentIngestionService {

    private final VectorStore vectorStore;

    /** application.yml: rag.file.base-path */
    @Value("${rag.file.base-path:/shared/uploads}")
    private String basePath;

    /**
     * 업로드된 파일을 임베딩하여 벡터 스토어에 저장
     *
     * @param file     업로드 파일 (PDF, DOCX, TXT 등 Tika 지원 형식)
     * @param category 사용자 지정 카테고리 메타데이터
     */
    public UploadResultDto ingestDocument(MultipartFile file, String category) throws IOException {
        String originalFileName = file.getOriginalFilename();
        log.info("문서 업로드 시작: {}", originalFileName);

        // 1. MultipartFile → Spring Resource 변환
        Resource resource = file.getResource();

        return saveDocument(resource, originalFileName, category);
    }

    /**
     * 파일명을 기반으로 공유 스토리지에서 파일을 읽어 임베딩 후 저장
     *
     * @param request 파일명 + 카테고리
     */
    public UploadResultDto ingestByFileName(EmbedRequestDto request) {
        String fileName = request.getFileName();
        String category = request.getCategory() != null ? request.getCategory() : "general";

        log.info("임베딩 요청 수신: fileName={}, category={}", fileName, category);

        // 1. 공유 스토리지 경로 조합 및 파일 존재 확인
        Path filePath = Paths.get(basePath, fileName);
        if (!Files.exists(filePath)) {
            log.error("파일을 찾을 수 없음: {}", filePath);
            return UploadResultDto.builder()
                    .success(false)
                    .fileName(fileName)
                    .chunkCount(0)
                    .message("파일을 찾을 수 없습니다: " + filePath)
                    .build();
        }

        // 2. FileSystemResource로 파일 로드
        Resource resource = new FileSystemResource(filePath);
        return saveDocument(resource, fileName, category);
    }

    public UploadResultDto saveDocument(Resource resource, String fileName, String category) {
        // 3. Apache Tika로 문서 파싱 (PDF, DOCX, XLSX, TXT, HTML 등)
        TikaDocumentReader reader = new TikaDocumentReader(resource);
        List<Document> rawDocuments = reader.get();
        log.info("Tika 파싱 완료: {}개 페이지/섹션", rawDocuments.size());

        // 4. 토큰 기반 청크 분할
        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(800)
                .withMinChunkSizeChars(350)
                .withKeepSeparator(true)
                .build();

        List<Document> chunks = splitter.apply(rawDocuments);
        log.info("청크 분할 완료: {}개", chunks.size());

        // 5. 각 청크에 메타데이터 추가
        String contentType = detectContentType(fileName);
        chunks.forEach(doc -> {
            Map<String, Object> meta = new HashMap<>(doc.getMetadata());
            meta.put("source_file", fileName);
            meta.put("category", category);
            meta.put("content_type", contentType);
            // meta.put("file_path", filePath.toString());
            doc.getMetadata().putAll(meta);
        });

        // 6. pgvector에 저장
        vectorStore.add(chunks);
        log.info("임베딩 저장 완료: fileName={}, chunks={}", fileName, chunks.size());

        return UploadResultDto.builder()
                .success(true)
                .fileName(fileName)
                .chunkCount(chunks.size())
                .message(chunks.size() + "개 청크가 pgvector에 저장되었습니다.")
                .build();
    }

    /** 파일명만 넘어오므로 파일 확장자로 Content-Type 추정 */
    private String detectContentType(String fileName) {
        String lower = fileName.toLowerCase();
        if (lower.endsWith(".pdf"))
            return "application/pdf";
        if (lower.endsWith(".docx"))
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        if (lower.endsWith(".doc"))
            return "application/msword";
        if (lower.endsWith(".txt"))
            return "text/plain";
        if (lower.endsWith(".md"))
            return "text/markdown";
        if (lower.endsWith(".html") || lower.endsWith(".htm"))
            return "text/html";
        if (lower.endsWith(".xlsx"))
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        return "application/octet-stream";
    }
}
