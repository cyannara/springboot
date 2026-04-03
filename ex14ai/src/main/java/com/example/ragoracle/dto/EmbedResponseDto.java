package com.example.ragoracle.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** RAG 서버로부터 받는 임베딩 결과 (Jackson 역직렬화용) */
@Getter
@Setter
@NoArgsConstructor
public class EmbedResponseDto {
    private boolean success;
    private String fileName;
    private int chunkCount;
    private String message;
}
