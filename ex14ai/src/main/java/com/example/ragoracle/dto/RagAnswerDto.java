package com.example.ragoracle.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class RagAnswerDto {
    private String question;
    private String answer;
    private List<SearchResultDto> sourceDocuments;
}
