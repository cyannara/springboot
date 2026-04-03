package com.rag.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import com.rag.dto.RagAnswerDto;
import com.rag.dto.SearchResultDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * pgvector에서 유사 문서를 검색하고,
 * RAG 방식으로 LLM에 컨텍스트를 전달해 답변을 생성합니다.
 *
 * Spring AI 1.1.x 기준:
 * - VectorStoreDocumentRetriever.builder() 에 topK / similarityThreshold 직접 지정
 * - RetrievalAugmentationAdvisor (org.springframework.ai.rag.advisor) 사용
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RagSearchService {

        private final VectorStore vectorStore;
        private final ChatClient chatClient;

        /**
         * 유사도 검색만 수행 (LLM 호출 없음)
         *
         * @param query     검색 쿼리
         * @param topK      반환할 최대 문서 수
         * @param threshold 유사도 임계값 (0.0 ~ 1.0)
         */
        public List<SearchResultDto> similaritySearch(String query, int topK, double threshold) {
                log.info("유사도 검색: query={}, topK={}, threshold={}", query, topK, threshold);

                SearchRequest request = SearchRequest.builder()
                                .query(query)
                                .topK(topK)
                                .similarityThreshold(threshold)
                                .build();

                List<Document> results = vectorStore.similaritySearch(request);

                return results.stream()
                                .map(doc -> SearchResultDto.builder()
                                                .id(doc.getId())
                                                .content(doc.getText())
                                                .metadata(doc.getMetadata())
                                                .score((Float) doc.getMetadata().getOrDefault("distance", 0.0))
                                                .build())
                                .toList();
        }

        /**
         * RAG 질의응답: 벡터 검색으로 컨텍스트를 가져온 뒤 LLM으로 답변 생성
         *
         * Spring AI 1.1.x 권장 방식:
         * RetrievalAugmentationAdvisor + VectorStoreDocumentRetriever 조합
         *
         * @param question 사용자 질문
         * @param topK     참조 문서 수
         */
        public RagAnswerDto askWithRag(String question, int topK) {
                log.info("RAG 질의응답: question={}", question);

                // 1. 유사 문서 검색 (화면 표시용)
                List<SearchResultDto> sources = similaritySearch(question, topK, 0.3);

                // 2. Spring AI 1.1.x 권장 RAG Advisor 구성
                // VectorStoreDocumentRetriever.Builder → topK / similarityThreshold 직접 지정
                RetrievalAugmentationAdvisor ragAdvisor = RetrievalAugmentationAdvisor.builder()
                                .documentRetriever(
                                                VectorStoreDocumentRetriever.builder()
                                                                .vectorStore(vectorStore)
                                                                .topK(topK)
                                                                .similarityThreshold(0.3)
                                                                .build())
                                .build();
                log.info("questtion", question);
                // 3. ChatClient + RAG Advisor로 LLM 호출
                String answer = chatClient.prompt()
                                .user(question)
                                .advisors(ragAdvisor)
                                .call()
                                .content();

                log.info("LLM 답변 생성 완료");

                return RagAnswerDto.builder()
                                .question(question)
                                .answer(answer)
                                .sourceDocuments(sources)
                                .build();
        }
}
