package com.rag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * RAG 검색 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
  private String question; // 검색 문장
  private int topK = 5; // 참조할 최대 문서 수 (기본값 5)
}
