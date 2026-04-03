package com.example.ragoracle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 파일 업로드 서버 → RAG 서버로 전달하는 임베딩 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmbedRequestDto {
    private String fileName; // 파일명 (공유 스토리지 경로 포함 가능)
    private String category; // 카테고리 메타데이터
}
