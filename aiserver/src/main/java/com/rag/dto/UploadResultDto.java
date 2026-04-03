package com.rag.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UploadResultDto {
    private boolean success;
    private String fileName;
    private int chunkCount;
    private String message;
}
