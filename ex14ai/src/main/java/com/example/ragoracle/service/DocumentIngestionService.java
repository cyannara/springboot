package com.example.ragoracle.service;

import com.example.ragoracle.dto.UploadResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 파일을 읽어 청크로 분할하고 Oracle Vector Store에 임베딩하여 저장합니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentIngestionService {

    private final VectorStore vectorStore;

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

        // 2. Apache Tika로 문서 파싱 (PDF, DOCX, XLSX, TXT, HTML 등 지원)
        TikaDocumentReader reader = new TikaDocumentReader(resource);
        List<Document> rawDocuments = reader.get();

        // 3. 토큰 기반 텍스트 청크 분할
        //    - defaultChunkSize: 800 토큰
        //    - minChunkSizeChars: 350 문자
        //    - overlap: 청크 간 컨텍스트 유지를 위한 중복 없음 (기본값)
        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(800)
                .withMinChunkSizeChars(350)
                .withKeepSeparator(true)
                .build();

        List<Document> chunks = splitter.apply(rawDocuments);

        // 4. 각 청크에 메타데이터 추가
        chunks.forEach(doc -> {
            Map<String, Object> meta = new HashMap<>(doc.getMetadata());
            meta.put("source_file", originalFileName);
            meta.put("category", category != null ? category : "general");
            meta.put("content_type", file.getContentType());
            doc.getMetadata().putAll(meta);
        });

        // 5. Vector Store에 저장 (내부적으로 EmbeddingModel 호출 후 Oracle DB에 INSERT)
        vectorStore.add(chunks);

        log.info("문서 임베딩 완료: {} → {}개 청크 저장", originalFileName, chunks.size());

        return UploadResultDto.builder()
                .success(true)
                .fileName(originalFileName)
                .chunkCount(chunks.size())
                .message(chunks.size() + "개 청크가 Oracle Vector Store에 저장되었습니다.")
                .build();
    }
}
