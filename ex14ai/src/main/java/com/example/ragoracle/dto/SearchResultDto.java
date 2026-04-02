package com.example.ragoracle.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.Map;

@Getter
@Builder
public class SearchResultDto {
    private String id;
    private String content;
    private Map<String, Object> metadata;
    private Float score;
}
